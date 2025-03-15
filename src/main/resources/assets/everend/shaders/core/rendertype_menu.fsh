#version 150

#moj_import <matrix.glsl>
#moj_import <everend:perlin.glsl>

uniform sampler2D Sampler0;

uniform float GameTime;
uniform float Seed;

in vec4 texProj0;

const vec3[] COLORS = vec3[](
vec3(0.584,0.,1.),
vec3(0.255, 0.02, 0.322),
vec3(0.29, 0., 0.549),
vec3(0.361, 0.035, 0.424),
vec3(0.004, 0., 0.012),
vec3(0.004, 0., 0.012),
vec3(0.004, 0., 0.012),
vec3(0.345, 0.082, 0.71),
vec3(0.004, 0., 0.012),
vec3(0.714, 0.176, 0.871)
);

const mat4 SCALE_TRANSLATE = mat4(
1.0, 0.0, 0.0, 0.5,
0.0, 1.0, 0.0, 0.5,
0.0, 0.0, 1.5, 0.0,
0.0, 0.0, 0.0, 1.5
);

mat4 end_portal_layer(float layer) {
    mat4 translate = mat4(
    1.0, 0.0, 0.0, 17.0 / layer,
    0.0, 1.0, 0.0, (2.0 + layer / 1.5) * (GameTime * 1.5),
    0.0, 0.0, 1.0, 0.0,
    0.0, 0.0, 0.0, 1.0
    );

    mat2 rotate = mat2_rotate_z(radians((layer * layer * 4321.0 + layer * 9.0) * 2.0 * Seed));

    mat2 scale = mat2(layer * 0.75);

    return mat4(scale * rotate) * translate * SCALE_TRANSLATE;
}

out vec4 fragColor;

void main() {
    vec3 samplePoint = vec3(texProj0.xy, GameTime * 50.0);

    float thickness = perlin3D(samplePoint, 2.0, 1.0);
    thickness += perlin3D(samplePoint, 4.0, 0.5);
    thickness += perlin3D(samplePoint, 8.0, 0.25);
    thickness += perlin3D(samplePoint, 16.0, 0.125);
    thickness += perlin3D(samplePoint, 32.0, 0.0625);
    thickness += perlin3D(samplePoint, 64.0, 0.03125);
    thickness += perlin3D(samplePoint, 128.0, 0.015625);
    thickness += perlin3D(samplePoint, 256.0, 0.0078125);
    thickness /= 1.9921875;
    thickness = clamp(thickness + 0.4, 0.0, 1.0);
    //thickness = (thickness + 1.0) / 2.0;

    float hue = perlin3D(samplePoint, 1.0, 0.5) + 0.5;

    vec3 color = vec3(thickness) * mix(vec3(0.82, 0.18, 0.66), vec3(0.247, 0.0588, 0.8588), hue);

    for (int i = 0; i < 10; i++) {
        color += textureProj(Sampler0, texProj0 * end_portal_layer(float(i + 1))).rgb * COLORS[i];
    }
    fragColor = vec4(color, 1.0);
}