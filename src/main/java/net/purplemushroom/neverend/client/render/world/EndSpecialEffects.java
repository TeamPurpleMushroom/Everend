package net.purplemushroom.neverend.client.render.world;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.neverend.client.registry.NEShaderRegistry;
import org.joml.Matrix4f;

public class EndSpecialEffects extends DimensionSpecialEffects.EndEffects {
    private VertexBuffer neOverlayBuffer;
    private static final int DIVISIONS = 50;
    private static final ResourceLocation END_SKY_LOCATION = new ResourceLocation("textures/environment/end_sky.png");

    public EndSpecialEffects() {
        super();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        if (this.neOverlayBuffer != null) {
            this.neOverlayBuffer.close();
        }

        this.neOverlayBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = this.generateNEOverlayBuffer(bufferbuilder);
        this.neOverlayBuffer.bind();
        this.neOverlayBuffer.upload(bufferbuilder$renderedbuffer);
        VertexBuffer.unbind();
    }

    private void addSkyboxVertex(BufferBuilder builder, float phi, float theta) {
        float u = theta / Mth.TWO_PI;
        float v = phi / Mth.PI;

        Vec3 vector = new Vec3(Mth.sin(phi) * Mth.cos(theta), Mth.cos(phi), Mth.sin(phi) * Mth.sin(theta));
        vector = vector.normalize().scale(100);
        builder
                .vertex(vector.x, vector.y, vector.z)
                .uv(u, v)
                .endVertex();
    }

    private BufferBuilder.RenderedBuffer generateNEOverlayBuffer(BufferBuilder builder) {
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        for (float phi = 0.0f; phi < Mth.PI; phi += Mth.PI / DIVISIONS) {
            for (float theta = 0.0f; theta < Mth.TWO_PI; theta += Mth.TWO_PI / DIVISIONS) {
                float nextPhi = phi + Mth.PI / DIVISIONS, nextTheta = theta + Mth.TWO_PI / DIVISIONS;

                addSkyboxVertex(builder, phi, theta);
                addSkyboxVertex(builder, nextPhi, theta);
                addSkyboxVertex(builder, nextPhi, nextTheta);
                addSkyboxVertex(builder, phi, nextTheta);
                /*vector = new Vec3(Mth.sin(phi) * Mth.cos(theta), Mth.sin(phi) * Mth.sin(theta), Mth.cos(phi));
                vector = vector.normalize().scale(100);
                pBuilder.vertex(vector.x, vector.y, vector.z).endVertex();

                vector = new Vec3(Mth.sin(nextPhi) * Mth.cos(theta), Mth.sin(nextPhi) * Mth.sin(theta), Mth.cos(nextPhi));
                vector = vector.normalize().scale(100);
                pBuilder.vertex(vector.x, vector.y, vector.z).endVertex();

                vector = new Vec3(Mth.sin(nextPhi) * Mth.cos(nextTheta), Mth.sin(nextPhi) * Mth.sin(nextTheta), Mth.cos(nextPhi));
                vector = vector.normalize().scale(100);
                pBuilder.vertex(vector.x, vector.y, vector.z).endVertex();

                vector = new Vec3(Mth.sin(phi) * Mth.cos(nextTheta), Mth.sin(phi) * Mth.sin(nextTheta), Mth.cos(phi));
                vector = vector.normalize().scale(100);
                pBuilder.vertex(vector.x, vector.y, vector.z).endVertex();*/
            }
        }

        return builder.end();
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




        /*
        THIS IS DEBUG CODE THAT CONSTANTLY REFRESHES THE SKYBOX BUFFER
        tesselator = Tesselator.getInstance();
        bufferbuilder = tesselator.getBuilder();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        if (this.neOverlayBuffer != null) {
            this.neOverlayBuffer.close();
        }

        this.neOverlayBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = this.generateNEOverlayBuffer(bufferbuilder);
        this.neOverlayBuffer.bind();
        this.neOverlayBuffer.upload(bufferbuilder$renderedbuffer);
        VertexBuffer.unbind();*/



        // render NE overlay
        Vec3 cameraPos = camera.getPosition();
        if (Math.sqrt(cameraPos.x * cameraPos.x + cameraPos.z * cameraPos.z) > 700) { // ensure player is at the outer islands
            Vec3 skyColor = level.getSkyColor(cameraPos, partialTick); // if we give biomes their own sky colors, they'll affect this!

            // TODO: maybe have a datapack or smth to give the vanilla end biomes the below color?
            if (skyColor.equals(Vec3.ZERO)) skyColor = new Vec3(0.08, 0.35, 0.34); // default to this color. If we decide not to keep the tint then ¯\_(ツ)_/¯

            poseStack.pushPose();
            RenderSystem.setShaderColor((float) skyColor.x, (float) skyColor.y, (float) skyColor.z, 0.3f);
            //poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
            //FogRenderer.setupNoFog();

            this.neOverlayBuffer.bind();
            this.neOverlayBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionShader());
            this.neOverlayBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, NEShaderRegistry.getShaderEndSky());

            VertexBuffer.unbind();

            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            poseStack.popPose();
        }
        RenderSystem.depthMask(true);

        RenderSystem.disableBlend();

        return true;
    }
}
