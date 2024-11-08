package net.purplemushroom.neverend.client.render.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.purplemushroom.neverend.capability.player.NEPlayer;
import net.purplemushroom.neverend.util.BitUtil;

@OnlyIn(Dist.CLIENT)
public class RiftFishingOverlay {
    public static void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float v, int u, int i1) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            NEPlayer playerCap = NEPlayer.from(player);
            if (playerCap != null && playerCap.riftFishingData.isActive()) {
                if (!forgeGui.getMinecraft().options.hideGui) {
                    float progress = playerCap.riftFishingData.getProgress();
                    int i = guiGraphics.guiWidth();
                    int j = 12;
                    int k = i / 2 - 91;

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
        }
    }
}
