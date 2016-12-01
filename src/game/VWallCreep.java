package game;

import engine.ColorRGBA8;
import java.util.*;

public class VWallCreep extends Enemy{
	
	protected int tileheight=16;
	protected int roomheight = tileheight*28;	
	public VWallCreep(int h, float x, float y, int width, int height, float speed, float shotSpeed, float fireRate,
			int damage, int texture, ColorRGBA8 color) {
		
		super(h, x, y, width, height, speed, shotSpeed, fireRate, damage, texture, color,true);
	}
	
	//public void update(Player p,ArrayList<String> roomData){
		
	//}

	//@Override
	public void update(Player p, ArrayList<String> roomData) {
		// TODO Auto-generated method stub
								
		if(p.position.y>position.y)
			position.y=position.y-speed>0?position.y-speed:position.y;
		else if(p.position.y<position.y)
			position.y=position.y+speed<roomheight?position.y+speed:position.y;
		
		
		if(p.position.x > position.x)
			this.shoot(0);
		else
			this.shoot(3.14f);
	}

}
