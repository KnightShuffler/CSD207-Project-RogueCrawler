package engine;

import org.joml.Vector2f;

public class Position {
	public float x, y;

	public Position() {
		// Empty
	}

	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Position(Vector2f p) {
		this.x = p.x;
		this.y = p.y;
	}
}
