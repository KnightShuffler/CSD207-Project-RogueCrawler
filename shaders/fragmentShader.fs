#130

uniform sampler2D mySampler;
//uniform float time;

in vec2 fragmentPosition;
in vec4 fragmentColor;
in vec2 fragmentUV;

out vec4 color;

void main() {
	color = texture2D(mySampler, fragmentUV) * fragmentColor;
}