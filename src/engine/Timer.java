package engine;

public class Timer {
	// the framerate that the game should be running at in frames per second,
	// the game can not run faster than this
	final public float targetFrameRate;
	// the time taken for one optimal frame to execute
	final public float frameCap;

	// The time at start of the previous frame
	private float time;
	// The time at the start of this frame
	private float time2;
	// The difference between time and time2
	private float timeDifference;
	// The time taken since the last time the framerate was displayed
	private float frameTime;
	// The number of frames that have occured until now
	private int frames;

	// The amount of unprocessed time between frames
	private float unprocessed;

	// Whether the frame should be rendered or not
	private boolean shouldRender;

	//Returns the system time in milliseconds
	public static float getTime() {
		return (float) System.nanoTime() / (float) 1000000000L;
	}

	public Timer(float target) {
		targetFrameRate = target;
		frameCap = 1f / targetFrameRate;
		frameTime = 0;
		frames = 0;

		time = Timer.getTime();
		unprocessed = 0;
	}

//	public void beginFrame() {
//		time2 = Timer.getTime();
//		timeDifference = time2 - time;
//		unprocessed += timeDifference;
//		frameTime += timeDifference;
//		time = time2;
//		
//		shouldRender = false;
//	}

//	public boolean shouldProcess() {
//		if (unprocessed >= frameCap) {
//			unprocessed -= frameCap;
//			shouldRender = true;
//			frames++;
//			return true;
//		} else {
//			return false;
//		}
//	}

//	public boolean shouldRender() {
//		return shouldRender;
//	}

//	public void displayFrameRate() {
//		if (frameTime >= 1.0) {
//			frameTime = 0;
//			System.out.println("FPS: " + frames);
//			frames = 0;
//		}
//	}
	
	private float frameStartTime;
	private float frameEndTime;
	private float frameDuration;
	
	private float prevTicks;
	private float newTicks;
	private float fTime;
	
	private float totalDeltaTime;
	
	public void init() {
		prevTicks = getTime();
	}
	
	//calculates delta time
	public void updateTimeStep() {
		newTicks = getTime();
		fTime = newTicks - prevTicks;
		prevTicks = newTicks;
		
		totalDeltaTime = fTime / frameCap;
//		totalDeltaTime = fTime / (1f / 60f);
	}
	
	public float getTotalDeltaTime() {
		return totalDeltaTime;
	}
	
	public void startFrame() {
		frameStartTime = getTime();
	}
	
	public void endFrame() throws InterruptedException {
		frameEndTime = getTime();
		frameDuration = frameEndTime - frameStartTime;
		
		if (frameDuration < frameCap) {
			Thread.sleep((long)(frameCap * 1000) - (long)(frameDuration * 1000f));
		}
	}
}