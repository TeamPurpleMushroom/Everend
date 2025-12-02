package net.purplemushroom.everend.util.text;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.joml.Matrix4f;

public abstract class CustomTextRendering extends Font {
    public CustomTextRendering() {
        this(Minecraft.getInstance().font);
    }

    public CustomTextRendering(Font mainFont) {
        super(mainFont.fonts, mainFont.filterFishyGlyphs);

    }

    public final void render(BakedGlyph pGlyph, int index, boolean pBold, boolean pItalic, float pBoldOffset, float pX, float pY, Matrix4f pMatrix, VertexConsumer buffer, DisplayMode mode, float pRed, float pGreen, float pBlue, float pAlpha, boolean shadow, int pPackedLight) {
        renderChar(pGlyph, index, pX, pY, pRed, pGreen, pBlue, pAlpha, shadow, pItalic, pMatrix, buffer, pPackedLight);

        if (pBold) {
            renderChar(pGlyph, index, pX + pBoldOffset, pY, pRed, pGreen, pBlue, pAlpha, shadow, pItalic, pMatrix, buffer, pPackedLight);
        }
    }

    public VertexConsumer makeVertexConsumer(VertexConsumer original, BakedGlyph glyph, DisplayMode mode) {
        return original;
    }

    protected void renderChar(BakedGlyph glyph, int index, float pX, float pY, float red, float green, float blue, float alpha, boolean shadow, boolean italic, Matrix4f matrix, VertexConsumer buffer, int light) {
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

    public void renderEffect(BakedGlyph glyph, BakedGlyph.Effect pEffect, Matrix4f pMatrix, VertexConsumer pBuffer, float originalCharX, float originalCharY, boolean isShadow, int pPackedLight) {
        pBuffer.vertex(pMatrix, pEffect.x0, pEffect.y0, pEffect.depth).color(pEffect.r, pEffect.g, pEffect.b, pEffect.a).uv(glyph.u0, glyph.v0).uv2(pPackedLight).endVertex();
        pBuffer.vertex(pMatrix, pEffect.x1, pEffect.y0, pEffect.depth).color(pEffect.r, pEffect.g, pEffect.b, pEffect.a).uv(glyph.u0, glyph.v1).uv2(pPackedLight).endVertex();
        pBuffer.vertex(pMatrix, pEffect.x1, pEffect.y1, pEffect.depth).color(pEffect.r, pEffect.g, pEffect.b, pEffect.a).uv(glyph.u1, glyph.v1).uv2(pPackedLight).endVertex();
        pBuffer.vertex(pMatrix, pEffect.x0, pEffect.y1, pEffect.depth).color(pEffect.r, pEffect.g, pEffect.b, pEffect.a).uv(glyph.u1, glyph.v0).uv2(pPackedLight).endVertex();
    }
}
