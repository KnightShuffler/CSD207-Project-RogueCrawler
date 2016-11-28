package engine;

public class Timer {
	
	 public double frame_cap;
	 public double frame_time;
	 public int frames;
	 double time;
	 public double unprocessed;
	 public boolean shouldRender;
	 
	public static double getTime() {
		return (double)System.nanoTime() / (double)1000000000L;
	}
    public Timer(){
        frame_cap = 1.0/60.0;
		frame_time = 0;
		this.frames = 0;
		
		time = Timer.getTime();
		unprocessed = 0;
    }
    public void beginFrame(){
    	shouldRender=false;
    	double time_2 = Timer.getTime();
		double passed = time_2 - time;
		unprocessed+=passed;
		frame_time +=passed;
		time = time_2;
    }
    public static void endFrame(){
    	
    }
    public boolean shouldRender(){
    	if(unprocessed >= frame_cap)
    	{
    	unprocessed-=frame_cap;
    	shouldRender=true;
		return true;
    	}
    	else 
    		return false;
    }
}
