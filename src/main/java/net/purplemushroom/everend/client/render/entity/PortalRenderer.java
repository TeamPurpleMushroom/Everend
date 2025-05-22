package net.purplemushroom.everend.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.content.entities.Portal;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import static net.purplemushroom.everend.client.registry.EERenderTypes.*;

public class PortalRenderer extends EntityRenderer<Portal> {
    private static final RenderType RENDER_TYPE = getEnderLordPortalType();
    private final RenderType frameRenderType;

    public PortalRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        frameRenderType = RenderType.entityTranslucentCull(Everend.tl("entity/mirror_frame.png").fullLocation());
    }

    @Override
    public boolean shouldRender(Portal pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public void render(Portal entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        // TODO: this code is kinda messy?Porta
        VertexConsumer vertexConsumer = buffer.getBuffer(RENDER_TYPE);
        poseStack.pushPose();
        poseStack.translate(0.0, entity.getBbHeight() * 0.5f, 0.0);

        Matrix4f pose = poseStack.last().pose();
        Vec3i rightNormal = entity.getDirection().getNormal().cross(Direction.UP.getNormal());
        Vec3 up = Vec3.atLowerCornerOf(Direction.UP.getNormal()).normalize().scale(entity.getBbHeight() * 0.5f);
        Vec3 right = Vec3.atLowerCornerOf(rightNormal).normalize().scale(0.5f);

        //PORTAL
        //face 1
        addVertex(vertexConsumer, pose, right.add(up));
        addVertex(vertexConsumer, pose, right.scale(-1.0F).add(up));
        addVertex(vertexConsumer, pose, right.scale(-1.0F).subtract(up));
        addVertex(vertexConsumer, pose, right.subtract(up));
        //face 2
        addVertex(vertexConsumer, pose, right.subtract(up));
        addVertex(vertexConsumer, pose, right.scale(-1.0F).subtract(up));
        addVertex(vertexConsumer, pose, right.scale(-1.0F).add(up));
        addVertex(vertexConsumer, pose, right.add(up));

        //FRAME
        int packedLightLevel = 15728880;
        vertexConsumer = buffer.getBuffer(frameRenderType);
        Matrix3f normal = pose.normal(poseStack.last().normal());
        //face 1
        addFrameVertex(vertexConsumer, pose, normal, packedLightLevel, right.add(up), 0, 1);
        addFrameVertex(vertexConsumer, pose, normal, packedLightLevel, right.scale(-1.01F).add(up), 1, 1);
        addFrameVertex(vertexConsumer, pose, normal, packedLightLevel, right.scale(-1.01F).subtract(up), 1, 0);
        addFrameVertex(vertexConsumer, pose, normal, packedLightLevel, right.subtract(up), 0, 0);
        //face 2
        addFrameVertex(vertexConsumer, pose, normal, packedLightLevel, right.subtract(up), 0, 1);
        addFrameVertex(vertexConsumer, pose, normal, packedLightLevel, right.scale(-1.01F).subtract(up), 1, 1);
        addFrameVertex(vertexConsumer, pose, normal, packedLightLevel, right.scale(-1.01F).add(up), 1, 0);
        addFrameVertex(vertexConsumer, pose, normal, packedLightLevel, right.add(up), 0, 0);

        poseStack.popPose();
    }

    private void addVertex(VertexConsumer consumer, Matrix4f pose, Vec3 coords) {
        consumer.vertex(pose, (float) coords.x, (float) coords.y, (float) coords.z).endVertex();
    }

    private static void addFrameVertex(VertexConsumer pConsumer, Matrix4f pose, Matrix3f normal, int lightmap, Vec3 coords, int u, int v) {
        pConsumer.vertex(pose, (float) coords.x, (float) coords.y, 0.0F).color(255, 255, 255, 255).uv((float) u, (float) v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightmap).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(Portal pEntity) {
        return null;
    }
}
