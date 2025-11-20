package net.purplemushroom.everend.mixin.inject.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Style;
import net.purplemushroom.everend.util.text.CustomTextRendering;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Font.StringRenderOutput.class)
public class FontMixin {
    @Final
    @Shadow
    MultiBufferSource bufferSource;

    @Final
    @Shadow
    private boolean dropShadow;

    @Final
    @Shadow
    private float dimFactor;

    @Final
    @Shadow
    private Font.DisplayMode mode;

    @WrapOperation(
            method = "accept",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;renderChar(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;ZZFFFLorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFI)V")
    )
    private void test(Font instance, BakedGlyph pGlyph, boolean pBold, boolean pItalic, float pBoldOffset, float pX, float pY, Matrix4f pMatrix, VertexConsumer pBuffer, float pRed, float pGreen, float pBlue, float pAlpha, int pPackedLight, Operation<Void> original, @Local VertexConsumer vertexconsumer) {
        if (instance instanceof CustomTextRendering customText) {
            customText.renderChar(
                    pGlyph,
                    pBold,
                    pItalic,
                    pBoldOffset,
                    pX,
                    pY,
                    pMatrix,
                    customText.makeVertexConsumer(vertexconsumer, pGlyph, mode),
                    mode,
                    pRed,
                    pGreen,
                    pBlue,
                    pAlpha,
                    dropShadow,
                    dimFactor,
                    pPackedLight
            );
        } else {
            original.call(instance, pGlyph, pBold, pItalic, pBoldOffset, pX, pY, pMatrix, pBuffer, pRed, pGreen, pBlue, pAlpha, pPackedLight);
        }
    }
}
