#130

//uniform float time;

uniform mat4 projection;

in vec2 vertices;
in vec4 colors;
in vec2 textures;

out vec2 fragmentPosition;
out vec4 fragmentColor;
out vec2 fragmentUV;

void main() {
	gl_Position = projection * vec4(vertices, 0f, 1f);
	
	fragmentPosition = vertices;
	fragmentColor = colors;
	fragmentUV = vec2(textures.x, 1f - textures.y);
}