package net.purplemushroom.everend.event;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.capability.player.EEPlayer;
import net.purplemushroom.everend.capability.player.data.PlayerTracker;
import net.purplemushroom.everend.capability.player.data.RiftFishingData;
import net.purplemushroom.everend.content.entities.FishingRift;
import net.purplemushroom.everend.registry.EEItems;
import net.purplemushroom.everend.util.EntityUtil;
import net.purplemushroom.everend.util.MathUtil;

@Mod.EventBusSubscriber(modid = Everend.MODID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            Level level = player.level();
            EEPlayer playerCap = EEPlayer.from(player);
            if (!event.player.level().isClientSide()) {
                if (playerCap != null) {
                    /**
                     * Nullberry and Shifterine Handler
                     */
                    PlayerTracker fallTracker = playerCap.playerTracker;
                    if (player.blockPosition() != fallTracker.getLastGroundPos()) {
                        if (EntityUtil.isAtStableLocation(player)) {
                            fallTracker.setLastGroundStats(player.blockPosition(), level.dimension().location());
                        } else if (player.isPassenger() && player.getVehicle() != null && EntityUtil.isAtStableLocation(player.getVehicle())) {
                            Entity vehicle = player.getVehicle();
                            fallTracker.setLastGroundStats(vehicle.blockPosition(), level.dimension().location());
                        }
                        playerCap.detectAndSendChanges();
                    }

                    /**
                     * Rift Fishing Handler
                     */
                    RiftFishingData riftFishingData = playerCap.riftFishingData;
                    if (riftFishingData.isActive()) { // FIXME: will break if you exit world while doing the rift thingie
                        FishingRift fishingRift = riftFishingData.getFishingRift(level);
                        if (player.distanceTo(fishingRift) > 16 || !EntityUtil.isHolding(player, EEItems.SHIFTERINE_ROD)) {
                            riftFishingData.stopFishingFromRift();
                        } else {
                            float looking = (float) EntityUtil.lookingAt(player, EntityUtil.getCenterPos(fishingRift));
                            if (looking <= 0.0f) riftFishingData.stopFishingFromRift();
                            riftFishingData.progressFishing(looking / 100);
                            System.out.println(riftFishingData.getProgress());
                        }
                        playerCap.detectAndSendChanges();
                    }
                }
            } else {
                if (playerCap != null) {
                    RiftFishingData riftFishingData = playerCap.riftFishingData;
                    if (riftFishingData.isActive()) { // FIXME: will break if you exit world while doing the rift thingie
                        FishingRift fishingRift = riftFishingData.getFishingRift(level);
                        Vec3 playerPos = event.player.getEyePosition();
                        Vec3 riftPos = EntityUtil.getCenterPos(fishingRift);
                        Vec3 extraPoint = playerPos.add(player.getViewVector(0.0f).scale(10));
                        for (Vec3 node : MathUtil.createBezierCurve(playerPos, riftPos, extraPoint, 20)) {
                            level.addParticle(ParticleTypes.CRIT, node.x, node.y, node.z, 0, 0, 0);
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
