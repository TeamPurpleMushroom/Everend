package net.purplemushroom.neverend.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
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

            poseStack.pushPose();
            poseStack.translate(0.0f, 1.0f, 0.0f);
            this.itemRenderer.renderStatic(
                    item,
                    ItemDisplayContext.FIXED,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    poseStack, buffer,
                    blockEntity.getLevel(),
                    0);
            poseStack.popPose();
        }
    }
}
