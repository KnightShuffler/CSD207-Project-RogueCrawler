package engine;

public class Timer {
	// the framerate that the game should be running at in frames per second,
	// the game can not run faster than this
	final public float targetFrameRate;
	// the time taken for one optimal frame to execute
	final public float frameCap;

	// Frame details
	private float frameStartTime;
	private float frameEndTime;
	private float frameDuration;

	// TimeStep details
	private float prevTicks;
	private float newTicks;
	private float frameTime;

	// The ratio of the frame duration to the ideal frame duration (frameCap)
	private float totalDeltaTime;

	final private static float NANOSECONDS_PER_SECOND = 1000000000L;

	// Returns the system time in milliseconds
	public static float getTime() {
		 return (float) System.nanoTime() / NANOSECONDS_PER_SECOND;
//		return (float) System.nanoTime() / (float) 1000000000L;
	}

	public Timer(float target) {
		targetFrameRate = target;
//		 ideal frame time in ms
		frameCap = 1 / targetFrameRate;
	}

	// Initializes the prevTicks variable to a start value (only called before
	// the processing of the first frame)
	public void init() {
		prevTicks = getTime();
	}

	public float getTotalDeltaTime() {
		return totalDeltaTime;
	}

	// Stores the start time of the frame
	public void startFrame() {
		frameStartTime = getTime();
	}

	// Gets the current processing time of the frame, and uses it to calculate
	// the total delta time to be used for variable time step
	public void updateTimeStep() {
		newTicks = getTime();
		frameTime = newTicks - prevTicks;
		prevTicks = newTicks;

		totalDeltaTime = frameTime / frameCap;

		// Debugging code to check if the time step works
//		 totalDeltaTime = frameTime / (1f / 60f);
	}

	// Stores the end time of the frame and if the frame duration was less than
	// the frameCap, then the thread is delayed until that time is passed
	public void endFrame() throws InterruptedException {
		frameEndTime = getTime();
		frameDuration = frameEndTime - frameStartTime;

		if (frameDuration < frameCap) {
			Thread.sleep((long) (frameCap * 1000) - (long) (frameDuration * 1000f));
		}
	}
}