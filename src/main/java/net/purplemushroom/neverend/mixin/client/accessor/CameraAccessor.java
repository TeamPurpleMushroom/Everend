package net.purplemushroom.neverend.mixin.client.accessor;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = { Camera.class })
public interface CameraAccessor {
/*    @Invoker("setPosition")
    void setPosition(double pX, double pY, double pZ);

    @Invoker("setRotation")
    void setRotation(float pYRot, float pXRot);*/
}









