package net.purplemushroom.neverend.client.render.screen.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.ShaderInstance;
import net.purplemushroom.neverend.client.render.NERenderTypes;

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
        pGuiGraphics.fill(NERenderTypes.getVoidStarsQuadsRenderType(), 0, 0, width, height, 0);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}

