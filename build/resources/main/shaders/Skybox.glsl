#vertex

#version 330 core

layout (location=0) in vec3 vertex_position;
layout (location=1) in vec2 vertex_uv;
layout (location=2) in vec3 vertex_normal;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;

out vec3 uv;

void main() {
    uv = normalize(vertex_position);
    vec4 world_position = transform * vec4(vertex_position, 1);
    vec4 pos = projection * view * world_position;
    gl_Position = pos;
}

#fragment

#version 330 core

in vec3 uv;

uniform samplerCube texture_sampler;

out vec4 out_color;

void main() {
    out_color = texture(texture_sampler, uv);
}