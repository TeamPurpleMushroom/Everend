#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

uniform vec2 InSize;

uniform float Frequency;
uniform float Amplitude;
uniform float Time;
uniform float Speed;

out vec4 fragColor;

void main() {
    float offset = sin(texCoord.y * Frequency + Time * Speed) * Amplitude * (texCoord.x - 0.5) * 2;

    fragColor = texture(DiffuseSampler, vec2(texCoord.x + offset, texCoord.y));
}
