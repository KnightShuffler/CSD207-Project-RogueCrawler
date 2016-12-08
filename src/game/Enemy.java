package game;

import engine.ColorRGBA8;

public abstract class Enemy extends Entity {

	public Enemy(int h, float x, float y, int width, float speed, float shotSpeed, float fireRate,
			int damage, int texture, ColorRGBA8 color) {

		super(h, x, y, width, speed, shotSpeed, fireRate, damage, texture, color);
	}

	public abstract void update(Player p);
}
