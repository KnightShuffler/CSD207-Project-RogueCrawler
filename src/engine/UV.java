package engine;

import org.joml.Vector2f;

public class UV {
	public float u, v;
	
	public UV() {
		//Empty
	}
	
	public UV(float u, float v) {
		this.u = u;
		this.v = v;
	}
	
	public UV(Vector2f uv) {
		this.u = uv.x;
		this.v = uv.y;
	}
}
