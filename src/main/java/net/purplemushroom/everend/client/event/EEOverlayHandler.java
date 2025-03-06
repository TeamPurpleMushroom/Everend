package net.purplemushroom.everend.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.client.render.RenderUtils;
import net.purplemushroom.everend.client.render.overlay.RiftFishingOverlay;

@Mod.EventBusSubscriber(modid= Everend.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EEOverlayHandler {
    @SubscribeEvent
    public static void renderGUI(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("rift_fishing_progress", (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
            gui.setupOverlayRenderState(true, false);
            RiftFishingOverlay.render(gui, guiGraphics, partialTick, screenWidth, screenHeight);
        });
        event.registerAboveAll("gaze_crystal_teleport", (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
            gui.setupOverlayRenderState(true, false);
            gazeCrystal(gui, guiGraphics, partialTick, screenWidth, screenHeight);
        });
    }

    //TODO: cap to overlay
    public static void gazeCrystal(ForgeGui forgeGui, GuiGraphics guiGraphics, float v, int u, int i1) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player != null) {
            if (player.hasEffect(MobEffects.FIRE_RESISTANCE)) {
                RenderUtils.renderTextureOverlay(Everend.tl("gui/gaze_crystal_overlay.png").fullLocation(), 0.5F);
            }
        }
    }
}
