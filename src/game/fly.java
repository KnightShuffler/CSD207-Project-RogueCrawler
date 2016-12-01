package game;

import engine.ColorRGBA8;
import java.util.*;
import org.joml.Vector2f;



public class fly extends Enemy{
	protected Vector2f followpoint;												//speed in pixels per frame
	protected double angle=0,xdist,ydist,angularspeed=0.042;					//angularspeed in radians per frame
																				
	
	public fly(int h, float x, float y, int width, int height, float speed, float shotSpeed, float fireRate,
			int damage, int texture, ColorRGBA8 color,boolean canshoot) {
		
		super(h, x, y, width, height, speed, shotSpeed, fireRate, damage, texture, color,false);
		this.followpoint.x=x;
		this.followpoint.y=y;
		
	}
	
	public void update (Player p,ArrayList<String> roomData){
		if(p.position.x>followpoint.x)
			followpoint.x += speed;
		else if(p.position.x<followpoint.x)
			followpoint.x -= speed;
        
        if(p.position.y>followpoint.y)
			followpoint.y += speed;
		else if(p.position.y<followpoint.y)
			followpoint.y -= speed;
        
        if(angle >= 6.28)
            angle=0;
        else
            angle+=angularspeed;
        
        xdist=50*Math.cos(angle);
        ydist=50*Math.sin(angle);
        this.position.x=(int)xdist+followpoint.x;
        this.position.y=(int)ydist+followpoint.y;
		
			
	}

	
}
