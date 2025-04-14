package net.purplemushroom.everend.client.render.screen.menu.splash;

import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.util.Mth;
import net.purplemushroom.everend.util.BitUtil;

public class EverendSplash extends SplashRenderer {
    protected EverendSplash(String pSplash) {
        super(pSplash);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pScreenWidth, Font pFont, int pColor) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate((float) pScreenWidth / 2.0F + 123.0F, 69.0F, 0.0F);
        pGuiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-20.0F));

        float f = 1.8F - Mth.sin((float) (Util.getMillis() % 4000L) / 4000.0F * ((float) Math.PI * 2F)) * 0.1F;
        int textWidth = pFont.width(this.splash);
        f = f * 100.0F / (float) (textWidth + 32);
        pGuiGraphics.pose().mulPose(Axis.ZP.rotationDegrees((1 + Mth.sin((float) Util.getMillis() / 1500L)) * 2.5f));
        pGuiGraphics.pose().scale(f, f, f);
        renderText(pGuiGraphics, pFont, pColor);
        pGuiGraphics.pose().popPose();
    }

    public String getText() {
        return this.splash;
    }

    protected void renderText(GuiGraphics graphics, Font font, int color) {
        graphics.drawCenteredString(font, this.splash, 0, -8, BitUtil.rgbToInt(13, 82, 60) | color);
    }
}
