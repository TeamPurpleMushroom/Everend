package net.purplemushroom.everend.client.render.screen.menu.splash;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.purplemushroom.everend.util.BitUtil;
import net.purplemushroom.everend.util.text.GradientTextRendering;

public class SpookySplash extends EverendSplash {
    protected SpookySplash(String pSplash) {
        super(pSplash);
    }

    @Override
    protected Font getCustomFont() {
        return new GradientTextRendering(BitUtil.rgbToInt(200, 64, 9), GradientTextRendering.GradientDirection.DOWN);
    }

    @Override
    protected void renderText(GuiGraphics graphics, Font font, int color) {
        graphics.drawCenteredString(font, this.splash, 0, -8, BitUtil.rgbToInt(242, 156, 27) | color);
    }
}
