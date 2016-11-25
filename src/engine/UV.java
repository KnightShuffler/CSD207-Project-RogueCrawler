package engine;

import org.joml.Vector2f;
import org.joml.Vector4f;

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
	
	public static Vector4f DEFAULT_UV_RECT = new Vector4f(0f, 0f, 1f, 1f);
}
