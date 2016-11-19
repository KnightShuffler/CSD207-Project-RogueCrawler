import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;

import engine.InputManager;
import engine.ShaderProgram;
import engine.Sprite;
import engine.Texture;
import engine.Window;

public class Main {

	public static void main(String[] args) {
		Window.setCallBacks();
		
		try {
			if (!glfwInit()) {
				throw new IllegalStateException("GLFW was not initialized");
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
		InputManager.init();
		InputManager.addKey(GLFW_KEY_ESCAPE);
		
		Window window = new Window();
		try {
			window.createWindow("Test");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
		GL.createCapabilities();
		glEnable(GL_TEXTURE_2D);
		
		float[] vertexData1 = {
			0f, 0f,
			1f, 0f, 0f, 1f,
			0f, 0f,
			
			1f, 0f,
			0f, 1f, 0f, 1f,
			1f, 0f,
			
			1f, 1f,
			0f, 0f, 1f, 1f,
			1f, 1f,
			
			0f, 1f,
			1f, 1f, 1f, 1f,
			0f, 1f,
		};
		
		float[] vertexData2 = {
				-1f, -1f,
				1f, 0f, 0f, 1f,
				0f, 0f,
				
				0f, -1f,
				0f, 1f, 0f, 1f,
				1f, 0f,
				
				0f, 0f,
				0f, 0f, 1f, 1f,
				1f, 1f,
				
				-1f, 0f,
				1f, 1f, 1f, 1f,
				0f, 1f,
			};
		
		int[] indices = {
			0, 1, 2,
			2, 3, 0,
		};
		
		Sprite sprite1 = new Sprite(vertexData1, indices);
		Sprite sprite2 = new Sprite(vertexData2, indices);
		
		Texture texture1 = new Texture("./textures/kumiko legs.jpg");
		Texture texture2 = new Texture("./textures/1.png");
		
		ShaderProgram shader = new ShaderProgram("./shaders/vertexShader.vs", "./shaders/fragmentShader.fs");
		
		float t = 0f;
		
		while (!window.shouldClose()) {
			window.takeInput();
			if (window.getInputManager().isReleased(GLFW_KEY_ESCAPE)) {
				window.close();
			}
			window.updateInput();
			glClearDepth(1.0);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			shader.bind();
			shader.setUniform("mySampler", 0);
			shader.setUniform("time", t);
			texture1.bind(0);
			sprite1.drawShader();
			texture2.bind(0);
			sprite2.drawShader();
			
			window.swapBuffers();
			
			t+=0.01f;
		}
	}

}
