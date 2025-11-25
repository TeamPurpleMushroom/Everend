package net.purplemushroom.everend.util.text;

import com.mojang.blaze3d.systems.RenderSystem;
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

    @Override
    protected void render(BakedGlyph glyph, float pX, float pY, float red, float green, float blue, float alpha, boolean shadow, float shadowFactor, boolean italic, Matrix4f matrix, VertexConsumer buffer, int light) {
        float left = pX + glyph.left;
        float right = pX + glyph.right;
        float f2 = glyph.up - 3.0F;
        float f3 = glyph.down - 3.0F;
        float top = pY + f2;
        float bottom = pY + f3;
        float italicTopOffset = italic ? 1.0F - 0.25F * f2 : 0.0F;
        float italicBottomOffset = italic ? 1.0F - 0.25F * f3 : 0.0F;

        double time = System.nanoTime() / 1e9;
        float hueLeft = (float) ((time + -left / 50) % 1.0f);
        float hueRight = (float) ((time + -right / 50) % 1.0f);
        Color colLeft = Color.getHSBColor(hueLeft, 1.0f, 1.0f);
        Color colRight = Color.getHSBColor(hueRight, 1.0f, 1.0f);

        float rl = shadowFactor * (float) colLeft.getRed() / 255.0f;
        float gl = shadowFactor * (float) colLeft.getGreen() / 255.0f;
        float bl = shadowFactor * (float) colLeft.getBlue() / 255.0f;

        float rr = shadowFactor * (float) colRight.getRed() / 255.0f;
        float gr = shadowFactor * (float) colRight.getGreen() / 255.0f;
        float br = shadowFactor * (float) colRight.getBlue() / 255.0f;

        buffer.vertex(matrix, left + italicTopOffset, top, 0.0F).color(rl, gl, bl, alpha).uv(glyph.u0, glyph.v0).uv2(light).endVertex();
        buffer.vertex(matrix, left + italicBottomOffset, bottom, 0.0F).color(rl, gl, bl, alpha).uv(glyph.u0, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, right + italicBottomOffset, bottom, 0.0F).color(rr, gr, br, alpha).uv(glyph.u1, glyph.v1).uv2(light).endVertex();
        buffer.vertex(matrix, right + italicTopOffset, top, 0.0F).color(rr, gr, br, alpha).uv(glyph.u1, glyph.v0).uv2(light).endVertex();
    }
}
