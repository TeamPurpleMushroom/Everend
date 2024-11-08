#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

uniform float GameTime;
uniform int VoidStarLayers;

in vec4 texProj0;

const vec3[] COLORS = vec3[](
vec3(0.004, 0., 0.012),
vec3(0.255, 0.02, 0.322),
vec3(0.004, 0., 0.012),
vec3(0.361, 0.035, 0.424),
vec3(0.004, 0., 0.012),
vec3(0.004, 0., 0.012),
vec3(0.004, 0., 0.012),
vec3(0.345, 0.082, 0.71),
vec3(0.004, 0., 0.012),
vec3(0.714, 0.176, 0.871),
vec3(0.004, 0., 0.012),
vec3(0.004, 0., 0.012),
vec3(0.004, 0., 0.012),
vec3(0.596, 0.424, 0.906),
vec3(0.004, 0., 0.012),
vec3(0.624, 0.482, 1.)
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

    mat2 rotate = mat2_rotate_z(radians((layer * layer * 4321.0 + layer * 9.0) * 2.0));

    mat2 scale = mat2((3.5 - layer / 3.0) * 3.0);

    return mat4(scale * rotate) * translate * SCALE_TRANSLATE;
}

out vec4 fragColor;

void main() {
    vec3 color = textureProj(Sampler0, texProj0).rgb * COLORS[0];
    for (int i = 0; i < VoidStarLayers; i++) {
        color += textureProj(Sampler1, texProj0 * end_portal_layer(float(i + 1))).rgb * COLORS[i];
    }
    fragColor = vec4(color, 1.0);
}