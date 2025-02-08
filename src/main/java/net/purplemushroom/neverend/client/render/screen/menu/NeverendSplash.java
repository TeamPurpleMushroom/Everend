package net.purplemushroom.neverend.client.render.screen.menu;

import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.util.Mth;
import net.purplemushroom.neverend.util.BitUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

class NeverendSplash extends SplashRenderer {
    private static final int SPECIAL_SPLASHES = 5;

    public static final int SPECIAL_SPLASH_NONE = 0;
    public static final int SPECIAL_SPLASH_DOUBLE_TROUBLE = 1;
    public static final int SPECIAL_SPLASH_END_IS_NEVER = 2;

    public static final String[] SPLASHES = {
            "Purple is the new black!",
            "Nevermine in the End!",
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
            "Human-generated!",
            "You love to see it!",
            "Wazzup!"
    };

    private int specialRenderType = SPECIAL_SPLASH_NONE;

    private NeverendSplash(String pSplash) {
        super(pSplash);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pScreenWidth, Font pFont, int pColor) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate((float) pScreenWidth / 2.0F + 123.0F, 69.0F, 0.0F);
        pGuiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-20.0F));
        float f = 1.8F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * ((float) Math.PI * 2F)) * 0.1F);
        int textWidth = pFont.width(this.splash);
        if (specialRenderType == SPECIAL_SPLASH_END_IS_NEVER) textWidth /= 10;
        f = f * 100.0F / (float) (textWidth + 32);
        pGuiGraphics.pose().scale(f, f, f);
        if (specialRenderType == SPECIAL_SPLASH_DOUBLE_TROUBLE) {
            pGuiGraphics.drawCenteredString(pFont, this.splash, 0, -3, BitUtil.rgbToInt(13, 82, 60) | pColor);
            pGuiGraphics.drawCenteredString(pFont, this.splash, 0, -13, BitUtil.rgbToInt(197, 54, 201) | pColor);
        } else {
            pGuiGraphics.drawCenteredString(pFont, this.splash, 0, -8, BitUtil.rgbToInt(13, 82, 60) | pColor);
        }
        pGuiGraphics.pose().popPose();
    }

    private NeverendSplash setType(int type) {
        specialRenderType = type;
        return this;
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
                return new NeverendSplash("Double trouble!").setType(SPECIAL_SPLASH_DOUBLE_TROUBLE);
            case 3: // you're a wizard
                return new NeverendSplash("You're a wizard, " + Minecraft.getInstance().getUser().getName() + "!");
            case 4: // THE END IS NEVER THE END
                return new NeverendSplash("THE END IS NEVER ".repeat(20)).setType(SPECIAL_SPLASH_END_IS_NEVER);
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
