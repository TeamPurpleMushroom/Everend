package net.purplemushroom.everend.mixin.accessor.client;

import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.client.gui.TitleScreenModUpdateIndicator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = { TitleScreen.class })
public interface TitleScreenAccessor {
    @Accessor(value = "modUpdateNotification", remap = false)
    void setModUpdateNotification(TitleScreenModUpdateIndicator titleScreenModUpdateIndicator);
}
