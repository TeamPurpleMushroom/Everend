package net.purplemushroom.neverend.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.purplemushroom.neverend.Neverend;
import net.purplemushroom.neverend.content.entities.BaseRift;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import static net.purplemushroom.neverend.client.registry.NERenderTypes.getRiftPortalRenderType;
import static net.purplemushroom.neverend.client.registry.NERenderTypes.getVoidStarsTriRenderType;

public class BaseRiftRenderer<T extends BaseRift> extends Entity2DRenderer<T> {
    private static final float PORTAL_RADIUS = 0.432f;
    private static final float PORTAL_SUBDIVISIONS = 10.0f;

    private static final RenderType FISHING_RENDER_TYPE = getRiftPortalRenderType();
    private static final RenderType VOID_RENDER_TYPE = getVoidStarsTriRenderType();

    private final RenderType riftCloudRenderType;
    private final RenderType riftCloudOutsideRenderType;
    private final RiftType riftType;

    public BaseRiftRenderer(EntityRendererProvider.Context pContext, RiftType riftType) {
        super(pContext, (RenderType) null);
        riftCloudRenderType = RenderType.entityTranslucentCull(Neverend.tl("entity/rift.png").fullLocation());
        riftCloudOutsideRenderType = RenderType.entityTranslucentCull(Neverend.tl("entity/rift_outside.png").fullLocation());
        this.riftType = riftType;
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.translate(0, pEntity.getBbHeight() / 2, 0);
        pPoseStack.scale(getScale(), getScale(), getScale());
        pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        PoseStack.Pose pose = pPoseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        VertexConsumer vertexConsumer;
        int packedLightLevel;
        if (isFullBright()) packedLightLevel = 15728880;
        else packedLightLevel = pPackedLight;

        vertexConsumer = pBuffer.getBuffer(riftType == RiftType.FISHING ? FISHING_RENDER_TYPE : VOID_RENDER_TYPE);

        float angleOffset = Mth.TWO_PI / PORTAL_SUBDIVISIONS;
        for (double f = 0.0f; f <= Mth.TWO_PI; f += angleOffset) {
            Vec2 vector = new Vec2((float) Math.cos(f), (float) Math.sin(f));

            portalVertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, vector.x * PORTAL_RADIUS, vector.y * PORTAL_RADIUS);
            portalVertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.0f, 0.0f);
            portalVertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, vector.x * PORTAL_RADIUS, vector.y * PORTAL_RADIUS);
        }

        vertexConsumer = pBuffer.getBuffer(riftCloudOutsideRenderType);
        pPoseStack.mulPose(Axis.ZP.rotation((float) (pEntity.tickCount / 4) * 5));
        vertexBG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.65F, -0.65F, 0, 1);
        vertexBG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.65F, -0.65F, 1, 1);
        vertexBG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.65F, 0.65F, 1, 0);
        vertexBG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.65F, 0.65F, 0, 0);

        vertexConsumer = pBuffer.getBuffer(riftCloudRenderType);
        pPoseStack.mulPose(Axis.ZP.rotation((float) (pEntity.tickCount / 4) / 5));
        vertexFG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.5F, -0.5F, 0, 1);
        vertexFG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.5F, -0.5F, 1, 1);
        vertexFG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.5F, 0.5F, 1, 0);
        vertexFG(vertexConsumer, matrix4f, matrix3f, packedLightLevel, -0.5F, 0.5F, 0, 0);
        pPoseStack.popPose();
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
    public @NotNull ResourceLocation getTextureLocation(@NotNull T pEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }

    public enum RiftType {
        FISHING,
        VOID,
        ENEMY
    }
}
