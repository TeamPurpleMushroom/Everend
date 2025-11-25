package net.purplemushroom.everend.client.render.screen.menu.splash;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.purplemushroom.everend.util.BitUtil;
import net.purplemushroom.everend.util.text.GradientTextRendering;

public class FoolsSplash extends EverendSplash {
    private final long startTime;

    private static final String[] LINES = {
            "Never gonna give you up!",
            "Never gonna let you down!",
            "Never gonna run around and desert you!",
            "Never gonna make you cry!",
            "Never gonna say goodbye!",
            "Never gonna tell a lie and hurt you!"
    };

    protected FoolsSplash() {
        super("Never gonna give you up!");
        startTime = System.nanoTime();
    }

    @Override
    public void renderText(GuiGraphics graphics, Font font, int color) {
        int line = (int) ((System.nanoTime() - startTime) / 3E9) % LINES.length;
        graphics.drawCenteredString(font, LINES[line], 0, -8, BitUtil.rgbToInt(0, 255, 0) | color);
    }

    @Override
    protected Font getCustomFont() {
        return new GradientTextRendering(BitUtil.rgbToInt(200, 255, 200), GradientTextRendering.GradientDirection.UP);
    }
}
