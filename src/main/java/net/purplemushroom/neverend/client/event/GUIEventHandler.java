package net.purplemushroom.neverend.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.capability.player.NEPlayer;
import net.purplemushroom.neverend.util.BitUtil;

@Mod.EventBusSubscriber(modid= Neverend.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GUIEventHandler {
    @SubscribeEvent
    public static void renderGUI(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.BOSS_EVENT_PROGRESS.id(), "rift_fishing_progress", (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
            if (!gui.getMinecraft().options.hideGui) {
                NEPlayer playerCap = NEPlayer.from(Minecraft.getInstance().player);
                if (playerCap != null && playerCap.riftFishingData.isActive()) {
                    float progress = playerCap.riftFishingData.getProgress();
                    int i = guiGraphics.guiWidth();
                    int j = 12;
                    int k = i / 2 - 91;

                    gui.setupOverlayRenderState(true, false);

                    guiGraphics.pose().pushPose();
                    guiGraphics.pose().translate(0, 0, -90);
                    RenderSystem.defaultBlendFunc();
                    guiGraphics.fill(k, j, k + 182, j + 5, BitUtil.rgbToInt(0, 255, 0));
                    RenderSystem.enableBlend();
                    guiGraphics.fill(k, j, k + (int) (182 * progress), j + 5, BitUtil.rgbToInt(0, 0, 255));
                    RenderSystem.disableBlend();
                    guiGraphics.pose().popPose();
                }
            }
        });
    }
}
