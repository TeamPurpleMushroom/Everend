package net.purplemushroom.everend.util;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.LivingEntity;
import net.purplemushroom.everend.client.registry.EEMusic;
import net.purplemushroom.everend.client.registry.EERenderTypes;
import net.purplemushroom.everend.client.registry.EEShaderRegistry;
import org.joml.Matrix4f;

import java.awt.*;

public class EverendBossInfo<T extends LivingEntity> {
    private static final ResourceLocation GUI_BARS_LOCATION = new ResourceLocation("textures/gui/bars.png");

    T boss;
    Font font;
    int textColor;
    float primaryRed, primaryGreen, primaryBlue, primaryAlpha;
    float secondaryRed, secondaryGreen, secondaryBlue, secondaryAlpha;
    Music music;

    public EverendBossInfo(T boss, Font text, int textColorModifier, Color primaryFogColor, Color secondaryFogColor, Music music) {
        this.boss = boss;

        this.font = text;
        textColor = textColorModifier;

        primaryRed = (float)primaryFogColor.getRed() / 255;
        primaryGreen = (float)primaryFogColor.getGreen() / 255;
        primaryBlue = (float)primaryFogColor.getBlue() / 255;
        primaryAlpha = (float)primaryFogColor.getAlpha() / 255;

        secondaryRed = (float)secondaryFogColor.getRed() / 255;
        secondaryGreen = (float)secondaryFogColor.getGreen() / 255;
        secondaryBlue = (float)secondaryFogColor.getBlue() / 255;
        secondaryAlpha = (float)secondaryFogColor.getAlpha() / 255;

        this.music = music;
    }

    public void renderBar(GuiGraphics graphics, int x, int y) {
        Matrix4f matrix = graphics.pose().last().pose();

        // set up shader colors
        RenderSystem.setShader(EEShaderRegistry::getShaderBossBar);
        ShaderInstance shaderInstance = RenderSystem.getShader();
        if (shaderInstance != null) {
            Uniform primaryColor = shaderInstance.getUniform("PrimaryColor");
            Uniform secondaryColor = shaderInstance.getUniform("SecondaryColor");
            if (primaryColor != null && secondaryColor != null) {
                primaryColor.set(primaryRed, primaryGreen, primaryBlue, primaryAlpha);
                secondaryColor.set(secondaryRed, secondaryGreen, secondaryBlue, secondaryAlpha);
                shaderInstance.apply();
            }
        }

        VertexConsumer vertexconsumer = graphics.bufferSource().getBuffer(EERenderTypes.getBossBarRenderType());
        vertexconsumer.vertex(matrix, (float)(x - 3), (float)(y - 3), -90.0f).uv(0.0f, 0.0f).endVertex();
        vertexconsumer.vertex(matrix, (float)(x - 3), (float)(y + 5 + 3), -90.0f).uv(0.0f, 0.048387f).endVertex();
        vertexconsumer.vertex(matrix, (float)(x + 182 + 3), (float)(y + 5 + 3), -90.0f).uv(1.0f, 0.048387f).endVertex();
        vertexconsumer.vertex(matrix, (float)(x + 182 + 3), (float)(y - 3), -90.0f).uv(1.0f, 0.0f).endVertex();

        this.renderBarInternal(graphics, x, y, 182, 0);
        int i = (int)((boss.getHealth() / boss.getMaxHealth()) * 183.0F);
        if (i > 0) {
            this.renderBarInternal(graphics, x, y, i, 5);
        }
        Component component = boss.getDisplayName();
        int l = font.width(component);
        int i1 = graphics.guiWidth() / 2 - l / 2;
        int j1 = y - 9;
        graphics.drawString(font, component, i1, j1, textColor);
        RenderSystem.enableDepthTest();
    }

    private void renderBarInternal(GuiGraphics pGuiGraphics, int pX, int pY, int pWidth, int p_281636_) {
        pGuiGraphics.blit(GUI_BARS_LOCATION, pX, pY, 0, 6 * 5 * 2 + p_281636_, pWidth, 5);
    }

    public Music getMusicTrack() {
        return music;
    }
}
