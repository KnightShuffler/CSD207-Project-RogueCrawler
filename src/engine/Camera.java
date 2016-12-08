package engine;

import org.joml.*;

public class Camera {

	private Vector3f position;
	private Matrix4f projection;
	private float scale = 1f;

	public Camera(int width, int height) {
		position = new Vector3f(0, 0, 0);
		projection = new Matrix4f().setOrtho2D(-width / 2, width / 2, -height / 2, height / 2);
	}

	public void setPosition(Vector3f position) {
		this.position = position.mul(-1f);
	}
	
	public void setPosition(Vector2f position) {
		this.position = new Vector3f(-position.x, -position.y, 1f);
	}

	public void addPosition(Vector3f position) {
		this.position.add(position);
	}

	public Vector3f getPosition() {
		return position;
	}

	public Matrix4f getProjection() {
		Matrix4f target = new Matrix4f();
		Matrix4f pos = new Matrix4f().setTranslation(position);
		Matrix4f scaling = new Matrix4f().scale(scale);

		target = projection.mul(pos, target);
		target = target.mul(scaling, target);
		return target;
	}

	public void setScale(float s) {
		scale = s;
	}
}