package net.purplemushroom.neverend.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.capability.player.NEPlayer;
import net.purplemushroom.neverend.content.capability.player.data.PlayerTracker;
import net.purplemushroom.neverend.content.capability.player.data.RiftFishingData;
import net.purplemushroom.neverend.content.entities.Rift;
import net.purplemushroom.neverend.registry.NEItems;
import net.purplemushroom.neverend.util.EntityUtil;
import net.purplemushroom.neverend.util.MathUtil;

@Mod.EventBusSubscriber(modid = Neverend.MODID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            Level level = player.level();
            NEPlayer playerCap = NEPlayer.from(player);
            if (!event.player.level().isClientSide()) {
                if (playerCap != null) {
                    // nullberry/shifterine code
                    PlayerTracker fallTracker = playerCap.playerTracker;
                    if (level.getGameTime() % 20L == 0L) {
                        if (player.blockPosition() != fallTracker.getLastGroundPos()) {
                            if (EntityUtil.isAtStableLocation(player)) {
                                fallTracker.setLastGroundStats(player.blockPosition(), level.dimension().location());
                            } else if (player.isPassenger() && player.getVehicle() != null && EntityUtil.isAtStableLocation(player.getVehicle())) {
                                Entity vehicle = player.getVehicle();
                                fallTracker.setLastGroundStats(vehicle.blockPosition(), level.dimension().location());
                            }
                        }
                        playerCap.detectAndSendChanges();
                    }

                    // rift fishing code
                    RiftFishingData riftData = playerCap.riftFishingData;
                    if (riftData.isActive(level)) { // FIXME: will break if you exit world while doing the rift thingie
                        Rift rift = riftData.getRift(level);
                        if (player.distanceTo(rift) > 16 || !EntityUtil.isHolding(player, NEItems.SHIFTERINE_ROD)) {
                            riftData.stopFishingFromRift();
                            playerCap.detectAndSendChanges();
                        } else {
                            float looking = (float) EntityUtil.lookingAt(player, EntityUtil.getCenterPos(rift));
                            if (looking <= 0.0f) riftData.stopFishingFromRift();
                            riftData.progressFishing(looking / 100);
                        }
                    }
                }
            } else {
                if (playerCap != null) {
                    RiftFishingData riftData = playerCap.riftFishingData;
                    if (riftData.isActive(event.player.level())) { // FIXME: will break if you exit world while doing the rift thingie
                        Rift rift = riftData.getRift(level);
                        Vec3 playerPos = event.player.getEyePosition();
                        Vec3 riftPos = EntityUtil.getCenterPos(rift);
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
