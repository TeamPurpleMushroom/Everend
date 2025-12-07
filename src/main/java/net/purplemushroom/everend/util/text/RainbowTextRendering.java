package net.purplemushroom.everend.util.text;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.joml.Matrix4f;

import java.awt.*;

public class RainbowTextRendering extends CustomTextRendering {
    public RainbowTextRendering() {
        super();
    }

    public RainbowTextRendering(Font mainFont) {
        super(mainFont);
    }

    private Color getColor(double time, float x) {
        float hue = (float) ((time + -x / 50) % 1.0f);
        return Color.getHSBColor(hue, 1.0f, 1.0f);
    }

    @Override
    protected void renderChar(BakedGlyph glyph, int index, float pX, float pY, float red, float green, float blue, float alpha, boolean shadow, boolean italic, Matrix4f matrix, VertexConsumer buffer, int light) {
        float left = pX + glyph.left;
        float right = pX + glyph.right;
        float f2 = glyph.up - 3.0F;
        float f3 = glyph.down - 3.0F;
        float top = pY + f2;
        float bottom = pY + f3;
        float italicTopOffset = italic ? 1.0F - 0.25F * f2 : 0.0F;
        float italicBottomOffset = italic ? 1.0F - 0.25F * f3 : 0.0F;

        double time = System.nanoTime() / 1e9;
        Color colLeft = getColor(time, left);
        Color colRight = getColor(time, right);

        float rl = red * (float) colLeft.getRed() / 255.0f;
        float gl = green * (float) colLeft.getGreen() / 255.0f;
        float bl = blue * (float) colLeft.getBlue() / 255.0f;

        float rr = red * (float) colRight.getRed() / 255.0f;
        float gr = green * (float) colRight.getGreen() / 255.0f;
        float br = blue * (float) colRight.getBlue() / 255.0f;

        buffer.vertex(matrix, left + italicTopOffset, top, 0.0F).color(rl, gl, bl, alpha).uv(glyph.u0, glyph.v0).uv2(light).endVertex();
        buffer.vertex(matrix, left + italicBottomOffset, bottom, 0.0F).color(rl, gl, bl, alpha).uv(glyph.u0, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, right + italicBottomOffset, bottom, 0.0F).color(rr, gr, br, alpha).uv(glyph.u1, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, right + italicTopOffset, top, 0.0F).color(rr, gr, br, alpha).uv(glyph.u1, glyph.v0).uv2(light).endVertex();
    }

    @Override
    public void renderEffect(BakedGlyph glyph, BakedGlyph.Effect pEffect, Matrix4f pMatrix, VertexConsumer pBuffer, float originalCharX, float originalCharY, boolean isShadow, int pPackedLight) {
        double time = System.nanoTime() / 1e9;
        Color colLeft = getColor(time, pEffect.x0);
        Color colRight = getColor(time, pEffect.x1);

        float rl = pEffect.r * (float) colLeft.getRed() / 255.0f;
        float gl = pEffect.g * (float) colLeft.getGreen() / 255.0f;
        float bl = pEffect.b * (float) colLeft.getBlue() / 255.0f;

        float rr = pEffect.r * (float) colRight.getRed() / 255.0f;
        float gr = pEffect.g * (float) colRight.getGreen() / 255.0f;
        float br = pEffect.b * (float) colRight.getBlue() / 255.0f;


        pBuffer.vertex(pMatrix, pEffect.x0, pEffect.y0, pEffect.depth).color(rl, gl, bl, pEffect.a).uv(glyph.u0, glyph.v0).uv2(pPackedLight).endVertex();
        pBuffer.vertex(pMatrix, pEffect.x1, pEffect.y0, pEffect.depth).color(rr, gr, br, pEffect.a).uv(glyph.u0, glyph.v1).uv2(pPackedLight).endVertex();
        pBuffer.vertex(pMatrix, pEffect.x1, pEffect.y1, pEffect.depth).color(rr, gr, br, pEffect.a).uv(glyph.u1, glyph.v1).uv2(pPackedLight).endVertex();
        pBuffer.vertex(pMatrix, pEffect.x0, pEffect.y1, pEffect.depth).color(rl, gl, bl, pEffect.a).uv(glyph.u1, glyph.v0).uv2(pPackedLight).endVertex();
    }
}
