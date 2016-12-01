package game;

import engine.ColorRGBA8;

public final class Player extends Entity {

	public Player(int h, float x, float y, int width, int height, float speed, float shotSpeed, float fireRate,
			int damage, int texture, ColorRGBA8 color) {

		super(h, x, y, width, height, speed, shotSpeed, fireRate, damage, texture, color);

	}

}
