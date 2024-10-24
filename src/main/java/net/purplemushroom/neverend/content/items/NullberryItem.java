package net.purplemushroom.neverend.content.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.neverend.Neverend;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NullberryItem extends Item {
    private final Logger LOGGER = Neverend.LOGGER;
    public NullberryItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity player) {
        ItemStack heldItem = super.finishUsingItem(stack, level, player);
        if (!level.isClientSide) {
            if (certainDoom(level, player)) {
                LOGGER.info("Player passed requirements to be teleported");
                handleTeleport((ServerLevel) level, (Player) player);
            }
        }
        return heldItem;
    }

    private boolean certainDoom(Level level, LivingEntity player) {
        BlockPos playerPos = player.blockPosition();
        int playerY = playerPos.getY();
        int voidY = level.dimensionType().minY() - 1;
        boolean damagedByVoid = player.getLastDamageSource() == player.damageSources().fellOutOfWorld() && player.getY() < voidY;
        LOGGER.info("Min Y level: " + voidY);
        if (damagedByVoid || (playerPos.getY() <= voidY && player.isDescending())) {
            LOGGER.info("Player damaged by void or falling below minimum Y level");
            return true;
        } else {
            int distanceToVoid = Mth.clamp(playerY - voidY, voidY, level.getMaxBuildHeight());
            AABB belowBB = AABB.ofSize(player.position().subtract(0, distanceToVoid, 0), 1, distanceToVoid, 1);
            boolean allMatch = level.getBlockStatesIfLoaded(belowBB).allMatch(BlockBehaviour.BlockStateBase::isAir);

            LOGGER.info("Distance to void: " + distanceToVoid);
            LOGGER.info("Player position: " + playerPos);
            LOGGER.info("Bounding Box: " + belowBB);
            LOGGER.info("Bounding Box size " + belowBB.getSize());
            LOGGER.info("Bounding Box height: " + belowBB.getYsize());
            LOGGER.info("Bounding Box min Y: " + belowBB.minY);
            if (allMatch) {
                LOGGER.info("All match");
                return true;
            } else {
                LOGGER.info("Void not detected");
            }
        }
        return false;
    }

    private void handleTeleport(ServerLevel serverLevel, Player serverPlayer) {
        RandomSource random = serverPlayer.getRandom();
        int maxDistance = 128;
        int minHeight = serverLevel.getMinBuildHeight();
        int maxHeight = serverLevel.getLogicalHeight() - 1;
        double playerX = serverPlayer.getX();
        double playerY = serverPlayer.getY();
        double playerZ = serverPlayer.getZ();
        //for (int i = 0; i < 128; ++i) {
            double randomX = playerX + (random.nextDouble() - 0.5) * maxDistance;
            double randomY = Mth.clamp(playerY + random.nextInt(maxDistance) - 8, minHeight, maxHeight);
            double randomZ = playerZ + (random.nextDouble() - 0.5) * maxDistance;
            Entity mount = serverPlayer.getVehicle();
            if (serverPlayer.isPassenger() && mount != null) {
                List<Entity> passengers = new ArrayList<>(mount.getPassengers());
                for (Entity entity : passengers) {
                    if (entity instanceof LivingEntity passenger) {
                        passenger.stopRiding();
                        teleport(serverLevel, passenger, playerX, playerY, playerZ, randomX, randomY, randomZ);
                        //break;
                    }
                }
                if (mount instanceof LivingEntity livingEntity) {
                    teleport(serverLevel, livingEntity, playerX, playerY, playerZ, randomX, randomY, randomZ);
                    //break;
                }
            } else {
                teleport(serverLevel, serverPlayer, playerX, playerY, playerZ, randomX, randomY, randomZ);
                //break;
            }
        //}
    }

    private void teleport(ServerLevel serverLevel, LivingEntity entity, double x, double y, double z, double x1, double y1, double z1) {
        if (entity.randomTeleport(x1, y1, z1, true)) {
            serverLevel.gameEvent(GameEvent.TELEPORT, entity.position(), GameEvent.Context.of(entity));
            SoundEvent soundevent = SoundEvents.CHORUS_FRUIT_TELEPORT;
            serverLevel.playSound(null, x, y, z, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
            entity.playSound(soundevent, 1.0F, 1.0F);
        }
    }
}
