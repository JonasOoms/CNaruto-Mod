#version 150

uniform sampler2D Sampler0; // Fill texture
uniform sampler2D Sampler1; // Alpha mask texture

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 fill = texture(Sampler0, texCoord);
    vec4 mask = texture(Sampler1, texCoord);
    fragColor = vec4(fill.rgb, fill.a * mask.a);
}