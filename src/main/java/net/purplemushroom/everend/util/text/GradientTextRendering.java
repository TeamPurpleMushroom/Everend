package net.purplemushroom.everend.util.text;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public class GradientTextRendering extends CustomTextRendering {
    private final int gradientColor;
    private final GradientDirection gradientDirection;

    public GradientTextRendering(int gradientColor, GradientDirection direction) {
        super();
        this.gradientColor = Font.adjustColor(gradientColor);
        this.gradientDirection = direction;
    }

    public GradientTextRendering(Font mainFont, int gradientColor, GradientDirection direction) {
        super(mainFont);
        this.gradientColor = gradientColor;
        this.gradientDirection = direction;
    }

    @Override
    protected void render(BakedGlyph glyph, float pX, float pY, float red, float green, float blue, float alpha, boolean shadow, float shadowFactor, boolean italic, Matrix4f matrix, VertexConsumer buffer, int light) {
        float gradientRed = (float)(gradientColor >> 16 & 255) / 255.0F * shadowFactor;
        float gradientGreen = (float)(gradientColor >> 8 & 255) / 255.0F * shadowFactor;
        float gradientBlue = (float)(gradientColor & 255) / 255.0F * shadowFactor;

        float left = pX + glyph.left;
        float right = pX + glyph.right;
        float f2 = glyph.up - 3.0F;
        float f3 = glyph.down - 3.0F;
        float top = pY + f2;
        float bottom = pY + f3;
        float italicTopOffset = italic ? 1.0F - 0.25F * f2 : 0.0F;
        float italicBottomOffset = italic ? 1.0F - 0.25F * f3 : 0.0F;

        if (gradientDirection == GradientDirection.CENTER) {
            float middle = (top + bottom) / 2;
            float vMid = (glyph.v0 + glyph.v1) / 2;

            buffer.vertex(matrix, left + italicTopOffset, top, 0.0F).color(red, green, blue, alpha).uv(glyph.u0, glyph.v0).uv2(light).endVertex();
            buffer.vertex(matrix, left + italicBottomOffset, middle, 0.0F).color(gradientRed, gradientGreen, gradientBlue, alpha).uv(glyph.u0, vMid).uv2(light).endVertex();
            buffer.vertex(matrix, right + italicBottomOffset, middle, 0.0F).color(gradientRed, gradientGreen, gradientBlue, alpha).uv(glyph.u1, vMid).uv2(light).endVertex();
            buffer.vertex(matrix, right + italicTopOffset, top, 0.0F).color(red, green, blue, alpha).uv(glyph.u1, glyph.v0).uv2(light).endVertex();

            buffer.vertex(matrix, left + italicTopOffset, middle, 0.0F).color(gradientRed, gradientGreen, gradientBlue, alpha).uv(glyph.u0, vMid).uv2(light).endVertex();
            buffer.vertex(matrix, left + italicBottomOffset, bottom, 0.0F).color(red, green, blue, alpha).uv(glyph.u0, glyph.v1).uv2(light).endVertex();
            buffer.vertex(matrix, right + italicBottomOffset, bottom, 0.0F).color(red, green, blue, alpha).uv(glyph.u1, glyph.v1).uv2(light).endVertex();
            buffer.vertex(matrix, right + italicTopOffset, middle, 0.0F).color(gradientRed, gradientGreen, gradientBlue, alpha).uv(glyph.u1, vMid).uv2(light).endVertex();
        } else {
            float redTop, greenTop, blueTop;
            float redBottom, greenBottom, blueBottom;

            if (gradientDirection == GradientDirection.DOWN) {
                redTop = red;
                greenTop = green;
                blueTop = blue;
                redBottom = gradientRed;
                greenBottom = gradientGreen;
                blueBottom = gradientBlue;
            } else {
                redTop = gradientRed;
                greenTop = gradientGreen;
                blueTop = gradientBlue;
                redBottom = red;
                greenBottom = green;
                blueBottom = blue;
            }

            buffer.vertex(matrix, left + italicTopOffset, top, 0.0F).color(redTop, greenTop, blueTop, alpha).uv(glyph.u0, glyph.v0).uv2(light).endVertex();
            buffer.vertex(matrix, left + italicBottomOffset, bottom, 0.0F).color(redBottom, greenBottom, blueBottom, alpha).uv(glyph.u0, glyph.v1).uv2(light).endVertex();
            buffer.vertex(matrix, right + italicBottomOffset, bottom, 0.0F).color(redBottom, greenBottom, blueBottom, alpha).uv(glyph.u1, glyph.v1).uv2(light).endVertex();
            buffer.vertex(matrix, right + italicTopOffset, top, 0.0F).color(redTop, greenTop, blueTop, alpha).uv(glyph.u1, glyph.v0).uv2(light).endVertex();
        }
    }

    public enum GradientDirection {
        UP,
        CENTER,
        DOWN
    }
}
