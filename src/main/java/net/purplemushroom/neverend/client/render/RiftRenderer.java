package net.purplemushroom.neverend.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.entities.Rift;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import ru.timeconqueror.timecore.api.client.resource.location.TextureLocation;

public class RiftRenderer extends EntityRenderer<Rift> {
    private final RenderType renderType;
    private float scale;
    private boolean fullBright = false;
    private boolean projectile = false;
    private int blockLightLevel;

    public RiftRenderer(EntityRendererProvider.Context pContext, TextureLocation texture) {
        super(pContext);
        renderType = RenderType.entityTranslucentCull(texture.fullLocation());
    }

    @Override
    protected int getBlockLightLevel(Rift pEntity, BlockPos pPos) {
        return blockLightLevel;
    }

    @Override
    public void render(Rift pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.translate(0, pEntity.getBbHeight() / 2, 0);
        pPoseStack.scale(scale, scale, scale);
        pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        PoseStack.Pose pose = pPoseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.endPortal());
        int packedLightLevel;
        if (fullBright) packedLightLevel = 15728880;
        else packedLightLevel = pPackedLight;

        // not a perfect square so that it fits inside the portal
        portalVertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.40F, -0.42F);
        portalVertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.42F, -0.42F);
        portalVertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.42F, 0.42F);
        portalVertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.38F, 0.42F);

        vertexConsumer = pBuffer.getBuffer(RenderType.entityTranslucentCull(Neverend.tl("entity/rift_outside.png").fullLocation()));
        pPoseStack.mulPose(Axis.ZP.rotation((float) (pEntity.tickCount / 4) * 5));
        vertexBG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.65F, -0.65F, 0, 1);
        vertexBG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.65F, -0.65F, 1, 1);
        vertexBG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.65F, 0.65F, 1, 0);
        vertexBG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.65F, 0.65F, 0, 0);

        vertexConsumer = pBuffer.getBuffer(renderType);
        pPoseStack.mulPose(Axis.ZP.rotation((float) (pEntity.tickCount / 4) / 5));
        vertexFG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.5F, -0.5F, 0, 1);
        vertexFG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.5F, -0.5F, 1, 1);
        vertexFG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.5F, 0.5F, 1, 0);
        vertexFG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.5F, 0.5F, 0, 0);
        pPoseStack.popPose();

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    public RiftRenderer scale(float scale) {
        this.scale = scale;
        return this;
    }

    public RiftRenderer fullbright(boolean fullbright) {
        this.fullBright = fullbright;
        return this;
    }

    public RiftRenderer projectile(boolean projectile) {
        this.projectile = projectile;
        return this;
    }

    private static void vertexFG(VertexConsumer pConsumer, Matrix4f pPose, Matrix3f pNormal, int pLightmapUV, float pX, float pY, int pU, int pV) {
        pConsumer.vertex(pPose, pX, pY, 0.01F).color(255, 255, 255, 255).uv((float) pU, (float) pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pLightmapUV).normal(pNormal, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private static void vertexBG(VertexConsumer pConsumer, Matrix4f pPose, Matrix3f pNormal, int pLightmapUV, float pX, float pY, int pU, int pV) {
        pConsumer.vertex(pPose, pX, pY, 0.0F).color(255, 255, 255, 255).uv((float) pU, (float) pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pLightmapUV).normal(pNormal, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private static void portalVertex(VertexConsumer pConsumer, Matrix4f pPose, Matrix3f pNormal, int pLightmapUV, float pX, float pY) {
        pConsumer.vertex(pPose, pX, pY, -0.01F).color(255, 255, 255, 255).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pLightmapUV).normal(pNormal, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(Rift pEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
