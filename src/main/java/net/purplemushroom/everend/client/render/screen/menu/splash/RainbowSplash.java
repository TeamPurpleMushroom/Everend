package net.purplemushroom.everend.client.render.screen.menu.splash;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.purplemushroom.everend.util.text.RainbowTextRendering;

public class RainbowSplash extends EverendSplash {
    protected RainbowSplash(String pSplash) {
        super(pSplash);
    }

    @Override
    protected Font getCustomFont() {
        return new RainbowTextRendering(Minecraft.getInstance().font);
    }
}
