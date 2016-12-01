package engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Window {
	private long windowHandle;
	private int width, height;
	private boolean isFullScreen;
	public final int DEFAULT_WINDOW_WIDTH = 640;
	public final int DEFAULT_WINDOW_HEIGHT = 480;

	private InputManager inputManager;

	public static void setCallBacks() {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
	}

	public Window() {
		width = DEFAULT_WINDOW_WIDTH;
		height = DEFAULT_WINDOW_HEIGHT;
		isFullScreen = false;
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

		inputManager = new InputManager();
	}

	public Window(int w, int h, boolean f) {
		width = w;
		height = h;
		isFullScreen = f;
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

		inputManager = new InputManager();
	}

	public void createWindow(String title) {
		windowHandle = glfwCreateWindow(width, height, title, isFullScreen ? glfwGetPrimaryMonitor() : 0, 0);
		if (windowHandle == 0) {
			throw new IllegalStateException("Window was not created");
		}

		if (!isFullScreen) {
			GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(windowHandle, (vid.width() - width) / 2, (vid.height() - height) / 2);
			glfwShowWindow(windowHandle);
		}

		glfwMakeContextCurrent(windowHandle);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isFullScreen() {
		return isFullScreen;
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(windowHandle);
	}

	public void close() {
		glfwSetWindowShouldClose(windowHandle, true);
	}

	public InputManager getInputManager() {
		return inputManager;
	}

	public void takeInput() {
		inputManager.takeInput(windowHandle);
	}

	public void updateInput() {
		glfwPollEvents();
		inputManager.update();
	}

	public void swapBuffers() {
		glfwSwapBuffers(windowHandle);
	}
}
