#version 150

#moj_import <everend:perlin.glsl>

uniform vec4 ColorModulator;

in vec2 uv;

uniform float GameTime;

out vec4 fragColor;

const float fadeoffStart = 0.75; // the v distance from the poles at which clouds start fading out
const float fadeoffEnd = 0.85; // the v distance from the poles at which clouds are fully faded out
const float fadeoffDistance = fadeoffEnd - fadeoffStart; // represents the range of v values over which the polar fading is happening

void main() {
    // generate the base noise
    vec3 p = vec3(uv, GameTime * 5.0);
    float col = perlin3D(p, 5.0, 1.0);
    col += perlin3D(p, 10.0, 0.5);
    col += perlin3D(p, 20.0, 0.25);
    col += perlin3D(p, 40.0, 0.125);
    col += perlin3D(p, 80.0, 0.0625);
    col += perlin3D(p, 160.0, 0.03125);
    col += perlin3D(p, 320.0, 0.015625);
    /*float col = perlin2DLoop(uv, 5.0, 1.0, 1.0);
    col += perlin2DLoop(uv, 10.0, 0.5, 1.0);
    col += perlin2DLoop(uv, 20.0, 0.25, 1.0);
    col += perlin2DLoop(uv, 40.0, 0.125, 1.0);
    col += perlin2DLoop(uv, 80.0, 0.0625, 1.0);
    col += perlin2DLoop(uv, 160.0, 0.03125, 1.0);
    col += perlin2DLoop(uv, 320.0, 0.015625, 1.0);*/

    // modulate it
    col = 1.0 - abs(col / 1.984375); // this creates the veiny shapes. It also normalizes the previously generated noise
    col = col * col; // the accentuates the veins by making small values smaller
    col = clamp(col, 0.0, 1.0); // ensure it's normalized

    // fade out the noise near the poles in order to hide the polar distortion
    float closenessToPoles = abs(0.5 - uv.y) / 0.5; // from [0.0, 1.0], where 0.0 is the equator & 1.0 is the poles
    if (closenessToPoles > fadeoffStart) {
        closenessToPoles -= fadeoffStart;
        col = mix(col, 0.9, clamp(closenessToPoles / fadeoffDistance, 0.0, 1.0));
    }

    col = floor(col * 30.0) / 30.0; // simplify the color palette to make it look less blurry and more minecrafty

    fragColor = vec4(0.015, 0.3294, 0.28627, 0.4) * col; // calculate the final color
}
