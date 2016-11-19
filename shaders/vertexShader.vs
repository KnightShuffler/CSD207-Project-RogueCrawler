#130

uniform float time;

in vec2 vertices;
in vec4 colors;
in vec2 textures;

out vec2 fragmentPosition;
out vec4 fragmentColor;
out vec2 fragmentUV;

void main() {
	vec2 pos = vec2(vertices.x + sin(4*time + 3) / 2f,
					vertices.y + cos(2*time + 1) / 3f);
	gl_Position = vec4(pos, 0f, 1f);
	
	fragmentPosition = vertices;
	fragmentColor = colors;
	fragmentUV = vec2(textures.x, 1f - textures.y);
}