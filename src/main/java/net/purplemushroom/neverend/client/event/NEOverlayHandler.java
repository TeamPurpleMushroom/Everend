package net.purplemushroom.neverend.client.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.client.render.overlay.RiftFishingOverlay;

@Mod.EventBusSubscriber(modid= Neverend.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NEOverlayHandler {
    @SubscribeEvent
    public static void renderGUI(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("rift_fishing_progress", (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
            gui.setupOverlayRenderState(true, false);
            RiftFishingOverlay.render(gui, guiGraphics, partialTick, screenWidth, screenHeight);
        });
    }
}
