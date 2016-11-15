import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL;

import engine.*;

public class Main {

	public static void main(String[] args) {
		//Temporarily checking functionality
		
		if (!glfwInit()) {
			throw new IllegalStateException("GLFW did not initialize");
		}
		
		InputManager.init();
		
		Window window = new Window();
		window.createWindow("Test");
		
		//create context
		GL.createCapabilities();
		
		Sprite sprite = new Sprite();
		sprite.init(-1f, -1f, .5f, .5f);
		
		Shader shader = new Shader("./Shaders/vertexShader.vert", "./Shaders/fragmentShader.frag");
		shader.addAttributes("vertexPosition");
		
		while (!window.shouldClose()) {
			window.takeInput();
			if (window.getInputManager().isKeyReleased(GLFW_KEY_ESCAPE)) {
				window.closeWindow();
			}
			glfwPollEvents();
			window.getInputManager().update();
			
			shader.use();
			sprite.draw();
			shader.unuse();
			
			window.swapBuffers();
		}
	}

}
