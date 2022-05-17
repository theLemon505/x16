#vertex

#version 420

in vec3 vertex_position;
in vec2 vertex_uv;

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

#version 420

in vec2 uv;

uniform sampler2D texture_sampler;

out vec4 out_color;

void main() {
    out_color = texture(texture_sampler, uv);
}