package net.purplemushroom.neverend.mixin.accessor;

import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.client.gui.TitleScreenModUpdateIndicator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = { TitleScreen.class }, remap = false)
public interface TitleScreenAccessor {
    @Accessor(value = "modUpdateNotification")
    static void getModUpdateNotification(TitleScreenModUpdateIndicator modUpdateNotification) {
        throw new AssertionError();
    }
}
