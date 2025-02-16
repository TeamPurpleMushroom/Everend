package net.purplemushroom.neverend.client.render.screen.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.purplemushroom.neverend.Neverend;

class NeverendLogoRender extends LogoRenderer {
    public static final ResourceLocation NEVEREND_LOGO = Neverend.rl("textures/gui/logo.png");

    public NeverendLogoRender() {
        super(false);
    }

    @Override
    public void renderLogo(GuiGraphics pGuiGraphics, int pScreenWidth, float pTransparency, int pHeight) {
        if (((NeverendMenuScreen) Minecraft.getInstance().screen).getSplash().specialRenderType == NeverendSplash.SPECIAL_SPLASH_LAUNCH_SEQUENCE) {
            long elapsedTime = System.nanoTime() - NeverendSplash.launchStart;
            boolean flag = 10 - (int) (elapsedTime / 1E9) <= 0;
            if (flag) {
                double y = ((double) elapsedTime) / 1E9 - 10;
                double x = Math.sin(y * 50);
                y = Math.max(0, y - 3);
                y = -y * y * y;
                pGuiGraphics.pose().pushPose();
                pGuiGraphics.pose().translate(x, y, 0);
            }
            pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, pTransparency);
            int i = pScreenWidth / 2 - 128;
            pGuiGraphics.blit(NEVEREND_LOGO, i, pHeight, 0.0F, 0.0F, 256, 47, 256, 47);
            pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            if (flag) pGuiGraphics.pose().popPose();
            return;
        }
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, pTransparency);
        int i = pScreenWidth / 2 - 128;
        pGuiGraphics.blit(NEVEREND_LOGO, i, pHeight, 0.0F, 0.0F, 256, 47, 256, 47);
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
