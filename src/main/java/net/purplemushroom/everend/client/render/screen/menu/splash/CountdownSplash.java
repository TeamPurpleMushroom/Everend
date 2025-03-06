package net.purplemushroom.everend.client.render.screen.menu.splash;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.purplemushroom.everend.util.BitUtil;

public class CountdownSplash extends NeverendSplash {
    private final long launchStart;

    protected CountdownSplash(String pSplash) {
        super(pSplash);
        launchStart = System.nanoTime();
    }

    @Override
    protected void renderText(GuiGraphics graphics, Font font, int color) {
        int secondsLeft = 10 - (int) (getElapsedTime() / 1E9);
        if (secondsLeft > 0) {
            String msg = String.valueOf(secondsLeft) + '!';
            if (secondsLeft <= 3) {
                graphics.drawCenteredString(font, msg, 0, -8, BitUtil.rgbToInt(120, 0, 0) | color);
            } else {
                graphics.drawCenteredString(font, msg, 0, -8, BitUtil.rgbToInt(13, 82, 60) | color);
            }
        }
    }

    public long getElapsedTime() {
        return System.nanoTime() - launchStart;
    }
}
