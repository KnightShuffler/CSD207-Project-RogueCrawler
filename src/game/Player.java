package game;

import static org.lwjgl.glfw.GLFW.*;

import engine.ColorRGBA8;
import engine.InputManager;
import engine.Texture;

public final class Player extends Entity {
	final private static int PLAYER_HEALTH = 3;
	final private static int PLAYER_WIDTH = 32;
	final private static int PLAYER_HEIGHT = 32;
	final private static float PLAYER_SPEED = 200f;
	final private static float PLAYER_SHOTSPEED = 5;
	final private static float PLAYER_FIRERATE = 5;
	final private static int PLAYER_DAMAGE = 3;

	final public static Texture PLAYER_TEXTURE = new Texture("./textures/player.png");

	public Player(float x, float y) {
		super(PLAYER_HEALTH, x, y, PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_SPEED, PLAYER_SHOTSPEED, PLAYER_FIRERATE,
				PLAYER_DAMAGE, PLAYER_TEXTURE.getTexture(), ColorRGBA8.WHITE);
	}

	// The keys mapped for moving up, down, left and right
	final public int KEY_UP = GLFW_KEY_W;
	final public int KEY_DOWN = GLFW_KEY_S;
	final public int KEY_LEFT = GLFW_KEY_A;
	final public int KEY_RIGHT = GLFW_KEY_D;

	// The keys mapped for shooting up, down, left and right
	final public int KEY_SHOOT_UP = GLFW_KEY_UP;
	final public int KEY_SHOOT_DOWN = GLFW_KEY_DOWN;
	final public int KEY_SHOOT_LEFT = GLFW_KEY_LEFT;
	final public int KEY_SHOOT_RIGHT = GLFW_KEY_RIGHT;

	// The key mapped for using an active item
	final public int KEY_USE_ACTIVE = GLFW_KEY_SPACE;

	// Hold the orientation of the player 0 for neutral, -1 for left and down,
	// +1 for right and up
	private int horizontal = 0, vertical = 0;

	public void processInput(InputManager inputManager) {
		/*
		 * if (inputManager.isPressed(KEY_UP)) { vertical = 1; } if
		 * (inputManager.isPressed(KEY_DOWN)) { vertical = -1; } if
		 * (inputManager.isUp(KEY_UP) && inputManager.isUp(KEY_DOWN)) { vertical
		 * = 0; }
		 * 
		 * if (inputManager.isPressed(KEY_RIGHT)) { horizontal = 1; } if
		 * (inputManager.isPressed(KEY_LEFT)) { horizontal = -1; } if
		 * (inputManager.isUp(KEY_RIGHT) && inputManager.isUp(KEY_LEFT)) {
		 * horizontal = 0; }
		 */

		if (inputManager.isPressed(KEY_UP)) {
			vertical = 1;
		}
		if (inputManager.isReleased(KEY_UP)) {
			if (inputManager.isDown(KEY_DOWN)) {
				vertical = -1;
			} else {
				vertical = 0;
			}
		}

		if (inputManager.isPressed(KEY_DOWN)) {
			vertical = -1;
		}

		if (inputManager.isReleased(KEY_DOWN)) {
			if (inputManager.isDown(KEY_UP)) {
				vertical = 1;
			} else {
				vertical = 0;
			}
		}
		
		if (inputManager.isPressed(KEY_RIGHT)) {
			horizontal = 1;
		}
		if (inputManager.isReleased(KEY_RIGHT)) {
			if (inputManager.isDown(KEY_LEFT)) {
				horizontal = -1;
			} else {
				horizontal = 0;
			}
		}

		if (inputManager.isPressed(KEY_LEFT)) {
			horizontal = -1;
		}

		if (inputManager.isReleased(KEY_LEFT)) {
			if (inputManager.isDown(KEY_RIGHT)) {
				horizontal = 1;
			} else {
				horizontal = 0;
			}
		}

		// Debug
		// System.out.println("Horizontal: " + horizontal + ", Vertical: " +
		// vertical);

		direction = getDirection();
		if (inputManager.isDown(KEY_LEFT) || inputManager.isDown(KEY_RIGHT))
			velocity.x = speed * (float) Math.cos(direction);
		else
			velocity.x = 0;

		if (inputManager.isDown(KEY_DOWN) || inputManager.isDown(KEY_UP))
			velocity.y = speed * (float) Math.sin(direction);
		else
			velocity.y = 0;
	}

	private float getDirection() {
		switch (horizontal) {
		case 1:
			switch (vertical) {
			case 0:
				return 0;
			case 1:
				return (float) Math.toRadians(45);
			case -1:
				return (float) Math.toRadians(315);
			}
			break;
		case -1:
			switch (vertical) {
			case 0:
				return (float) Math.toRadians(180);
			case 1:
				return (float) Math.toRadians(135);
			case -1:
				return (float) Math.toRadians(225);
			}
			break;
		case 0:
			switch (vertical) {
			case 0:
				return direction;
			case 1:
				return (float) Math.toRadians(90);
			case -1:
				return (float) Math.toRadians(270);
			}
		}
		return direction;
	}
}
