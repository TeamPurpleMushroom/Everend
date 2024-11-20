package net.purplemushroom.neverend.mixin.client.accessor;

import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = { GameRenderer.class })
public interface GameRendererAccessor {
    @Accessor("zoom")
    void setZoom(float zoom);
}












