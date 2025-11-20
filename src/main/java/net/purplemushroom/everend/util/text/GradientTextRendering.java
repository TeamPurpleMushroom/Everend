package net.purplemushroom.everend.util.text;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public class GradientTextRendering extends CustomTextRendering {
    private final int gradientColor;

    public GradientTextRendering(Font mainFont, int gradientColor) {
        super(mainFont);
        this.gradientColor = Font.adjustColor(gradientColor);
    }

    @Override
    protected void render(BakedGlyph glyph, float pX, float pY, float red, float green, float blue, float alpha, boolean shadow, float shadowFactor, boolean italic, Matrix4f matrix, VertexConsumer buffer, int light) {
        float gr = (float)(gradientColor >> 16 & 255) / 255.0F * shadowFactor;
        float gg = (float)(gradientColor >> 8 & 255) / 255.0F * shadowFactor;
        float gb = (float)(gradientColor & 255) / 255.0F * shadowFactor;

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
        buffer.vertex(matrix, f + f7, f5, 0.0F).color(gr, gg, gb, alpha).uv(glyph.u0, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, f1 + f7, f5, 0.0F).color(gr, gg, gb, alpha).uv(glyph.u1, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, f1 + f6, f4, 0.0F).color(red, green, blue, alpha).uv(glyph.u1, glyph.v0).uv2(light).endVertex();
    }
}
