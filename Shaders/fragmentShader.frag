#130

out vec4 color;

uniform float time;

void main() {
	color = vec4(clamp(sin(time), 0.0, 1.0), clamp(cos(time), 0.0, 1.0), clamp(tan(time), 0.0, 1.0), 1.0f);
}