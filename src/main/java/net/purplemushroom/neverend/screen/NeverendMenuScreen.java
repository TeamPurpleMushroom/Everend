package net.purplemushroom.neverend.screen;

import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.util.BitUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class NeverendMenuScreen extends TitleScreen {
    public NeverendMenuScreen() {
        super(false, new NeverendLogoRender());
    }

    @Override
    protected void init() {
        super.init();
        splash = NeverendSplash.getRandomSplash();
        panorama = new NeverendMenuBackground();
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

class NeverendSplash extends SplashRenderer {
    public static final String[] SPLASHES = {
            "Purple is the new black!",
            "Nevermine in the End!",
            "THE END IS NEVER THE END IS NEVER THE END IS NEVER THE END IS NEVER THE END IS NEVER THE END IS NEVER THE END IS NEVER THE END IS NEVER",
            "A friendly perdition!",
            isNostalgic() ? "Nostalgic!" : "Not nostalgic yet!",
            "Subjectively good!",
            "Woo, Discord!",
            "Woo, GitHub!",
            "Woo, CurseForge!",
            "Woo, Modrinth!",
            "Eww, 9Minecraft!",
            "Also try The Aether!",
            "Also try The Betweenlands!",
            "Also try Eternalcraft!",
            "Also try Advent of Ascension!",
            "Family friendly!",
            "Remember the cows!",
            "It never ends!",
            "Powered by TimeCore!",
            "Powered by NeoForge!",
            "The effect affects the picture!",
            "Do you want to have a bad time?",
            "I... am Steve.",
            "The Ancients have awakened!",
            "No soup for you!",
            "Mun or bust!",
            "Faster than light!",
            "To infinity, and beyond!",
            "FATALITY",
            "Finish him!",
            "Absolutely NOT dragon free!",
            "Modded!",
            "Modified!",
            "Hot!",
            "Open source!",
            "Made with love!",
            "Here's Johnny!",
            "Part of a complete breakfast!",
            "Mamma mia!",
            "It's a bird! It's a plane!"
    };

    private NeverendSplash(String pSplash) {
        super(pSplash);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pScreenWidth, Font pFont, int pColor) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate((float)pScreenWidth / 2.0F + 123.0F, 69.0F, 0.0F);
        pGuiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-20.0F));
        float f = 1.8F - Mth.abs(Mth.sin((float)(Util.getMillis() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
        f = f * 100.0F / (float)(pFont.width(this.splash) + 32);
        pGuiGraphics.pose().scale(f, f, f);
        pGuiGraphics.drawCenteredString(pFont, this.splash, 0, -8, BitUtil.rgbToInt(13, 82, 60) | pColor);
        pGuiGraphics.pose().popPose();
    }

    public static NeverendSplash getRandomSplash() {
        return new NeverendSplash(SPLASHES[new Random().nextInt(SPLASHES.length)]);
    }

    private static boolean isNostalgic() {
        return false;
    }
}

class NeverendMenuBackground extends PanoramaRenderer {
    public NeverendMenuBackground() {
        super(null);
    }

    @Override
    public void render(float pDeltaT, float pAlpha) {

    }
}
