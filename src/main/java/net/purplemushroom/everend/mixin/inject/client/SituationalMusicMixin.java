package net.purplemushroom.everend.mixin.inject.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.purplemushroom.everend.Everend;
import net.purplemushroom.everend.client.registry.EESoundRegistry;
import net.purplemushroom.everend.client.render.screen.menu.NeverendMenuScreen;
import net.purplemushroom.everend.content.blocks.EndAnchorBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Minecraft.class)
public class SituationalMusicMixin {
    @Shadow @Final public Gui gui;

    @Inject(method = "getSituationalMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/BossHealthOverlay;shouldPlayMusic()Z"), cancellable = true)
    private void defineCustomEndMusic(CallbackInfoReturnable<Music> cir) {
        if(true) {
            cir.setReturnValue(NeverendMenuScreen.BOSS_MUSIC);
        }
    }
}