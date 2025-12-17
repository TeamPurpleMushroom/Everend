package net.purplemushroom.everend.util.text;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public class GradientTextRendering extends CustomTextRendering {
    private final int gradientTopColor;
    private final int gradientBottomColor;

    public GradientTextRendering(int gradientTop, int gradientBottom) {
        super();
        this.gradientTopColor = Font.adjustColor(gradientTop);
        this.gradientBottomColor = Font.adjustColor(gradientBottom);
    }

    public GradientTextRendering(Font mainFont, int gradientTop, int gradientBottom) {
        super(mainFont);
        this.gradientTopColor = Font.adjustColor(gradientTop);
        this.gradientBottomColor = Font.adjustColor(gradientBottom);
    }

    private float getTopRed(float multiplier) {
        return (float)(gradientTopColor >> 16 & 255) / 255.0F * multiplier;
    }

    private float getTopGreen(float multiplier) {
        return (float)(gradientTopColor >> 8 & 255) / 255.0F * multiplier;
    }

    private float getTopBlue(float multiplier) {
        return (float)(gradientTopColor & 255) / 255.0F * multiplier;
    }

    private float getBottomRed(float multiplier) {
        return (float)(gradientBottomColor >> 16 & 255) / 255.0F * multiplier;
    }

    private float getBottomGreen(float multiplier) {
        return (float)(gradientBottomColor >> 8 & 255) / 255.0F * multiplier;
    }

    private float getBottomBlue(float multiplier) {
        return (float)(gradientBottomColor & 255) / 255.0F * multiplier;
    }

    @Override
    protected void renderChar(BakedGlyph glyph, int index, float pX, float pY, float red, float green, float blue, float alpha, boolean shadow, boolean italic, Matrix4f matrix, VertexConsumer buffer, int light) {
        // the vanilla red, green, and blue values already account for the shadowFactor
        float redTop = getTopRed(red);
        float greenTop = getTopGreen(green);
        float blueTop = getTopBlue(blue);
        float redBottom = getBottomRed(red);
        float greenBottom = getBottomGreen(green);
        float blueBottom = getBottomBlue(blue);

        float left = pX + glyph.left;
        float right = pX + glyph.right;
        float f2 = glyph.up - 3.0F;
        float f3 = glyph.down - 3.0F;
        float top = pY + f2;
        float bottom = pY + f3;
        float italicTopOffset = italic ? 1.0F - 0.25F * f2 : 0.0F;
        float italicBottomOffset = italic ? 1.0F - 0.25F * f3 : 0.0F;

        buffer.vertex(matrix, left + italicTopOffset, top, 0.0F).color(redTop, greenTop, blueTop, alpha).uv(glyph.u0, glyph.v0).uv2(light).endVertex();
        buffer.vertex(matrix, left + italicBottomOffset, bottom, 0.0F).color(redBottom, greenBottom, blueBottom, alpha).uv(glyph.u0, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, right + italicBottomOffset, bottom, 0.0F).color(redBottom, greenBottom, blueBottom, alpha).uv(glyph.u1, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, right + italicTopOffset, top, 0.0F).color(redTop, greenTop, blueTop, alpha).uv(glyph.u1, glyph.v0).uv2(light).endVertex();
    }

    @Override
    public void renderEffect(BakedGlyph glyph, BakedGlyph.Effect pEffect, Matrix4f pMatrix, VertexConsumer pBuffer, float originalCharX, float originalCharY, boolean isShadow, int pPackedLight) {
        float redTop = getTopRed(pEffect.r);
        float greenTop = getTopGreen(pEffect.g);
        float blueTop = getTopBlue(pEffect.b);
        float redBottom = getBottomRed(pEffect.r);
        float greenBottom = getBottomGreen(pEffect.g);
        float blueBottom = getBottomBlue(pEffect.b);

        // we will copy the code from the renderChar function to find the top and bottom y values of the actual character
        float f2 = glyph.up - 3.0F;
        float f3 = glyph.down - 3.0F;
        float originalGlyphTop = originalCharY + f2;
        float originalGlyphBottom = originalCharY + f3;
        float originalGlyphHeight = originalGlyphBottom - originalGlyphTop;

        // the top and bottom y values of the effect
        float effectTop = pEffect.y0;
        float effectBottom = pEffect.y1;

        // now we use interpolation to figure out what the color of the character's gradient is at the effect's top and bottom
        float fTop = (effectTop - originalGlyphTop) / originalGlyphHeight;
        float fBottom = (effectBottom - originalGlyphTop) / originalGlyphHeight;
        /*fTop = Mth.clamp(fTop, 0.0f, 1.0f);
        fBottom = Mth.clamp(fBottom, 0.0f, 1.0f);*/

        float rEffectTop = Mth.clamp(Mth.lerp(fTop, redTop, redBottom), 0.0f, 1.0f);
        float gEffectTop = Mth.clamp(Mth.lerp(fTop, greenTop, greenBottom), 0.0f, 1.0f);
        float bEffectTop = Mth.clamp(Mth.lerp(fTop, blueTop, blueBottom), 0.0f, 1.0f);
        float rEffectBottom = Mth.clamp(Mth.lerp(fBottom, redTop, redBottom), 0.0f, 1.0f);
        float gEffectBottom = Mth.clamp(Mth.lerp(fBottom, greenTop, greenBottom), 0.0f, 1.0f);
        float bEffectBottom = Mth.clamp(Mth.lerp(fBottom, blueTop, blueBottom), 0.0f, 1.0f);


        pBuffer.vertex(pMatrix, pEffect.x0, pEffect.y0, pEffect.depth).color(rEffectTop, gEffectTop, bEffectTop, pEffect.a).uv(glyph.u0, glyph.v0).uv2(pPackedLight).endVertex();
        pBuffer.vertex(pMatrix, pEffect.x1, pEffect.y0, pEffect.depth).color(rEffectTop, gEffectTop, bEffectTop, pEffect.a).uv(glyph.u0, glyph.v1).uv2(pPackedLight).endVertex();
        pBuffer.vertex(pMatrix, pEffect.x1, pEffect.y1, pEffect.depth).color(rEffectBottom, gEffectBottom, bEffectBottom, pEffect.a).uv(glyph.u1, glyph.v1).uv2(pPackedLight).endVertex();
        pBuffer.vertex(pMatrix, pEffect.x0, pEffect.y1, pEffect.depth).color(rEffectBottom, gEffectBottom, bEffectBottom, pEffect.a).uv(glyph.u1, glyph.v0).uv2(pPackedLight).endVertex();
    }
}
