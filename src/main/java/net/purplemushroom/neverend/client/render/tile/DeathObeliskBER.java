package net.purplemushroom.neverend.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.purplemushroom.neverend.client.render.NERenderTypes;
import net.purplemushroom.neverend.content.blocks.tile.DeathObeliskBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class DeathObeliskBER implements BlockEntityRenderer<DeathObeliskBlockEntity> {
    public DeathObeliskBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(@NotNull DeathObeliskBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Matrix4f $$6 = pPoseStack.last().pose();
        this.renderCube(pBlockEntity, $$6, pBuffer.getBuffer(this.renderType()));
    }

    private void renderCube(DeathObeliskBlockEntity pBlockEntity, Matrix4f pPose, VertexConsumer pConsumer) {
        float size = 0.99F;
        float offset = 1.0F - size;
        this.renderFace(pBlockEntity, pPose, pConsumer, offset, size, offset, size, size, size, size, size, Direction.SOUTH);
        this.renderFace(pBlockEntity, pPose, pConsumer, offset, size, size, offset, offset, offset, offset, offset, Direction.NORTH);
        this.renderFace(pBlockEntity, pPose, pConsumer, size, size, size, offset, offset, size, size, offset, Direction.EAST);
        this.renderFace(pBlockEntity, pPose, pConsumer, offset, offset, offset, size, offset, size, size, offset, Direction.WEST);
    }

    private void renderFace(DeathObeliskBlockEntity pBlockEntity, Matrix4f pPose, VertexConsumer pConsumer, float pX0, float pX1, float pY0, float pY1, float pZ0, float pZ1, float pZ2, float pZ3, Direction pDirection) {
        if (pBlockEntity.shouldRenderFace(pDirection)) {
            pConsumer.vertex(pPose, pX0, pY0, pZ0).endVertex();
            pConsumer.vertex(pPose, pX1, pY0, pZ1).endVertex();
            pConsumer.vertex(pPose, pX1, pY1, pZ2).endVertex();
            pConsumer.vertex(pPose, pX0, pY1, pZ3).endVertex();
        }
    }

    protected RenderType renderType() {
        return NERenderTypes.VOID_STARS_QUADS_RENDER_TYPE;
    }

    public int getViewDistance() {
        return 256;
    }
}
