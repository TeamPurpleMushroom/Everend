#version 150

#moj_import <everend:perlin.glsl>

in vec2 uv;

uniform float GameTime;

out vec4 fragColor;

const float fadeoffStart = 0.75; // the v distance from the poles at which clouds start fading out
const float fadeoffEnd = 0.85; // the v distance from the poles at which clouds are fully faded out
const float fadeoffDistance = fadeoffEnd - fadeoffStart; // represents the range of v values over which the polar fading is happening

void main() {
    // generate the base noise
    vec3 sampleCoords = vec3(uv, GameTime * 5.0);
    vec3 loopConfig = vec3(1.0, -1.0, 5.0); // loop var for time is 5 since getShaderGameTime returns a float between 0 & 1, and we're multiplying it by 5
    float col = perlin3DLoop(sampleCoords, 5.0, 1.0, loopConfig);
    col += perlin3DLoop(sampleCoords, 10.0, 0.5, loopConfig);
    col += perlin3DLoop(sampleCoords, 20.0, 0.25, loopConfig);
    col += perlin3DLoop(sampleCoords, 40.0, 0.125, loopConfig);
    col += perlin3DLoop(sampleCoords, 80.0, 0.0625, loopConfig);
    col += perlin3DLoop(sampleCoords, 160.0, 0.03125, loopConfig);
    col += perlin3DLoop(sampleCoords, 320.0, 0.015625, loopConfig);

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
