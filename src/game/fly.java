package game;

import engine.ColorRGBA8;
import java.util.*;



public class fly extends Enemy{
	
																				
	
	public fly(int h, float x, float y, int width, int height, float speed, float shotSpeed, float fireRate,
			int damage, int texture, ColorRGBA8 color,boolean canshoot) {
		
		super(h, x, y, width, height, speed, shotSpeed, fireRate, damage, texture, color,false);
				
	}
	
	public void update (Player p,ArrayList<String> roomData){
		//Check the postion of player w.r.t fly and make the fly follow the player
		if(p.position.x>this.position.x)
			this.position.x += speed;
		else if(p.position.x<this.position.x)
			this.position.x -= speed;
        
        if(p.position.y>this.position.y)
        	this.position.y += speed;
		else if(p.position.y<this.position.y)
			this.position.y -= speed;
			
	}

	
}
