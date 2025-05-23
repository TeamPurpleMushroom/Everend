package net.purplemushroom.everend.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.client.BossInfoHandler;
import net.purplemushroom.everend.client.render.RenderUtils;
import net.purplemushroom.everend.client.render.overlay.RiftFishingOverlay;
import net.purplemushroom.everend.util.EverendBossInfo;

// FIXME: figure out how to get both events registered
@Mod.EventBusSubscriber/*(modid= Everend.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)*/
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

    @SubscribeEvent
    public static void overrideBossBarGUI(CustomizeGuiOverlayEvent.BossEventProgress event) {
        EverendBossInfo<?> bossInfo = BossInfoHandler.info.get(event.getBossEvent().getId());
        if (bossInfo != null) {
            bossInfo.renderBar(event.getGuiGraphics(), event.getX(), event.getY());
            event.setCanceled(true);
        }
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
