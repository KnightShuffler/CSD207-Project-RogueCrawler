package engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.openal.ALC10.*;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.opengl.GL;

/*
 * This is the handle of the final game
 * All the functionality will be defined in this class
 * This includes the logic for switching between game screens
 */
public abstract class MainGame {
	// The screen list required for the game
	protected ScreenList screenList;
	// The currently active screen
	protected GameScreen currentScreen = null;
	// A flag to check if the game is running or not
	protected boolean isRunning = false;

	// The target FPS for the game
	protected float targetFPS;
	// The actual FPS
	protected double FPS;

	// The window for the game to run in
	protected Window window;
	protected String windowTitle;

	protected Timer timer;

	protected SoundManager soundManager;

	public MainGame(float targetFPS) {
		this.targetFPS = targetFPS;
	}

	// Initialize the game and return if everything initialized properly
	protected boolean init() {
		// Create the screen list (empty)
		screenList = new ScreenList(this);

		// Initialize the input manager class
		InputManager.init();
		// add the keys for the input manager
		addKeys();

		// Initialize the window for the game
		window = new Window();

		// Set the glfw callbacks
		Window.setCallBacks();

		timer = new Timer(targetFPS);

		// Initialize glfw
		try {
			if (!glfwInit()) {
				throw new IllegalStateException("GLFW was not initialized");
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return false;
		}

		// Create the window with the title passed in
		window.createWindow(windowTitle);

		// Create an OpenGL context for the window
		GL.createCapabilities();

		// Enable blending (for alpha)
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Enable texturing
		glEnable(GL_TEXTURE_2D);

		soundManager = new SoundManager();
		try {
			soundManager.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Custom initialization code
		onInit();

		// Add the screens to the screen list
		addScreens();

		// Get the current screen in the handle
		currentScreen = screenList.getCurrentScreen();
		// Run the entry for the current screen
		currentScreen.onEntry();
		// Set the current screen to be running
		currentScreen.setRunning();

		return true;
	}

	// Run the game
	public void run() throws InterruptedException {
		// Try to initialize the game, if it fails print the error log and
		// return
		try {
			if (!init()) {
				throw new IllegalStateException();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return;
		}

		// Set the running flag to true
		isRunning = true;

		// While the game is running
		timer.init();
		while (isRunning) {
//			timer.beginFrame();
			timer.startFrame();

			/*while (timer.shouldProcess()) {*/
				// Take input
				window.takeInput();
				
				timer.updateTimeStep();
				
				// Update the current screen
				update();
				// Update the input in the input manager
				window.updateInput();

				// timer.displayFrameRate();
			/*}*/

			/*if (timer.shouldRender()) {*/
				// Draw to the screen
				draw();
				// Swap the buffers
				window.swapBuffers();
			/*}*/

			if (window.shouldClose()) {
				break;
			}
			timer.endFrame();
		}

		exitGame();
	}

	// Exit the game (cleanup)
	public void exitGame() {
		// Exit function for the current screen
		currentScreen.onExit();
		// Delete the sound manager
		soundManager.destroy();
		// clean up the screen list
		screenList.destroy();
		// custom exit code
		onExit();
		// set the running flag to false
		isRunning = false;
	}

	// Update the game
	protected void update() {
		if (currentScreen != null) {
			switch (currentScreen.getScreenState()) {
			case RUNNING:
				currentScreen.update();
				break;

			case CHANGE_NEXT:
				currentScreen.onExit();
				currentScreen = screenList.moveNext();
				if (currentScreen != null) {
					currentScreen.onEntry();
				}
				break;

			case CHANGE_PREVIOUS:
				currentScreen.onExit();
				currentScreen = screenList.movePrevious();
				if (currentScreen != null) {
					currentScreen.onEntry();
				}
				break;

			case EXIT_APPLICATION:
				isRunning = false;
				break;

			default:
				break;
			}
		} else {
			isRunning = false;
		}
	}

	protected void draw() {
		if (currentScreen != null && currentScreen.getScreenState() == GameScreen.ScreenState.RUNNING) {
			currentScreen.draw();
		}
	}

	// Which keys to initialize
	public abstract void addKeys();

	// What to do on initializing
	public abstract void onInit();

	// Add the screens to the game
	public abstract void addScreens();

	// What to do for cleanup
	public abstract void onExit();
}
