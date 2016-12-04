#130

//uniform float time;

uniform mat4 projection;

in vec2 vertexPosition;
in vec4 vertexColor;
in vec2 vertexUV;

out vec2 fragmentPosition;
out vec4 fragmentColor;
out vec2 fragmentUV;

void main() {
	gl_Position = projection * vec4(vertexPosition, 0f, 1f);
	
	fragmentPosition = vertexPosition;
	fragmentColor = vertexColor;
	fragmentUV = vec2(vertexUV.x, 1f - vertexUV.y);
}