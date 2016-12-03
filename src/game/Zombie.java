package game;

import java.util.ArrayList;
import java.awt.Point;
import engine.ColorRGBA8;


public class Zombie extends Enemy{
	protected static int tilewidth=16, zombiedimension=5;
	
	//Keeps track whether the zombie can move left, right, up or down at every frame.
	protected int dxl=0,dxr=0,dyu=0,dyd=0;
	
	//Stores the location of all the blocks on screen
	protected static ArrayList<Point> blocks = new <Point> ArrayList();				
	
	//Stores room width and height i.e., the number of tiles 
	protected static int roomwidth=20,roomheight=15;
	
	public Zombie(int h, float x, float y, int width, int height, float speed, float shotSpeed, float fireRate,
			int damage, int texture, ColorRGBA8 color) {
		
		super(h, x, y, width, height, speed, shotSpeed, fireRate, damage, texture, color,false);
	}

	//Goes throught the roomdata and stores all the locations of the blocks in "blocks" arraylist declared in line 15
	//This method has to be called only once after the room layout is decided
	public void simulate(ArrayList<String> roomData){
		for(int i=0;i<roomheight;i++){
            for(int j=0;j<roomwidth;j++){
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
		//Resets the variables to enable the zombie to move in all directions for now
		dxl=0;							//variables to check if the zombie can move in left direction
	    dxr=0;							//												right direction
	    dyu=0;							//												up direction
	    dyd=0;							//												down direction
	            for(int i = 0; i<blocks.size();i++){
	            	
	            	//Defines the region around the block where the zombie cannot go anymore right
		              if(position.x+speed+zombiedimension>=blocks.get(i).x && position.x<=(blocks.get(i).x-zombiedimension) && position.y>(blocks.get(i).y-zombiedimension) && position.y<(blocks.get(i).y+tilewidth))
		              {dxr=1;}
		            //Defines the region around the block where the zombie cannot go anymore left
		              if(position.x-speed<=(blocks.get(i).x+tilewidth)&& position.x>=(blocks.get(i).x+tilewidth) && position.y>(blocks.get(i).y-zombiedimension) && position.y<=(blocks.get(i).y+tilewidth))
		              {dxl=1;}
		            //Defines the region around the block where the zombie cannot go anymore down
		              if(position.y+speed+zombiedimension>=blocks.get(i).y && position.y<=(blocks.get(i).y-zombiedimension) && position.x>=(blocks.get(i).x - zombiedimension) && position.x<=(blocks.get(i).x+tilewidth))
		              {dyd=1;}
		            //Defines the region around the block where the zombie cannot go anymore up
		              if(position.y-speed<=(blocks.get(i).y+tilewidth) && position.y>=(blocks.get(i).y+tilewidth) && position.x>(blocks.get(i).x-zombiedimension) && position.x<=(blocks.get(i).x+tilewidth))
		              {dyu=1;}
	        }
	            
	        //Checks the relative position of zombie w.r.t player and then checks if it can move in a certain direction or not
	        if(p.position.x>position.x){
	            if(dxr==0)
				position.x += speed;
	            }
			else if(p.position.x<position.x){
				if(dxl==0)
	            position.x -= speed;
				}
	        
	        if(p.position.y>position.y){
	            if(dyd==0)
				position.y += speed;
	            }
			else if(p.position.y<position.y){
	            if(dyu==0)   
				position.y -= speed;
	                }
	}

}
