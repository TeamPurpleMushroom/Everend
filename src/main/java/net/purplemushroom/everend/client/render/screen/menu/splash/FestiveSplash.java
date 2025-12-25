package net.purplemushroom.everend.client.render.screen.menu.splash;

import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.purplemushroom.everend.util.BitUtil;
import net.purplemushroom.everend.util.text.GradientTextRendering;

public class FestiveSplash extends EverendSplash {

    protected FestiveSplash(String pSplash) {
        super(pSplash);
    }

    @Override
    protected Font getCustomFont() {
        return new GradientTextRendering(BitUtil.rgbToInt(255, 255, 255), GradientTextRendering.GradientDirection.UP);
    }

    @Override
    protected void renderText(GuiGraphics graphics, Font font, int color) {
        StringBuilder builder = new StringBuilder();
        boolean green = (2 * Util.getMillis() / 1000L) % 2 == 0;
        int count = 0;
        for (char c : this.splash.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                if (count++ >= 3) {
                    count = 0;
                    green = !green;
                }
            }
            builder.append(green ? "§a" : "§c").append(c);
        }
        graphics.drawCenteredString(font, builder.toString(), 0, -8, BitUtil.rgbToInt(13, 82, 60) | color);
    }
}
