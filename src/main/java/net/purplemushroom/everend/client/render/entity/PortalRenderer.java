package net.purplemushroom.everend.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.everend.content.entities.Portal;
import org.joml.Matrix4f;

import static net.purplemushroom.everend.client.registry.EERenderTypes.*;

public class PortalRenderer extends EntityRenderer<Portal> {
    private static final RenderType RENDER_TYPE = getEnderLordPortalType();

    public PortalRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
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

        //face 1
        addVertex(vertexConsumer, pose, right.add(up));
        addVertex(vertexConsumer, pose, right.scale(-1.0).add(up));
        addVertex(vertexConsumer, pose, right.scale(-1.0).subtract(up));
        addVertex(vertexConsumer, pose, right.subtract(up));
        //face 2
        addVertex(vertexConsumer, pose, right.subtract(up));
        addVertex(vertexConsumer, pose, right.scale(-1.0).subtract(up));
        addVertex(vertexConsumer, pose, right.scale(-1.0).add(up));
        addVertex(vertexConsumer, pose, right.add(up));

        poseStack.popPose();
    }

    private void addVertex(VertexConsumer consumer, Matrix4f pose, Vec3 coords) {
        consumer.vertex(pose, (float) coords.x, (float) coords.y, (float) coords.z).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(Portal pEntity) {
        return null;
    }
}
