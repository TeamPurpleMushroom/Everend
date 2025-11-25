package net.purplemushroom.everend.client.render.screen.menu.splash;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.purplemushroom.everend.util.BitUtil;

public class SolidColorSplash extends EverendSplash {
    protected SolidColorSplash(String pSplash) {
        super(pSplash);
    }

    @Override
    protected Font getCustomFont() {
        return Minecraft.getInstance().font;
    }
}
