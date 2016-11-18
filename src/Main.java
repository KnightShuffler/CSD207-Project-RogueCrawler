import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL;

import engine.InputManager;
import engine.Shader;
import engine.Sprite;
import engine.Window;

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
		
		float [] vertexData = {
			-1f, -1f,
			-1f, 0f,
			0f, 0f,
			0f, -1f
		};
		
		int [] indices = {
			0, 1, 2,
			2, 3, 0
		};
		
		sprite.init(vertexData, indices);
		
		Shader shader = new Shader("./Shaders/vertexShader.vert", "./Shaders/fragmentShader.frag");
		shader.addAttributes("vertexPosition");
		
		//float time = 0.f;
		
		while (!window.shouldClose()) {
			window.takeInput();
			if (window.getInputManager().isKeyReleased(GLFW_KEY_ESCAPE)) {
				window.closeWindow();
			}
			glfwPollEvents();
			window.getInputManager().update();
			
			glClearDepth(1.0);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			shader.use();
			
			//shader.setUniform("time", time);
			
			sprite.draw();
			
			shader.unuse();
			
			window.swapBuffers();
			//time += 0.1f;
		}
	}

}
