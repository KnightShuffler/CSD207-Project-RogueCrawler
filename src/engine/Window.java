package engine;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
	private long m_windowID;
	private int m_width, m_height;
	
	private static final int DEFAULT_SCREEN_WIDTH = 640;
	private static final int DEFAULT_SCREEN_HEIGHT = 480;
	
	private boolean m_isFullScreen;
	
	public static void setCallbacks() {
		//set glfw error callback to use System.err
		//this allows us to check for glfw errors in the console
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}
	
	//Default constructor, makes a windowed window of the default size
	public Window() {
		setResolution(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
		setFullScreen(false);
	}
	
	//Takes in the resolution and if the window should be fullscreen
	public Window(int w, int h, boolean f) {
		setResolution(w, h);
		setFullScreen(f);
	}
	
	//creates the window
	public void createWindow(String windowTitle) {
		//Create the window and store its ID
		m_windowID = glfwCreateWindow(
				m_width,
				m_height,
				windowTitle,
				m_isFullScreen ? glfwGetPrimaryMonitor() : 0,
				0);
		
		//if the window failed to be created, then its ID will be set to 0, throw an illegal state exception
		if (m_windowID == 0) {
			throw new IllegalStateException("Window " + windowTitle + " was not created");
		}
		
		//if the window isn't fullscreen center the window position
		if (!m_isFullScreen) {
			//Save the video mode of the primary monitor (this contains all of the display's details
			GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
			//Set the position to the center
			glfwSetWindowPos(m_windowID, (vid.width() - m_width) / 2, (vid.height() - m_height) / 2);
			//Make the window visible
			glfwShowWindow(m_windowID);
		}
		
		//create the openGL context for the window
		glfwMakeContextCurrent(m_windowID);
	}
	
	//Sets the resolution of the fullscreen window or the size of the windowed window
	public void setResolution(int w, int h) {
		m_width = w;
		m_height = h;
	}
	
	public int getWidth() {
		return m_width;
	}
	
	public int getHeight() {
		return m_height;
	}
	
	public void setFullScreen(boolean f) {
		m_isFullScreen = f;
	}
	
	public boolean isFullScreen() {
		return m_isFullScreen;
	}
	
	//returns whether the window should close
	public boolean shouldClose() {
		return glfwWindowShouldClose(m_windowID);
	}
	
	//sets the shouldClose value to true
	public void closeWindow() {
		glfwSetWindowShouldClose(m_windowID, true);
	}
	
	//swaps the buffers to which the renderer is rendering
	public void swapBuffers() {
		glfwSwapBuffers(m_windowID);
	}
}
