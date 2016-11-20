import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import engine.Camera;
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
		InputManager.addKey(GLFW_KEY_W);
		InputManager.addKey(GLFW_KEY_A);
		InputManager.addKey(GLFW_KEY_S);
		InputManager.addKey(GLFW_KEY_D);
		
		Window window = new Window();
		try {
			window.createWindow("Test");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		
		Camera camera = new Camera(640,480);
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
		
		Sprite sprite1 = new Sprite(vertexData1, indices, 0);
		Sprite sprite2 = new Sprite(vertexData2, indices, 0);
		
		Texture texture1 = new Texture("./textures/deadmau5.jpg");
		Texture texture2 = new Texture("./textures/1.png");
		
		ShaderProgram shader = new ShaderProgram("./shaders/vertexShader.vs", "./shaders/fragmentShader.fs");
		
		
		
		Matrix4f scale = new Matrix4f().translate(new Vector3f(100,0,0)).scale(300);
		
		Matrix4f target = new Matrix4f();
			
	
		float t = 0f;
		camera.setPosition(new Vector3f(-100,0,0));
		
		while (!window.shouldClose()) {
			target=scale;
			window.takeInput();
			if (window.getInputManager().isReleased(GLFW_KEY_ESCAPE)) {
				window.close();
			}
			
			if(window.getInputManager().isReleased(GLFW_KEY_W)){
				camera.getPosition().sub(new Vector3f(0,5,0));
			}
			
            if(window.getInputManager().isReleased(GLFW_KEY_A)){
            	camera.getPosition().sub(new Vector3f(-5,0,0));
			}
            
            if(window.getInputManager().isReleased(GLFW_KEY_S)){
            	camera.getPosition().sub(new Vector3f(0,-5,0));
			} 
            
            if(window.getInputManager().isReleased(GLFW_KEY_D)){
            	camera.getPosition().sub(new Vector3f(5,0,0));
			}

			window.updateInput();
			glClearDepth(1.0);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			shader.bind();
			shader.setUniform("mySampler", 0);
			shader.setUniform("time", t);
			shader.setUniform("projection", camera.getProjection().mul(target));
			texture1.bind(0);
			sprite1.drawVertexArray();
			texture2.bind(0);
			sprite2.drawVertexArray();
			
			window.swapBuffers();
			
			t+=0.1f;
		}
	}

}
