package net.purplemushroom.neverend.mixin.client.invoker;

import net.minecraft.client.Camera;
import net.purplemushroom.neverend.config.NEClientConfig;
import net.purplemushroom.neverend.config.NEConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @ModifyVariable(method = "move", at = @At("HEAD"), index = 1, argsOnly = true)
    private double moveByHeadX(double value) {
        NEClientConfig clientConfig = NEConfigs.CLIENT;
        NEClientConfig.GuiCategory guiCategory = clientConfig.guiCategory;
        return guiCategory.isIsometricFOVEnabled() ? 0.0 : value;
    }

    @ModifyVariable(method = "move", at = @At("HEAD"), index = 3, argsOnly = true)
    private double moveByHeadZ(double value) {
        NEClientConfig clientConfig = NEConfigs.CLIENT;
        NEClientConfig.GuiCategory guiCategory = clientConfig.guiCategory;
        return guiCategory.isIsometricFOVEnabled() ? 0.0 : value;
    }
}