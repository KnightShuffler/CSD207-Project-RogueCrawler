package game;

import java.util.ArrayList;
import java.awt.Point;
import engine.ColorRGBA8;


public class Zombie extends Enemy{
	protected static int tilewidth=16, playerdimension=5;
	protected int dxl=0,dxr=0,dyu=0,dyd=0;
	//Stores the location of all the blocks on screen
	protected static ArrayList<Point> blocks = new <Point> ArrayList();				
	
	public Zombie(int h, float x, float y, int width, int height, float speed, float shotSpeed, float fireRate,
			int damage, int texture, ColorRGBA8 color) {
		
		super(h, x, y, width, height, speed, shotSpeed, fireRate, damage, texture, color,false);
	}

	
	public void simulate(ArrayList<String> roomData){
		for(int i=0;i<40;i++){
            for(int j=0;j<30;j++){
                if(roomData.get(i).charAt(j)=='b'){
                    Point temp = new Point();
                    temp.setLocation((j*(tilewidth)), (i*(tilewidth)));
                    blocks.add(temp);
                }
            }
        }
	}
	
	@Override
	public void update(Player p, ArrayList<String> roomData) {
		// TODO Auto-generated method stub
		dxl=0;							//variables to check if the zombie can move in left direction
	    dxr=0;							//												right direction
	    dyu=0;							//												up direction
	    dyd=0;							//												down direction
	            for(int i = 0; i<blocks.size();i++){
	            
	              if(position.x+speed+playerdimension>=blocks.get(i).x && position.x<=(blocks.get(i).x-playerdimension) && position.y>(blocks.get(i).y-playerdimension) && position.y<(blocks.get(i).y+tilewidth))
	              {dxr=1;}
	              if(position.x-speed<=(blocks.get(i).x+tilewidth)&& position.x>=(blocks.get(i).x+tilewidth) && position.y>(blocks.get(i).y-playerdimension) && position.y<=(blocks.get(i).y+tilewidth))
	              {dxl=1;}
	              if(position.y+speed+playerdimension>=blocks.get(i).y && position.y<=(blocks.get(i).y-playerdimension) && position.x>=(blocks.get(i).x - playerdimension) && position.x<=(blocks.get(i).x+tilewidth))
	              {dyd=1;}
	              if(position.y-speed<=(blocks.get(i).y+tilewidth) && position.y>=(blocks.get(i).y+tilewidth) && position.x>(blocks.get(i).x-playerdimension) && position.x<=(blocks.get(i).x+tilewidth))
	              {dyu=1;}
	        }
	            
	        if(p.position.x>position.x){
	            if(dxr==0)
				position.x += speed;}
			else if(p.position.x<position.x){
				if(dxl==0)
	                    position.x -= speed;}
	        
	        if(p.position.y>position.y){
	            if(dyd==0)
				position.y += speed;}
			else if(p.position.y<position.y){
	                 if(dyu==0)   
	                
				position.y -= speed;
	                }
	}

}
