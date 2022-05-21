#vertex

#version 330 core

layout(location=0) in vec3 position;

uniform mat4 transform;
uniform mat4 projection;

void main() {
    gl_Position = projection * transform * vec4(position, 1);
}

#fragment

#version 330 core

out vec4 color;

void main() {
    color = vec4(1,1,1,1);
}