package net.purplemushroom.neverend.mixin.client.accessor;

import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.client.gui.TitleScreenModUpdateIndicator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = { TitleScreen.class }, remap = false)
public interface TitleScreenAccessor {
    @Accessor("modUpdateNotification")
    void setModUpdateNotification(TitleScreenModUpdateIndicator titleScreenModUpdateIndicator);
}
