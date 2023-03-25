#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 textureCoords;

in vec3 fragColor;
out vec3 vertexColor;

out vec2 texture;

uniform mat4 transformWorld;
uniform mat4 transformObject;
uniform mat4 cameraProjection;

void main() {
	gl_Position = cameraProjection * transformWorld * transformObject * vec4(position, 1.0f);
	vertexColor = fragColor;
}