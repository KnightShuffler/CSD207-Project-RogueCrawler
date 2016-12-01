package engine;

public class Timer {
	// the framerate that the game should be running at in frames per second,
	// the game can not run faster than this
	private double targetFrameRate;
	// the time taken for one optimal frame to execute
	private double frameCap;

	// The time at start of the previous frame
	private double time;
	// The time at the start of this frame
	private double time2;
	// The difference between time and time2
	private double deltaTime;

	// The amount of unprocessed time between frames
	private double unprocessed;

	private boolean shouldRender;

	// Debugging members

	// The time taken since the last time the framerate was displayed
	private double frameTime;
	// The number of frames that have occured until now
	private int frames;

	public static double getTime() {
		return (double) System.nanoTime() / (double) 1000000000L;
	}

	public Timer(double target) {
		targetFrameRate = target;
		frameCap = 1.0 / targetFrameRate;
		frameTime = 0;
		frames = 0;

		time = Timer.getTime();
		unprocessed = 0;
	}

	public void beginFrame() {
		time2 = Timer.getTime();
		deltaTime = time2 - time;
		unprocessed += deltaTime;
		frameTime += deltaTime;
		time = time2;
		shouldRender = false;
	}

	public boolean shouldProcess() {
		if (unprocessed >= frameCap) {
			unprocessed -= frameCap;
			shouldRender = true;
			frames++;
			return true;
		} else {
			return false;
		}
	}

	public boolean shouldRender() {
		return shouldRender;
	}

	public void setTargetFrameRate(double fps) {
		targetFrameRate = fps;
	}

	public void displayFrameRate() {
		if (frameTime >= 1.0) {
			frameTime = 0;
			System.out.println("FPS: " + frames);
			frames = 0;
		}
	}
}