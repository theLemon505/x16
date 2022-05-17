#vertex

#version 120

in vec3 vertex_position;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;

void main() {
    vec4 world_position = transform * vec4(vertex_position, 1);
    gl_Position = projection * view * world_position;
}

#fragment

#version 120

out vec4 out_color;

void main() {
    out_color = vec4(1,1,1,1);
}