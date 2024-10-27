package net.purplemushroom.neverend.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.ResourceLocation;
import net.purplemushroom.neverend.Neverend;
import org.jetbrains.annotations.Nullable;

public class NeverendMenuScreen extends TitleScreen {
    public NeverendMenuScreen() {
        super(false, new NeverendLogoRender());
    }

    @Override
    protected void init() {
        super.init();
    }
}

class NeverendLogoRender extends LogoRenderer {
    public static final ResourceLocation NEVEREND_LOGO = Neverend.rl("textures/logo.png");
    public NeverendLogoRender() {
        super(false);
    }

    @Override
    public void renderLogo(GuiGraphics pGuiGraphics, int pScreenWidth, float pTransparency, int pHeight) {
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, pTransparency);
        int i = pScreenWidth / 2 - 128;
        pGuiGraphics.blit(NEVEREND_LOGO, i, pHeight, 0.0F, 0.0F, 256, 44, 256, 64);
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
