#vertex

#version 330 core

layout (location=0) in vec3 vertex_position;
layout (location=1) in vec2 vertex_uv;

uniform mat4 transform;

out vec2 uv;

void main() {
    uv = vertex_uv;
    vec4 world_position = transform * vec4(vertex_position, 1);
    gl_Position = world_position;
}

    #fragment

    #version 330 core

in vec2 uv;

uniform vec3 element_color;

out vec4 out_color;

void main() {
    out_color = vec4(1, 1,1, 1);
}