package net.purplemushroom.everend.util.text;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public class GradientTextRendering extends CustomTextRendering {
    public GradientTextRendering(Font mainFont) {
        super(mainFont);
    }

    @Override
    protected void render(BakedGlyph glyph, float pX, float pY, float red, float green, float blue, float alpha, boolean italic, Matrix4f matrix, VertexConsumer buffer, int light) {
        float bottomRed = Mth.lerp(0.25f, red, 1.0f);
        float bottomGreen = Mth.lerp(0.25f, green, 1.0f);
        float bottomBlue = Mth.lerp(0.25f, blue, 1.0f);

        red = Mth.lerp(0.25f, red, 0.0f);
        green = Mth.lerp(0.25f, green, 0.0f);
        blue = Mth.lerp(0.25f, blue, 0.0f);

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
        buffer.vertex(matrix, f + f7, f5, 0.0F).color(bottomRed, bottomGreen, bottomBlue, alpha).uv(glyph.u0, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, f1 + f7, f5, 0.0F).color(bottomRed, bottomGreen, bottomBlue, alpha).uv(glyph.u1, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, f1 + f6, f4, 0.0F).color(red, green, blue, alpha).uv(glyph.u1, glyph.v0).uv2(light).endVertex();
    }
}
