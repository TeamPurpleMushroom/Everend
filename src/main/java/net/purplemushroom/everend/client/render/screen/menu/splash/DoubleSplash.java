package net.purplemushroom.everend.client.render.screen.menu.splash;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.purplemushroom.everend.util.BitUtil;

public class DoubleSplash extends NeverendSplash {
    protected DoubleSplash(String pSplash) {
        super(pSplash);
    }

    @Override
    protected void renderText(GuiGraphics graphics, Font font, int color) {
        graphics.drawCenteredString(font, this.splash, 0, -3, BitUtil.rgbToInt(13, 82, 60) | color);
        graphics.drawCenteredString(font, this.splash, 0, -13, BitUtil.rgbToInt(197, 54, 201) | color);
    }
}
