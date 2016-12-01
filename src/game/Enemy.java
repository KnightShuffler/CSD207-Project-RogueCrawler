package game;

import java.util.ArrayList;

import engine.ColorRGBA8;

public abstract class Enemy extends Entity {
	
	
	
	public Enemy(int h, float x, float y, int width, int height, float speed, float shotSpeed, float fireRate,
			int damage, int texture, ColorRGBA8 color,boolean canshoot) {
									
		super(h, x, y, width, height, speed, shotSpeed, fireRate, damage, texture, color,canshoot);
			
	}
	
	public abstract void update(Player p,ArrayList<String> roomData);
}
