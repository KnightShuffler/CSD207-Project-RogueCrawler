package engine;

/*
 * This is a single game screen
 * Every game will be divided into multiple different game screens that have different logic associated with them
 */
public abstract class GameScreen {
	// Index of a non-existant screen
	public static final int NO_SCREEN_INDEX = -1;

	// List of possible states the screen can be in
	public static enum ScreenState {
		NONE, RUNNING, EXIT_APPLICATION, CHANGE_NEXT, CHANGE_PREVIOUS
	};

	// The index of the screen
	protected int screenIndex = NO_SCREEN_INDEX;
	// The state of the screen
	protected ScreenState state = ScreenState.NONE;
	// The parent game that the screen belongs to
	protected MainGame game = null;

	// Constructor
	public GameScreen() {
		screenIndex = NO_SCREEN_INDEX;
		state = ScreenState.NONE;
		game = null;
	}

	// Sets the parent game
	final public void setParentGame(MainGame mg) {
		game = mg;
	}

	// Returns the screen index
	final public int getScreenIndex() {
		return screenIndex;
	}

	final public ScreenState getScreenState() {
		return state;
	}

	final public void setRunning() {
		state = ScreenState.RUNNING;
	}

	// Return the next and previous screen indices, this will be dependent on
	// the game hence they are abstract
	public abstract int getNextScreenIndex();

	public abstract int getPreviousScreenIndex();

	// Called at beginning and end of screen
	public abstract void build();

	public abstract void destroy();

	// Called when a screen enters or exits focus
	public abstract void onEntry();

	public abstract void onExit();

	// Called in the main game loop
	public abstract void update();

	public abstract void draw();
}
