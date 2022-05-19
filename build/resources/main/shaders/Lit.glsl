#vertex

#version 330 core

layout (location=0) in vec3 vertex_position;
layout (location=1) in vec2 vertex_uv;
layout (location=2) in vec3 vertex_normal;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;
uniform vec3 sun;

out vec2 uv;
out vec3 normal;
out vec3 to_light;

void main() {
    uv = vertex_uv;
    normal = (transform * vec4(vertex_normal, 0)).xyz;
    vec4 world_position = transform * vec4(vertex_position, 1);
    gl_Position = projection * view * world_position;
    to_light = sun - world_position.xyz;
}

#fragment

#version 330 core

in vec2 uv;
in vec3 normal;
in vec3 to_light;

uniform sampler2D texture_sampler;

out vec4 out_color;

void main() {
    vec3 unit_normal = normalize(normal);

    float n_dot_1 = dot(unit_normal, to_light / 100);
    float brightness = max(n_dot_1, 0.2);

    out_color = brightness * texture(texture_sampler, uv) * vec4(1, 1, 0.9, 1) + vec4(0.02, 0.02, 0.02, 1);
}