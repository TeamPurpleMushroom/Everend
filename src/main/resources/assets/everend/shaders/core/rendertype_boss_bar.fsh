#version 150

#moj_import <matrix.glsl>
#moj_import <everend:perlin.glsl>

in vec2 uv;

uniform float GameTime;

uniform vec4 PrimaryColor;
uniform vec4 SecondaryColor;

out vec4 fragColor;

void main() {
    vec2 samplePoint = vec2(uv.x + GameTime * 20, uv.y) * 6;

    float thickness = perlin2D(samplePoint, 2.0, 1.0);
    thickness += perlin2D(samplePoint, 4.0, 0.5);
    thickness += perlin2D(samplePoint, 8.0, 0.25);
    thickness += perlin2D(samplePoint, 16.0, 0.125);
    thickness += perlin2D(samplePoint, 32.0, 0.0625);
    thickness += perlin2D(samplePoint, 64.0, 0.03125);
    thickness += perlin2D(samplePoint, 128.0, 0.015625);
    thickness += perlin2D(samplePoint, 256.0, 0.0078125);
    thickness /= 1.9921875;

    thickness = (thickness + 1.0) / 2;
    //thickness = (thickness - 0.5) * 2;

    float uMultiplier = abs(uv.x - 0.5);
    if (uMultiplier < 0.5 - 0.02) {
        uMultiplier = 1.0;
    } else {
        uMultiplier = 1.0 - (uMultiplier - (0.5 - 0.02)) / 0.02;
    }

    float vMultiplier = abs(uv.y - 0.0241935);
    if (vMultiplier < 0.0241935 - 0.02) {
        vMultiplier = 1.0;
    } else {
        vMultiplier = 1.0 - (vMultiplier - (0.0241935 - 0.02)) / 0.02;
    }

    fragColor = (PrimaryColor - SecondaryColor) * thickness * thickness + SecondaryColor;
    fragColor.a *= uMultiplier * vMultiplier;
}