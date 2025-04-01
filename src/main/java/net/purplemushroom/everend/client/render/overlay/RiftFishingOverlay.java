package net.purplemushroom.everend.client.render.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.purplemushroom.everend.capability.player.EEPlayer;
import net.purplemushroom.everend.util.BitUtil;

@OnlyIn(Dist.CLIENT)
public class RiftFishingOverlay {
    public static void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float partialTick, int u, int i1) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            EEPlayer playerCap = EEPlayer.from(player);
            if (playerCap != null && playerCap.riftFishingData.isActive()) {
                if (!forgeGui.getMinecraft().options.hideGui) {
                    float progress = playerCap.riftFishingData.getProgress();
                    int i = guiGraphics.guiWidth();
                    int j = 12;
                    int k = i / 2 - 91;

                    guiGraphics.pose().pushPose();
                    guiGraphics.pose().translate(0, 0, -90);
                    RenderSystem.defaultBlendFunc();
                    guiGraphics.fill(k, j, k + 182, j + 5, BitUtil.rgbaToInt(0, 20, 50, 255));
                    RenderSystem.enableBlend();
                    guiGraphics.fill(k, j, k + (int) (182 * progress), j + 5, BitUtil.rgbaToInt(0, 155, 155, 255));
                    RenderSystem.disableBlend();
                    guiGraphics.pose().popPose();
                }
            }
        }
    }
}
