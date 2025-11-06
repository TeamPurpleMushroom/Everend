package net.purplemushroom.everend.mixin.inject.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.purplemushroom.everend.util.shader.ShaderManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameRenderer.class)
public class GameRendererMixin {
    @Final
    @Shadow
    Minecraft minecraft;

    @Inject(method = "close", at = @At("TAIL"))
    private void closeShaders(CallbackInfo ci) {
        ShaderManager.closeShaders();
    }

    @Inject(method = "resize", at = @At("HEAD"))
    private void resizeShaders(int width, int height, CallbackInfo ci) {
        ShaderManager.resize(width, height);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/pipeline/RenderTarget;bindWrite(Z)V"))
    private void processShadersPreGUI(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        ShaderManager.processPreGUI(pPartialTicks);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;"))
    private void processShadersPostGUIExcludingScreen(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        if (!waitForScreen()) {
            ShaderManager.processPostGUI(pPartialTicks);
            this.minecraft.getMainRenderTarget().bindWrite(false);
        }
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeHooksClient;drawScreen(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/client/gui/GuiGraphics;IIF)V", shift = At.Shift.AFTER))
    private void processShadersPostGUIIncluding(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        if (waitForScreen()) {
            ShaderManager.processPostGUI(pPartialTicks);
            this.minecraft.getMainRenderTarget().bindWrite(false);
        }
    }

    private boolean waitForScreen() {
        return this.minecraft.screen != null && !this.minecraft.screen.isPauseScreen() && this.minecraft.screen.shouldCloseOnEsc();
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void processShadersPostEverything(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        ShaderManager.processEverything(pPartialTicks);
        this.minecraft.getMainRenderTarget().bindWrite(false);
    }
}
