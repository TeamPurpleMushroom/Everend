package net.purplemushroom.everend.client.render.screen.menu.splash;

import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public class EndIsNeverSplash extends EverendSplash {
    protected EndIsNeverSplash(String pSplash) {
        super(pSplash);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pScreenWidth, Font pFont, int pColor) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate((float) pScreenWidth / 2.0F + 123.0F, 69.0F, 0.0F);
        pGuiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-20.0F));

        float f = 1.8F - Mth.sin((float) (Util.getMillis() % 4000L) / 4000.0F * ((float) Math.PI * 2F)) * 0.1F;
        int textWidth = pFont.width(this.splash) / 10;
        f = f * 100.0F / (float) (textWidth + 32);
        pGuiGraphics.pose().mulPose(Axis.ZP.rotationDegrees((1 + Mth.sin((float) Util.getMillis() / 1500L)) * 2.5f));
        pGuiGraphics.pose().scale(f, f, f);
        renderText(pGuiGraphics, pFont, pColor);
        pGuiGraphics.pose().popPose();
    }
}
