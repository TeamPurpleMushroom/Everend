package net.purplemushroom.neverend.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.util.BitUtil;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class NeverendMenuScreen extends TitleScreen {
    private long time = 0;
    public NeverendMenuScreen() {
        super(false, new NeverendLogoRender());
    }

    @Override
    protected void init() {
        if (splash == null) splash = NeverendSplash.getRandomSplash();
        if (!(panorama instanceof NeverendMenuBackground)) panorama = new NeverendMenuBackground();
        super.init();
    }

    @Override
    public void tick() {
        super.tick();
        time++;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShaderGameTime(time, pPartialTick);
        ShaderInstance shaderinstance = RenderSystem.getShader();

        if (shaderinstance.GAME_TIME != null) {
            shaderinstance.GAME_TIME.set(RenderSystem.getShaderGameTime());
        }

        shaderinstance.apply();
        pGuiGraphics.fill(RenderType.endPortal(), 0, 0, width, height, 0);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
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
    private static final int SPECIAL_SPLASHES = 4;

    public static final String[] SPLASHES = {
            "Purple is the new black!",
            "Nevermine in the End!",
            "THE END IS NEVER THE END IS NEVER THE END IS NEVER THE END IS NEVER THE END IS NEVER THE END IS NEVER THE END IS NEVER THE END IS NEVER", // FIXME: this renders really small
            "A friendly perdition!",
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
            "It's a bird! It's a plane!",
            "Level up!",
            "A sense of pride and accomplishment!",
            "Alex has joined the party!",
            "Have you seen the §kHerobrine§r?",
            ">:(!",
            "Can't touch this!",
            "Low risk of death or serious injury!",
            "Better with friends!",
            "You've got mail!",
            "Say hello to my little friend!",
            "Timeless!",
            "You just lost The Game!",
            "It's a trap!",
            "Nothing personnel, kid!",
            "No swearing on Christian servers!",
            "Human-generated!"

    };

    private boolean doubleTrouble = false;

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
        if (doubleTrouble) {
            pGuiGraphics.drawCenteredString(pFont, this.splash, 0, -3, BitUtil.rgbToInt(13, 82, 60) | pColor);
            pGuiGraphics.drawCenteredString(pFont, this.splash, 0, -13, BitUtil.rgbToInt(197, 54, 201) | pColor);
        } else {
            pGuiGraphics.drawCenteredString(pFont, this.splash, 0, -8, BitUtil.rgbToInt(13, 82, 60) | pColor);
        }
        pGuiGraphics.pose().popPose();
    }

    public static NeverendSplash getRandomSplash() {
        int pick = new Random().nextInt(SPLASHES.length + SPECIAL_SPLASHES);
        if (pick < SPECIAL_SPLASHES) {
            return getSpecialSplash(pick);

        }
        return new NeverendSplash(SPLASHES[pick - SPECIAL_SPLASHES]);
    }

    private static NeverendSplash getSpecialSplash(int id) {
        switch (id) {
            case 0: // nostalgic
                return new NeverendSplash(isNostalgic() ? "Nostalgic!" : "Not nostalgic yet!");
            case 1: // greeting
                String greeting;
                Calendar time = Calendar.getInstance();
                time.setTime(new Date());
                int hour = time.get(Calendar.HOUR_OF_DAY);
                if (hour >= 5 && hour < 12) {
                    greeting = "morning";
                } else if (hour >= 12 && hour < 18) {
                    greeting = "afternoon";
                } else {
                    greeting = "evening";
                }
                return new NeverendSplash("Good " + greeting + "!");
            case 2: // double trouble
                NeverendSplash splash = new NeverendSplash("Double trouble!");
                splash.doubleTrouble = true;
                return splash;
            case 3: // you're a wizard
                return new NeverendSplash("You're a wizard, " + Minecraft.getInstance().getUser().getName() + "!");
        }
        throw new IllegalArgumentException(id + " is not a valid special splash ID!");
    }

    private static boolean isNostalgic() {
        Calendar releaseDate = Calendar.getInstance();
        releaseDate.set(2024, Calendar.OCTOBER, 29); // TODO: update release date!
        releaseDate.add(Calendar.YEAR, 3);

        Calendar time = Calendar.getInstance();
        time.setTime(new Date());
        return time.after(releaseDate);
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
