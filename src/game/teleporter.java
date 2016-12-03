package game;

import java.util.ArrayList;

import engine.ColorRGBA8;

//Teleporter walks through the blocks in the room
public class teleporter extends Zombie{
	
	//Checks if the teleporter is inside the block
	protected static boolean flag=false;
	
	//dimensions of the entity
	protected static int dimension=10;
	
	public teleporter(int h, float x, float y, int width, int height, float speed, float shotSpeed, float fireRate,
			int damage, int texture, ColorRGBA8 color) {
		
		super(h, x, y, width, height, speed, shotSpeed, fireRate, damage, texture, color);
	}
	
	@Override
	public void update(Player p, ArrayList<String> roomData) {
		// TODO Auto-generated method stub
		//Resets the flag to enable the teleporter to follow the player as usual
		flag=false;
		
	            for(int i = 0; i<blocks.size();i++){
	            	if(position.x>=(blocks.get(i).x-dimension-speed) && position.x<=(blocks.get(i).x+tilewidth+speed) && position.y>=(blocks.get(i).y-dimension-speed) && position.y<=(blocks.get(i).y+tilewidth+speed))
	                    flag=true;
	        }
	            
	        //Checks the relative position of teleporter w.r.t player and then checks if it can move in a certain direction or not
	        //And then teleports it by certain distance if it falls within the range of block
	            if(p.position.x>position.x){
	                if(!flag)
	    			position.x += 1;
	                else if(flag)
	                    position.x += (tilewidth+zombiedimension+speed);
	            }
	            
	            else if(p.position.x<position.x){
	                if(!flag)
	                        position.x -= 1;
	                else if(flag)
	                    position.x -= (tilewidth+zombiedimension+speed);
	            }
	            
	            if(p.position.y>position.y){
	                if(!flag)
	                    position.y += 1;
	                else if(flag)
	                    position.y += (tilewidth+zombiedimension+speed);
	            }
	            
	            else if(p.position.y<position.y){
	                if(!flag)        
	                	position.y -= 1;
	                else if(flag)
	                    position.y -= (tilewidth+zombiedimension+speed);
	            
	            }

	}
	            }
