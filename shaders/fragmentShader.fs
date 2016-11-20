#130

uniform sampler2D mySampler;
//uniform float time;

in vec2 fragmentPosition;
in vec4 fragmentColor;
in vec2 fragmentUV;

out vec4 color;

void main() {
	/*vec4 col = vec4(clamp(fragmentColor.r * sin(2 * time + fragmentPosition.x), 0f, 1f),
					clamp(fragmentColor.g * cos(5 *time + fragmentPosition.y), 0f, 1f),
					clamp(fragmentColor.b * abs(tan(time)), 0f, 1f),
					1f
					);*/ 
	color = texture2D(mySampler, fragmentUV);
}