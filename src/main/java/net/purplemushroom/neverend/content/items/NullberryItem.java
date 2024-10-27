package net.purplemushroom.neverend.content.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.capability.player.NEPlayer;
import net.purplemushroom.neverend.content.capability.player.data.PlayerTracker;
import net.purplemushroom.neverend.util.EntityUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dizzlepop12 (Cornman)
 */
public class NullberryItem extends Item {
    public NullberryItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity player) {
        if (!level.isClientSide()) {
            if (certainDoom(level, player)) handleTeleport((ServerLevel) level, (ServerPlayer) player);
        }
        return super.finishUsingItem(stack, level, player);
    }

    private boolean certainDoom(Level level, LivingEntity player) {
        BlockPos playerPos = player.blockPosition();
        int playerY = playerPos.getY();
        int voidY = level.dimensionType().minY() - 1;
        boolean damagedByVoid = player.getLastDamageSource() == player.damageSources().fellOutOfWorld() && player.getY() < voidY;
        if (damagedByVoid || (playerPos.getY() <= voidY && player.isDescending())) {
            return true;
        } else {
            int distanceToVoid = Mth.clamp(playerY - voidY, voidY, level.getMaxBuildHeight());
            AABB belowBB = AABB.ofSize(player.position().subtract(0, distanceToVoid, 0), 1, distanceToVoid, 1).expandTowards(0, playerY, 0);
            return level.getBlockStatesIfLoaded(belowBB).allMatch(BlockBehaviour.BlockStateBase::isAir);
        }
    }

    private void handleTeleport(ServerLevel serverLevel, Player serverPlayer) {
        BlockPos playerPos = serverLevel.getSharedSpawnPos();
        NEPlayer playerCap = NEPlayer.from((ServerPlayer) serverPlayer);
        if (playerCap != null) {
            PlayerTracker playerTracker = playerCap.playerTracker;
            if (playerTracker.getDimension() == serverLevel.dimension().location()) {
                BlockPos lastGroundPos = playerTracker.getLastGroundPos();
                if (lastGroundPos != null) {
                    playerPos = playerTracker.getLastGroundPos();
                } else {
                    Neverend.LOGGER.error("Last player position is null. Defaulting to spawn position at: {}", playerPos);
                }
            }
        }
        double playerX = playerPos.getX();
        double playerY = playerPos.getY();
        double playerZ = playerPos.getZ();
        Entity mount = serverPlayer.getVehicle();
        if (serverPlayer.isPassenger() && mount != null) {
            List<Entity> passengers = new ArrayList<>(mount.getPassengers());
            for (Entity entity : passengers) {
                entity.stopRiding();
                teleport(serverLevel, entity, playerX, playerY, playerZ);
            }
            //todo: stop mount from brutally dying?
            teleport(serverLevel, mount, playerX, playerY, playerZ);
        } else {
            teleport(serverLevel, serverPlayer, playerX, playerY, playerZ);
        }
        serverPlayer.getCooldowns().addCooldown(this, 20);
    }

    private void teleport(ServerLevel serverLevel, Entity entity, double playerX, double playerY, double playerZ) {
        RandomSource randomSource = entity instanceof LivingEntity livingEntity ? livingEntity.getRandom() : serverLevel.getRandom();
        double randomX = playerX + (randomSource.nextDouble() - 0.5D) * 16.0D;
        double randomY = Mth.clamp(playerY + (double) (randomSource.nextInt(16) - 8), serverLevel.getMinBuildHeight(), (serverLevel.getMinBuildHeight() + (serverLevel).getLogicalHeight() - 1));
        double randomZ = playerZ + (randomSource.nextDouble() - 0.5D) * 16.0D;
        for (int i = 0; i < 16; ++i) {
            if (EntityUtil.randomTeleport(entity, randomX, randomY, randomZ, true)) {
                playEffects(serverLevel, entity, randomX, randomY, randomZ);
            } else {
                if (i == 15) {
                    if (entity.isVehicle() && EntityUtil.randomTeleport(entity, playerX, playerY, playerZ, true)) {
                        break;
                    } else {
                        entity.teleportTo(playerX, playerY, playerZ);
                    }
                    playEffects(serverLevel, entity, playerX, playerY, playerZ);
                    break;
                }
            }
        }
    }

    private void playEffects(ServerLevel serverLevel, Entity entity, double x, double y, double z) {
        serverLevel.gameEvent(GameEvent.TELEPORT, entity.position(), GameEvent.Context.of(entity));
        SoundEvent soundEvent = SoundEvents.CHORUS_FRUIT_TELEPORT;
        serverLevel.playSound(null, x, y, z, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
        entity.playSound(soundEvent, 1.0F, 1.0F);
    }
}
