#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;
uniform float BlurRadius;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 sum = vec4(0.0);
    float blurSize = 2.0 / InSize.x;

    for (int x = -4; x <= 4; ++x) {
        for (int y = -4; y <= 4; ++y) {
            vec2 offset = vec2(x, y) * blurSize;
            sum += texture(DiffuseSampler, texCoord + offset);
        }
    }

    fragColor = sum / 81.0;
}