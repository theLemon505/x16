#vertex

#version 330 core

layout (location=0) in vec3 vertex_position;
layout (location=1) in vec2 vertex_uv;
layout (location=2) in vec3 vertex_normal;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;

out vec2 uv;

void main() {
    uv = vertex_uv;
    vec4 world_position = transform * vec4(vertex_position, 1);
    gl_Position = projection * view * world_position;
}

#fragment

#version 330 core

in vec2 uv;

uniform sampler2D texture_sampler;
uniform float specular;
uniform float damp;

out vec4 out_color;

void main() {
    out_color = texture(texture_sampler, uv);
}