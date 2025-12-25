package net.purplemushroom.everend.client.render.screen.menu.splash;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.purplemushroom.everend.util.BitUtil;
import net.purplemushroom.everend.util.text.GradientTextRendering;

public class CountdownSplash extends EverendSplash {
    private final long launchStart;
    private final Font redFont;

    protected CountdownSplash(String pSplash) {
        super(pSplash);
        launchStart = System.nanoTime();
        redFont = new GradientTextRendering(Minecraft.getInstance().font, BitUtil.rgbToInt(180, 0, 0), BitUtil.rgbToInt(120, 0, 0));
    }

    @Override
    protected void renderText(GuiGraphics graphics, Font font, int color) {
        int secondsLeft = 10 - (int) (getElapsedTime() / 1E9);
        if (secondsLeft > 0) {
            String msg = String.valueOf(secondsLeft) + '!';
            if (secondsLeft <= 3) {
                graphics.drawCenteredString(redFont, msg, 0, -8, BitUtil.rgbToInt(255, 255, 255) | color);
            } else {
                graphics.drawCenteredString(font, msg, 0, -8, BitUtil.rgbToInt(255, 255, 255) | color);
            }
        }
    }

    public long getElapsedTime() {
        return System.nanoTime() - launchStart;
    }
}
