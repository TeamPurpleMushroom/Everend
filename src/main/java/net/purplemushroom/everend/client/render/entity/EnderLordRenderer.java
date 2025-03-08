package net.purplemushroom.everend.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CarriedBlockLayer;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.everend.content.entities.EnderLord;

public class EnderLordRenderer extends MobRenderer<EnderLord, EnderLordModel<EnderLord>> {
    private static final ResourceLocation ENDERMAN_LOCATION = new ResourceLocation("textures/entity/enderman/enderman.png");
    private final RandomSource random = RandomSource.create();

    public EnderLordRenderer(EntityRendererProvider.Context p_173992_) {
        super(p_173992_, new EnderLordModel<>(p_173992_.bakeLayer(ModelLayers.ENDERMAN)), 0.5F);
        //this.addLayer(new EnderEyesLayer<>(this));
        //this.addLayer(new CarriedBlockLayer(this, p_173992_.getBlockRenderDispatcher()));
    }

    public void render(EnderLord pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        EnderLordModel<EnderLord> enderlordmodel = this.getModel();
        enderlordmodel.carrying = false;
        enderlordmodel.creepy = pEntity.isCreepy();
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    public Vec3 getRenderOffset(EnderLord pEntity, float pPartialTicks) {
        if (pEntity.isCreepy()) {
            double d0 = 0.02D;
            return new Vec3(this.random.nextGaussian() * 0.02D, 0.0D, this.random.nextGaussian() * 0.02D);
        } else {
            return super.getRenderOffset(pEntity, pPartialTicks);
        }
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getTextureLocation(EnderLord pEntity) {
        return ENDERMAN_LOCATION;
    }
}