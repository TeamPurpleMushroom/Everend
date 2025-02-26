package net.purplemushroom.neverend.client.render.world;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.neverend.client.registry.NEShaderRegistry;
import org.joml.Matrix4f;

public class EndSpecialEffects extends DimensionSpecialEffects.EndEffects {
    private VertexBuffer starBuffer;
    private static final int DIVISIONS = 20;
    private static final ResourceLocation END_SKY_LOCATION = new ResourceLocation("textures/environment/end_sky.png");

    public EndSpecialEffects() {
        super();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        if (this.starBuffer != null) {
            this.starBuffer.close();
        }

        this.starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = this.drawStars(bufferbuilder);
        this.starBuffer.bind();
        this.starBuffer.upload(bufferbuilder$renderedbuffer);
        VertexBuffer.unbind();
    }

    private BufferBuilder.RenderedBuffer drawStars(BufferBuilder pBuilder) {
        pBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for (float phi = Mth.PI; phi < Mth.TWO_PI; phi += Mth.PI / DIVISIONS) {
            for (float theta = 0.0f; theta < Mth.TWO_PI; theta += Mth.TWO_PI / DIVISIONS) {
                Vec3 vector = new Vec3(
                        Mth.sin(phi) * Mth.cos(theta),
                        Mth.sin(phi) * Mth.sin(theta),
                        Mth.cos(phi));
                vector = vector.normalize().scale(100);
                pBuilder.vertex(vector.x, vector.y, vector.z).endVertex();

                vector = new Vec3(
                        Mth.sin(phi + Mth.PI / DIVISIONS) * Mth.cos(theta),
                        Mth.sin(phi + Mth.PI / DIVISIONS) * Mth.sin(theta),
                        Mth.cos(phi + Mth.PI / DIVISIONS));
                vector = vector.normalize().scale(100);
                pBuilder.vertex(vector.x, vector.y, vector.z).endVertex();

                vector = new Vec3(
                        Mth.sin(phi + Mth.PI / DIVISIONS) * Mth.cos(theta + Mth.TWO_PI / DIVISIONS),
                        Mth.sin(phi + Mth.PI / DIVISIONS) * Mth.sin(theta + Mth.TWO_PI / DIVISIONS),
                        Mth.cos(phi + Mth.PI / DIVISIONS));
                vector = vector.normalize().scale(100);
                pBuilder.vertex(vector.x, vector.y, vector.z).endVertex();

                vector = new Vec3(
                        Mth.sin(phi) * Mth.cos(theta + Mth.TWO_PI / DIVISIONS),
                        Mth.sin(phi) * Mth.sin(theta + Mth.TWO_PI / DIVISIONS),
                        Mth.cos(phi));
                vector = vector.normalize().scale(100);
                pBuilder.vertex(vector.x, vector.y, vector.z).endVertex();
            }
        }

        return pBuilder.end();
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);

        // render vanilla end sky
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, END_SKY_LOCATION);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        for(int i = 0; i < 6; ++i) {
            poseStack.pushPose();
            if (i == 1) {
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            }

            if (i == 2) {
                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            }

            if (i == 3) {
                poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            }

            if (i == 4) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
            }

            if (i == 5) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
            }

            Matrix4f matrix4f = poseStack.last().pose();
            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(40, 40, 40, 255).endVertex();
            bufferbuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).uv(0.0F, 16.0F).color(40, 40, 40, 255).endVertex();
            bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).uv(16.0F, 16.0F).color(40, 40, 40, 255).endVertex();
            bufferbuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).uv(16.0F, 0.0F).color(40, 40, 40, 255).endVertex();
            tesselator.end();
            poseStack.popPose();
        }

        // render NE overlay
        Entity player = Minecraft.getInstance().getCameraEntity();
        if (player != null) {
            Vec3 pos = player.getPosition(partialTick);
            if (Math.sqrt(pos.x * pos.x + pos.z * pos.z) > 500) {
                poseStack.pushPose();
                RenderSystem.setShaderColor(0.08f, 0.35f, 0.34f, 0.3f);
                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                //FogRenderer.setupNoFog();

                this.starBuffer.bind();
                this.starBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionShader());
                VertexBuffer.unbind();

                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                poseStack.popPose();
            }
        }
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();

        return true;
    }
}
