package net.purplemushroom.neverend.mixin.client.invoker;

import net.minecraft.client.renderer.GameRenderer;
import net.purplemushroom.neverend.client.isometric.IsometricCameraHandler;
import net.purplemushroom.neverend.config.NEClientConfig;
import net.purplemushroom.neverend.config.NEConfigs;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GameRenderer.class)
public abstract class LevelRendererMixin {
    @ModifyArg(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;prepareCullFrustum(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/phys/Vec3;Lorg/joml/Matrix4f;)V"
            ),
            index = 2
    )
    private Matrix4f swapFrustumMatrix(Matrix4f matrix) {
        NEClientConfig clientConfig = NEConfigs.CLIENT;
        NEClientConfig.GuiCategory guiCategory = clientConfig.guiCategory;
        if (guiCategory.isIsometricFOVEnabled()) {
            return IsometricCameraHandler.createIsometricMatrix(1.0F, 20.0F);
        }
        return matrix;
    }

/*    @ModifyArg(
            method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lorg/joml/Matrix4f;)V"
            ),
            index = 7
    )
    private Matrix4f swapMatrix(Matrix4f pProjectionMatrix) {
        NEClientConfig clientConfig = NEConfigs.CLIENT;
        NEClientConfig.GuiCategory guiCategory = clientConfig.guiCategory;
        if (guiCategory.isIsometricFOVEnabled()) {
            Matrix4f mat = IsometricCameraHandler.createOrthoMatrix((float) IsometricCameraHandler.DELTA, 0.0F);
            RenderSystem.setProjectionMatrix(mat, VertexSorting.ORTHOGRAPHIC_Z);
        }
        return pProjectionMatrix;
    }*/
}