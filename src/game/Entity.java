package game;

import java.util.ArrayList;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;

import engine.ColorRGBA8;
import engine.SpriteBatch;
import engine.UV;

public abstract class Entity {
	// Holds the health of the entity
	protected Health health;
	// Holds the x and y position of the entity (as floats) (in room
	// coordinates)
	protected Vector2f position;
	// Holds the velocity of the entity
	protected Vector2f velocity;
	// Holds the angle of motion that the entity follows
	protected float direction;
	// Holds the width and height of the entity (as integers)
	protected Vector2i dimensions;
	// Speed of the entity in pixels per second
	protected float speed;
	// Speed of the shots that the entity fires (if any) in pixels per second
	protected float shotSpeed;
	// The rate at which the entity shoots its shots (if any) in shots per
	// second
	protected float fireRate;
	// The damage the entity does by shots (and by contact in the case of
	// enemies)
	protected int damage;

	// The handle to the texture of the entity
	private int texture = 0;
	// The color to multiply the texture color by
	private ColorRGBA8 color = ColorRGBA8.WHITE;

	public Entity(int hp, float x, float y, int width, int height, float speed, float shotSpeed, float fireRate,
			int damage, int texture, ColorRGBA8 color) {
		health = new Health(hp);
		position = new Vector2f(x, y);
		velocity = new Vector2f(0f, 0f);
		dimensions = new Vector2i(width, height);

		this.speed = speed;
		this.shotSpeed = shotSpeed;
		this.fireRate = fireRate;
		this.damage = damage;

		this.texture = texture;
		this.color = color;
	}

	// Default draw function for entities
	// their depth is their y position
	public void draw(SpriteBatch spb) {
		spb.addGlyph(new Vector4f(position.x, position.y, (float)dimensions.x, (float)dimensions.y),
				UV.DEFAULT_UV_RECT, color, texture, position.y);
	}
	
	public void move(float deltaTime, float idealFrameRate) {
		position = position.add(velocity.mul(deltaTime / idealFrameRate));
	}

	// Place holder for collision functions
	public void collision(ArrayList<String> roomData) {

	}

	// getters
	final public int getMaxHealth() {
		return health.getMaxHealth();
	}

	final public int getCurrentHealth() {
		return health.getCurrentHealth();
	}

	final public Vector2f getPosition() {
		return position;
	}

	final public Vector2i getDimensions() {
		return dimensions;
	}

	final public float getSpeed() {
		return speed;
	}

	final public float getShotSpeed() {
		return shotSpeed;
	}

	final public float getFireRate() {
		return fireRate;
	}

	final public int damage() {
		return damage;
	}
}
