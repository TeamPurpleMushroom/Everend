#version 150

#moj_import <neverend:perlin.glsl>

uniform vec4 ColorModulator;

in vec2 texCoord0;

out vec4 fragColor;

void main() {
    float col = perlin2DLoop(texCoord0, 5.0, 1.0, 1.0);
    col += perlin2DLoop(texCoord0, 10.0, 0.5, 1.0);
    col += perlin2DLoop(texCoord0, 20.0, 0.25, 1.0);
    col += perlin2DLoop(texCoord0, 40.0, 0.125, 1.0);
    col += perlin2DLoop(texCoord0, 80.0, 0.0625, 1.0);
    col += perlin2DLoop(texCoord0, 160.0, 0.03125, 1.0);
    col += perlin2DLoop(texCoord0, 320.0, 0.015625, 1.0);
    col = clamp(col, 0.0, 1.0);
    fragColor = vec4(col, col, col, 0.3);
}
