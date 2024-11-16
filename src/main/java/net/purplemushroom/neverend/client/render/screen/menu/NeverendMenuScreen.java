package net.purplemushroom.neverend.client.render.screen.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.ShaderInstance;
import net.purplemushroom.neverend.client.NEShaderRegistry;
import net.purplemushroom.neverend.client.render.NERenderTypes;
import net.purplemushroom.neverend.util.BitUtil;

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
        RenderSystem.setShader(NERenderTypes.Shaders::getShaderMenu);
        RenderSystem.setShaderGameTime(time, pPartialTick);
        ShaderInstance shaderinstance = RenderSystem.getShader();

        if (shaderinstance.GAME_TIME != null) {
            shaderinstance.GAME_TIME.set(RenderSystem.getShaderGameTime());

            int test = (int) (time / 120);
            test = Math.max(0, test - 1);
            System.out.println(test);
            shaderinstance.getUniform("IsUpperLayer").set(test);
            pGuiGraphics.drawCenteredString(Minecraft.getInstance().font, String.valueOf(test), 100, 100, BitUtil.rgbToInt(255, 255, 255));
        }

        shaderinstance.apply();
        pGuiGraphics.fill(NERenderTypes.getMenuRenderType(), 0, 0, width, height, 0);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}

