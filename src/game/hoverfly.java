package game;

import java.util.ArrayList;

import org.joml.Vector2f;

import engine.ColorRGBA8;

public class hoverfly extends fly{
	
	//followpoint follows the player while the fly circles the followpoint 
	protected Vector2f followpoint;												//speed in pixels per frame
	protected double angle=0,xdist,ydist,angularspeed=0.042;					//angularspeed in radians per frame
	protected int radius = 50;
	
	public hoverfly(int h, float x, float y, int width, int height, float speed, float shotSpeed, float fireRate,
			int damage, int texture, ColorRGBA8 color,boolean canshoot) {
		
		super(h, x, y, width, height, speed, shotSpeed, fireRate, damage, texture, color,false);
		this.followpoint.x=x;
		this.followpoint.y=y;
		
	}
	@Override
	public void update (Player p,ArrayList<String> roomData){
		//Check the realtive position of followpoint w.r.t player and make the followpoint follow the player
		if(p.position.x>followpoint.x)
			followpoint.x += speed;
		else if(p.position.x<followpoint.x)
			followpoint.x -= speed;
        
        if(p.position.y>followpoint.y)
			followpoint.y += speed;
		else if(p.position.y<followpoint.y)
			followpoint.y -= speed;
        
        //the angle increses with every frame i.e., technically increases with time
        if(angle >= 6.28)
            angle=0;
        else
            angle+=angularspeed;
        
        //The fly circles the followpoint all the time
        xdist=radius*Math.cos(angle);
        ydist=radius*Math.sin(angle);
        this.position.x=(int)xdist+followpoint.x;
        this.position.y=(int)ydist+followpoint.y;
		
			
	}

}