#130

uniform float time;

in vec2 vertices;
in vec4 colors;
in vec2 textures;

uniform mat4 projection;

out vec2 fragmentPosition;
out vec4 fragmentColor;
out vec2 fragmentUV;

void main() {
	vec2 pos = vec2(vertices.x,	vertices.y);
	gl_Position = projection*vec4(pos, 0f, 1f);
	
	fragmentPosition = vertices;
	fragmentColor = colors;
	fragmentUV = vec2(textures.x, 1f - textures.y);
}