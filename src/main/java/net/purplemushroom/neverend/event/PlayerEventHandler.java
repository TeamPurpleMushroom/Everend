package net.purplemushroom.neverend.event;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.capability.player.NEPlayer;
import net.purplemushroom.neverend.content.capability.player.data.PlayerTracker;
import net.purplemushroom.neverend.content.capability.player.data.RiftFishingData;
import net.purplemushroom.neverend.content.entities.Rift;
import net.purplemushroom.neverend.util.MathUtil;

@Mod.EventBusSubscriber(modid = Neverend.MODID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == LogicalSide.SERVER) {
            ServerPlayer serverPlayer = (ServerPlayer) event.player;
            ServerLevel serverLevel = serverPlayer.serverLevel();
            NEPlayer playerCap = NEPlayer.from(serverPlayer);
            if (playerCap != null) {
                // nullberry/shifterine code
                PlayerTracker fallTracker = playerCap.playerTracker;
                if (serverLevel.getGameTime() % 20L == 0L) {
                    if (serverPlayer.blockPosition() != fallTracker.getLastGroundPos()) {
                        if (serverPlayer.onGround()) {
                            fallTracker.setLastGroundStats(serverPlayer.blockPosition(), serverLevel.dimension().location());
                        } else if (serverPlayer.isPassenger() && serverPlayer.getVehicle() != null && serverPlayer.getVehicle().onGround()) {
                            Entity vehicle = serverPlayer.getVehicle();
                            fallTracker.setLastGroundStats(vehicle.blockPosition(), serverLevel.dimension().location());
                        }
                    }
                    playerCap.detectAndSendChanges();
                }

                // rift fishing code
                // TODO: a lot of this could be on the client side
                RiftFishingData riftData = playerCap.riftFishingData;
                if (riftData.isActive(serverLevel)) { // FIXME: will break if you exit world while doing the rift thingie
                    Rift rift = riftData.getRift(serverLevel);
                    if (serverPlayer.distanceTo(rift) > 16) {
                        riftData.stopFishingFromRift();
                    } else {
                        Vec3 playerPos = serverPlayer.getEyePosition();
                        Vec3 riftPos = rift.position().add(0, rift.getBbHeight() / 2, 0);
                        Vec3 extraPoint = playerPos.add(serverPlayer.getViewVector(0.0f).scale(10));
                        for (Vec3 node : MathUtil.createBezierCurve(playerPos, riftPos, extraPoint, 20)) {
                            serverLevel.sendParticles(ParticleTypes.CRIT, node.x, node.y, node.z, 10, 0, 0, 0, 0);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        DamageSource damageSource = event.getSource();
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            Inventory playerInventory = player.getInventory();
            ListTag listTag = new ListTag();
            playerInventory.save(listTag);
        }
    }
}
