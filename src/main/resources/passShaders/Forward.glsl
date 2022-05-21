#vertex

#version 330 core

layout (location=0) in vec3 vertex_position;
layout (location=1) in vec2 vertex_uv;

out vec2 uv;

void main() {
    gl_Position = vec4(vertex_position, 1);
    uv = vertex_uv;
}

#fragment

#version 330 core

in vec2 uv;

uniform sampler2D texture_sampler;

out vec4 out_color;

void main() {
    out_color = texture(texture_sampler, uv);
}