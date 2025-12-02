package net.purplemushroom.everend.mixin.inject.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.purplemushroom.everend.util.text.CustomTextRendering;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Font.StringRenderOutput.class)
public class FontMixin {
    @Final
    @Shadow
    MultiBufferSource bufferSource;

    @Final
    @Shadow
    private boolean dropShadow;

    @Shadow
    float x;

    @Shadow
    float y;

    @Final
    @Shadow
    private Font.DisplayMode mode;

    @Final
    @Shadow
    Font this$0;

    @Unique
    private int everend$charNum = 0;

    @WrapOperation(
            method = "accept",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;renderChar(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;ZZFFFLorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFI)V")
    )
    private void renderCustomFont(Font instance, BakedGlyph pGlyph, boolean pBold, boolean pItalic, float pBoldOffset, float pX, float pY, Matrix4f pMatrix, VertexConsumer pBuffer, float pRed, float pGreen, float pBlue, float pAlpha, int pPackedLight, Operation<Void> original, @Local VertexConsumer vertexconsumer) {
        if (instance instanceof CustomTextRendering customText) {
            customText.render(
                    pGlyph,
                    everend$charNum++,
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
                    pPackedLight
            );
        } else {
            original.call(instance, pGlyph, pBold, pItalic, pBoldOffset, pX, pY, pMatrix, pBuffer, pRed, pGreen, pBlue, pAlpha, pPackedLight);
        }
    }

    @WrapOperation(
            method = "finish",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;renderEffect(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph$Effect;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;I)V")
    )
    private void renderCustomFontEffect(BakedGlyph instance, BakedGlyph.Effect pEffect, Matrix4f pMatrix, VertexConsumer pBuffer, int pPackedLight, Operation<Void> original) {
        if (this$0 instanceof CustomTextRendering textRendering) {
            if (dropShadow) {
                /*
                We need to pass the x and y values of the original character that this effect rendered over.
                If we are rendering a shadow, the x and y values passed when rendering the character will have had an offset.
                This offset is typically 1.0f, but there can be exceptions for some special characters.
                Unfortunately, we can't be certain of the real offset without accessing the GlyphInfo used when rendering the character (which we don't have here).

                Vanilla seems to assume the shadow offset is 1.0f when rendering effects (see Font.StringRenderOutput, line 303).
                We will do the same here and assume the text offset was 1.0f.
                 */
                textRendering.renderEffect(instance, pEffect, pMatrix, pBuffer, this.x + 1.0f, this.y + 1.0f, true, pPackedLight);
            } else {
                textRendering.renderEffect(instance, pEffect, pMatrix, pBuffer, this.x, this.y, false, pPackedLight);
            }
        } else {
            original.call(instance, pEffect, pMatrix, pBuffer, pPackedLight);
        }
    }
}
