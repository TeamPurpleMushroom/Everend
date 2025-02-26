package net.purplemushroom.neverend.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.purplemushroom.neverend.content.blocks.tile.EndAltarBlockEntity;

public class EndAltarBER implements BlockEntityRenderer<EndAltarBlockEntity> {
    private final ItemRenderer itemRenderer;

    public EndAltarBER(BlockEntityRendererProvider.Context context) {
        itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(EndAltarBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        ItemStack item = blockEntity.getItem();
        Level level = blockEntity.getLevel();
        //BakedModel model = itemRenderer.getModel(item, blockEntity.getLevel(), null, 0);
        if (level != null && !item.isEmpty()) {

            float time = partialTick + level.getGameTime();
            poseStack.pushPose();
            poseStack.translate(0.5f, 1.6f, 0.5f);
            poseStack.translate(0.0f, 0.1f * Mth.sin(time / 10), 0.0f);
            poseStack.scale(0.5f, 0.5f, 0.5f);
            poseStack.mulPose(Axis.YP.rotationDegrees(time));
            this.itemRenderer.renderStatic(
                    item,
                    ItemDisplayContext.FIXED,
                    LevelRenderer.getLightColor(level, blockEntity.getBlockState(), blockEntity.getBlockPos().relative(Direction.UP)),//packedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack, buffer,
                    blockEntity.getLevel(),
                    0);
            poseStack.popPose();
        }
    }
}
