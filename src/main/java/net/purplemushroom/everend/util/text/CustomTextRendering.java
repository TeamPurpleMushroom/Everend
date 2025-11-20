package net.purplemushroom.everend.util.text;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public abstract class CustomTextRendering extends Font {
    public CustomTextRendering(Font mainFont) {
        super(mainFont.fonts, mainFont.filterFishyGlyphs);
    }

    public final void renderChar(BakedGlyph pGlyph, boolean pBold, boolean pItalic, float pBoldOffset, float pX, float pY, Matrix4f pMatrix, VertexConsumer buffer, DisplayMode mode, float pRed, float pGreen, float pBlue, float pAlpha, boolean shadow, float shadowFactor, int pPackedLight) {
        render(pGlyph, pX, pY, pRed, pGreen, pBlue, pAlpha, shadow, shadowFactor, pItalic, pMatrix, buffer, pPackedLight);
        //pGlyph.render(pItalic, pX, pY, pMatrix, buffer, pRed, pGreen, pBlue, pAlpha, pPackedLight);

        if (pBold) {
            render(pGlyph, pX + pBoldOffset, pY, pRed, pGreen, pBlue, pAlpha, shadow, shadowFactor, pItalic, pMatrix, buffer, pPackedLight);
            //pGlyph.render(pItalic, pX + pBoldOffset, pY, pMatrix, buffer, pRed, pGreen, pBlue, pAlpha, pPackedLight);
        }
    }

    public VertexConsumer makeVertexConsumer(VertexConsumer original, BakedGlyph glyph, DisplayMode mode) {
        return original;
    }

    protected void render(BakedGlyph glyph, float pX, float pY, float red, float green, float blue, float alpha, boolean shadow, float shadowFactor, boolean italic, Matrix4f matrix, VertexConsumer buffer, int light) {
        int i = 3;
        float f = pX + glyph.left;
        float f1 = pX + glyph.right;
        float f2 = glyph.up - 3.0F;
        float f3 = glyph.down - 3.0F;
        float f4 = pY + f2;
        float f5 = pY + f3;
        float f6 = italic ? 1.0F - 0.25F * f2 : 0.0F;
        float f7 = italic ? 1.0F - 0.25F * f3 : 0.0F;

        buffer.vertex(matrix, f + f6, f4, 0.0F).color(red, green, blue, alpha).uv(glyph.u0, glyph.v0).uv2(light).endVertex();
        buffer.vertex(matrix, f + f7, f5, 0.0F).color(red, green, blue, alpha).uv(glyph.u0, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, f1 + f7, f5, 0.0F).color(red, green, blue, alpha).uv(glyph.u1, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, f1 + f6, f4, 0.0F).color(red, green, blue, alpha).uv(glyph.u1, glyph.v0).uv2(light).endVertex();
    }
}
