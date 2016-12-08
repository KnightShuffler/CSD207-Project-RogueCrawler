package game;

import org.joml.Vector2f;

public class Bullet {
	private Vector2f position;
	private float speed;
	private Vector2f velocity;
	private float direction;
	private int diameter;
	
	public Bullet(int diameter, Vector2f position, float speed, float direction) {
		this.diameter = diameter;
		this.position = position;
		this.speed = speed;
		this.direction = direction;
		this.velocity = new Vector2f(speed * (float)Math.cos(direction), speed * (float)Math.sin(direction));
	}
	
	public void move(float timeStep, float idealFrameRate) {
		position = position.add(velocity.mul(timeStep / idealFrameRate));
	}
	
	public void update(float timeStep, float idealFrameRate) {
		move(timeStep, idealFrameRate);
	}
}
