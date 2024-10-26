package net.purplemushroom.neverend.event;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.capability.player.NEPlayer;
import net.purplemushroom.neverend.content.capability.player.data.PlayerFallTracker;

@Mod.EventBusSubscriber(modid = Neverend.MODID)
public class PlayerMovementEventHandler {

    @SubscribeEvent
    public static void onPlayerMove(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START && event.side == LogicalSide.SERVER) {
            ServerPlayer serverPlayer = (ServerPlayer) event.player;
            if (event.player != null) {
                boolean onGround = serverPlayer.onGround();

                NEPlayer playerCap = NEPlayer.from(serverPlayer);
                if (playerCap != null) {
                    PlayerFallTracker fallTracker = playerCap.playerFallTracker;
                    if (onGround) {
                        if (serverPlayer.level().getGameTime() % 20L == 0L) {
                            fallTracker.setLastGroundPos(serverPlayer.blockPosition());
                            Neverend.LOGGER.info("GroundPos set at: {}", fallTracker.getLastGroundPos().toString());
                        }
                    }
                    playerCap.detectAndSendChanges();
                }
            }
        }
    }
}
