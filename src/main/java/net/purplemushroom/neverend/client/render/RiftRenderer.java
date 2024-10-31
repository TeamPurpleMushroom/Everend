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
import net.minecraft.world.entity.Entity;
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
        pPoseStack.scale(scale, scale, scale);
        pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        pPoseStack.mulPose(Axis.ZP.rotation((pPartialTicks + pEntity.tickCount) / 40));

        PoseStack.Pose pose = pPoseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.endPortal());
        int packedLightLevel;
        if (fullBright) packedLightLevel = 15728880;
        else packedLightLevel = pPackedLight;

        // not a perfect square so that it fits inside the portal
        portalVertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.31F, -0.32F);
        portalVertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.32F, -0.32F);
        portalVertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.32F, 0.32F);
        portalVertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.29F, 0.32F);

        vertexConsumer = pBuffer.getBuffer(renderType);

        vertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.5F, -0.5F, 0, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.5F, -0.5F, 1, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.5F, 0.5F, 1, 0);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.5F, 0.5F, 0, 0);
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

    private static void vertex(VertexConsumer pConsumer, Matrix4f pPose, Matrix3f pNormal, int pLightmapUV, float pX, float pY, int pU, int pV) {
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
