package net.purplemushroom.neverend.event;

import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.capability.player.NEPlayer;
import net.purplemushroom.neverend.content.capability.player.data.PlayerTracker;

@Mod.EventBusSubscriber(modid = Neverend.MODID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onPlayerMove(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START && event.side == LogicalSide.SERVER) {
            ServerPlayer serverPlayer = (ServerPlayer) event.player;
            ServerLevel serverLevel = serverPlayer.serverLevel();
            NEPlayer playerCap = NEPlayer.from(serverPlayer);
            if (playerCap != null) {
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
