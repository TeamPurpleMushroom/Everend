package net.purplemushroom.neverend.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.capability.player.NEPlayer;
import net.purplemushroom.neverend.content.capability.player.data.PlayerTracker;

@Mod.EventBusSubscriber(modid = Neverend.MODID)
public class PlayerMovementEventHandler {

    @SubscribeEvent
    public static void onPlayerMove(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START && event.side == LogicalSide.SERVER) {
            ServerPlayer serverPlayer = (ServerPlayer) event.player;
            ServerLevel serverLevel = serverPlayer.serverLevel();
            NEPlayer playerCap = NEPlayer.from(serverPlayer);
            if (playerCap != null) {
                PlayerTracker fallTracker = playerCap.playerTracker;
                if (serverLevel.getGameTime() % 20L == 0L) {
                    if (serverPlayer.onGround() && serverPlayer.blockPosition() != fallTracker.getLastGroundPos()) {
                        fallTracker.setLastGroundStats(serverPlayer.blockPosition(), serverLevel.dimension().location());
                    }
                    playerCap.detectAndSendChanges();
                }
            }
        }
    }
}
