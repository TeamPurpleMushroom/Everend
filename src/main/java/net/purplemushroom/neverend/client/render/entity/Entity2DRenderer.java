package net.purplemushroom.neverend.client.render.entity;

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
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import ru.timeconqueror.timecore.api.client.resource.location.TextureLocation;

public class Entity2DRenderer<T extends Entity> extends EntityRenderer<T> {
    private final RenderType renderType;
    private float scale;
    private boolean fullBright = false;
    private boolean projectile = false;
    private int blockLightLevel;

    public Entity2DRenderer(EntityRendererProvider.Context pContext, RenderType renderType) {
        super(pContext);
        this.renderType = renderType;
    }

    public Entity2DRenderer(EntityRendererProvider.Context pContext, TextureLocation textureLocation) {
        this(pContext, RenderType.entityCutoutNoCull(textureLocation.fullLocation()));
    }

    @Override
    protected int getBlockLightLevel(T pEntity, BlockPos pPos) {
        return blockLightLevel;
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (projectile) {
            if (pEntity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(pEntity) < 12.25D)) {
                renderProjectile(pPoseStack, pBuffer, pPackedLight);
            }
        } else {
            renderProjectile(pPoseStack, pBuffer, pPackedLight);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    public void renderProjectile(PoseStack pPoseStack, MultiBufferSource pBuffer, int packedLightIn) {
        pPoseStack.pushPose();
        pPoseStack.scale(scale, scale, scale);
        pPoseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        PoseStack.Pose pose = pPoseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        VertexConsumer vertexConsumer = pBuffer.getBuffer(renderType);
        int packedLightLevel;
        if (fullBright) packedLightLevel = 15728880;
        else packedLightLevel = packedLightIn;
        vertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.0F, 0, 0, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 1.0F, 0, 1, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 1.0F, 1, 1, 0);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLightLevel, 0.0F, 1, 0, 0);
        pPoseStack.popPose();
    }

    public Entity2DRenderer<T> blockLightLevel(int blockLightLevel) {
        this.blockLightLevel = blockLightLevel;
        return this;
    }

    public int getBlockLightLevel() {
        return blockLightLevel;
    }

    public Entity2DRenderer<T> scale(float scale) {
        this.scale = scale;
        return this;
    }

    public float getScale() {
        return this.scale;
    }

    public Entity2DRenderer<T> fullbright(boolean fullbright) {
        this.fullBright = fullbright;
        return this;
    }

    public boolean isFullBright() {
        return this.fullBright;
    }

    public Entity2DRenderer<T> projectile(boolean projectile) {
        this.projectile = projectile;
        return this;
    }

    public boolean isProjectile() {
        return projectile;
    }

    private static void vertex(VertexConsumer pConsumer, Matrix4f pPose, Matrix3f pNormal, int pLightmapUV, float pX, int pY, int pU, int pV) {
        pConsumer.vertex(pPose, pX - 0.5F, (float) pY - 0.25F, 0.0F).color(255, 255, 255, 255).uv((float) pU, (float) pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(pLightmapUV).normal(pNormal, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}