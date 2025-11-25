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
    public CustomTextRendering() {
        this(Minecraft.getInstance().font);
    }

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
        float left = pX + glyph.left;
        float right = pX + glyph.right;
        float f2 = glyph.up - 3.0F;
        float f3 = glyph.down - 3.0F;
        float top = pY + f2;
        float bottom = pY + f3;
        float italicTopOffset = italic ? 1.0F - 0.25F * f2 : 0.0F;
        float italicBottomOffset = italic ? 1.0F - 0.25F * f3 : 0.0F;

        buffer.vertex(matrix, left + italicTopOffset, top, 0.0F).color(red, green, blue, alpha).uv(glyph.u0, glyph.v0).uv2(light).endVertex();
        buffer.vertex(matrix, left + italicBottomOffset, bottom, 0.0F).color(red, green, blue, alpha).uv(glyph.u0, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, right + italicBottomOffset, bottom, 0.0F).color(red, green, blue, alpha).uv(glyph.u1, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, right + italicTopOffset, top, 0.0F).color(red, green, blue, alpha).uv(glyph.u1, glyph.v0).uv2(light).endVertex();
    }
}
