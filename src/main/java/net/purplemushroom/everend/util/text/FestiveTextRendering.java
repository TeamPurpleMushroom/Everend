package net.purplemushroom.everend.util.text;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.joml.Matrix4f;

public class FestiveTextRendering extends CustomTextRendering {
    private boolean swapped = false;

    public void setColorsSwapped(boolean swap) {
        this.swapped = swap;
    }

    @Override
    protected void renderChar(BakedGlyph glyph, int index, float pX, float pY, float red, float green, float blue, float alpha, boolean shadow, boolean italic, Matrix4f matrix, VertexConsumer buffer, int light) {
        boolean isGreen = (index / 3) % 2 == 0;
        if (swapped) isGreen = !isGreen;

        // the vanilla red, green, and blue values already account for the shadowFactor
        float redBottom;
        float greenBottom;
        float blueBottom;

        if (isGreen) {
            redBottom = 0.0f * red;
            greenBottom = 1.0f * green;
            blueBottom = 0.0f * blue;
        } else {
            redBottom = 1.0f * red;
            greenBottom = 0.0f * green;
            blueBottom = 0.0f * blue;
        }

        float left = pX + glyph.left;
        float right = pX + glyph.right;
        float f2 = glyph.up - 3.0F;
        float f3 = glyph.down - 3.0F;
        float top = pY + f2;
        float bottom = pY + f3;
        float italicTopOffset = italic ? 1.0F - 0.25F * f2 : 0.0F;
        float italicBottomOffset = italic ? 1.0F - 0.25F * f3 : 0.0F;

        buffer.vertex(matrix, left + italicTopOffset, top, 0.0F).color(red, green, blue, alpha).uv(glyph.u0, glyph.v0).uv2(light).endVertex();
        buffer.vertex(matrix, left + italicBottomOffset, bottom, 0.0F).color(redBottom, greenBottom, blueBottom, alpha).uv(glyph.u0, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, right + italicBottomOffset, bottom, 0.0F).color(redBottom, greenBottom, blueBottom, alpha).uv(glyph.u1, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, right + italicTopOffset, top, 0.0F).color(red, green, blue, alpha).uv(glyph.u1, glyph.v0).uv2(light).endVertex();
    }
}
