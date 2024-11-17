package net.purplemushroom.neverend.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
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
                RenderUtils.renderTextureOverlay(Neverend.tl("gui/gaze_crystal_overlay.png").fullLocation(), 0.5F);
            }
        }
    }
}
