package net.purplemushroom.everend.mixin.inject.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.sounds.Music;
import net.purplemushroom.everend.client.registry.EEMusic;
import net.purplemushroom.everend.client.render.screen.menu.EverendMenuScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class SituationalMusicMixin {
    @Shadow @Final public Gui gui;

    @Inject(method = "getSituationalMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/BossHealthOverlay;shouldPlayMusic()Z"), cancellable = true)
    private void defineCustomEndMusic(CallbackInfoReturnable<Music> cir) {
        if(true) {
            cir.setReturnValue(EEMusic.ENDER_LORD);
        }
    }
}