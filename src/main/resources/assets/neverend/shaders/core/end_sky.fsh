#version 150

#moj_import <neverend:perlin.glsl>

uniform vec4 ColorModulator;

in vec2 uv;

out vec4 fragColor;

const float fadeoffStart = 0.75; // the v distance from the poles at which clouds start fading out
const float fadeoffEnd = 0.85; // the v distance from the poles at which clouds are fully faded out
const float fadeoffDistance = fadeoffEnd - fadeoffStart; // represents the range of v values over which the polar fading is happening

void main() {
    float col = perlin2DLoop(uv, 5.0, 1.0, 1.0);
    col += perlin2DLoop(uv, 10.0, 0.5, 1.0);
    col += perlin2DLoop(uv, 20.0, 0.25, 1.0);
    col += perlin2DLoop(uv, 40.0, 0.125, 1.0);
    col += perlin2DLoop(uv, 80.0, 0.0625, 1.0);
    col += perlin2DLoop(uv, 160.0, 0.03125, 1.0);
    col += perlin2DLoop(uv, 320.0, 0.015625, 1.0);
    col = clamp(col, 0.0, 1.0);

    float closenessToPoles = abs(0.5 - uv.y) / 0.5; // from [0.0, 1.0], where 0.0 is the equator & 1.0 is the poles
    if (closenessToPoles > fadeoffStart) {
        closenessToPoles -= fadeoffStart;
        float factor = mix(1.0, 0.0, closenessToPoles / fadeoffDistance);
        col = clamp(col * factor, 0.0, 1.0);
    }
    fragColor = vec4(col);
}
