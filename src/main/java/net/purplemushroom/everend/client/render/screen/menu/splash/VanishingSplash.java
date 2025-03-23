package net.purplemushroom.everend.client.render.screen.menu.splash;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

public class VanishingSplash extends NeverendSplash {
    private final long start;

    protected VanishingSplash(String pSplash) {
        super(pSplash);
        start = System.nanoTime();
    }

    /*@Override
    public void render(GuiGraphics pGuiGraphics, int pScreenWidth, Font pFont, int pColor) {
        if (System.nanoTime() - start <= 1E9 * 5) {
            super.render(pGuiGraphics, pScreenWidth, pFont, pColor);
        }
    }*/

    @Override
    protected void renderText(GuiGraphics graphics, Font font, int color) {
        double multiplier = (System.nanoTime() - start - 1E9 * 5) / 1E9;
        multiplier = Mth.clamp(1.0 - multiplier, 0.0, 1.0);
        //int alpha = Mth.ceil(f1 * 255.0F) << 24;
        if (multiplier > 0.0) super.renderText(graphics, font, Math.max(4, Mth.ceil(multiplier * 255.0F)) << 24);
    }
}
