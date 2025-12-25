package net.purplemushroom.everend.client.render.screen.menu.splash;

import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.purplemushroom.everend.util.BitUtil;
import net.purplemushroom.everend.util.text.FestiveTextRendering;
import net.purplemushroom.everend.util.text.GradientTextRendering;

public class FestiveSplash extends EverendSplash {

    protected FestiveSplash(String pSplash) {
        super(pSplash);
    }

    @Override
    protected Font getCustomFont() {
        return new FestiveTextRendering();
    }

    @Override
    protected void renderText(GuiGraphics graphics, Font font, int color) {
        ((FestiveTextRendering)font).setColorsSwapped((2 * Util.getMillis() / 1000L) % 2 == 0);
        graphics.drawCenteredString(font, this.splash, 0, -8, BitUtil.rgbToInt(255, 255, 255) | color);
    }
}
