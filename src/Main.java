import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import engine.ColorRGBA8;
import engine.InputManager;
import engine.ShaderProgram;
import engine.Sprite;
import engine.SpriteBatch;
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
		
		Sprite sprite1 = new Sprite(vertexData1, indices, 0);
		Sprite sprite2 = new Sprite(vertexData2, indices, 0);
		
		Texture texture1 = new Texture("./textures/kumiko legs.jpg");
		Texture texture2 = new Texture("./textures/1.png");
		
		ShaderProgram shader = new ShaderProgram("./shaders/vertexShader.vs", "./shaders/fragmentShader.fs");
		
		SpriteBatch spriteBatch = new SpriteBatch();
		spriteBatch.init();
		
		Vector4f destRect1 = new Vector4f(-1f, 0f, 1f, 1f);
		Vector4f destRect2 = new Vector4f(0f, -1f, 1f, 1f);
		Vector4f uvRect = new Vector4f(0f, 0f, 1f, 1f);
		ColorRGBA8 color = new ColorRGBA8((byte)255, (byte)255, (byte)255, (byte)255);
		
		float t = 0f;
		
		while (!window.shouldClose()) {
			//Frame Processing
			window.takeInput();
			if (window.getInputManager().isReleased(GLFW_KEY_ESCAPE)) {
				window.close();
			}
			window.updateInput();
			
			//Rendering
			glClearDepth(1.0);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			shader.bind();
			shader.setUniform("mySampler", 0);
			shader.setUniform("time", t);
			texture1.bind(0);
			sprite1.drawVertexArray();
			texture2.bind(0);
			sprite2.drawVertexArray();
//			shader.unbind()
			
//			shader.bind();
//			shader.setUniform("mySampler", 0);
//			shader.setUniform("time", t);
			
			spriteBatch.begin();
			spriteBatch.addGlyph(destRect1, color, uvRect, texture1.getTexture());
			spriteBatch.addGlyph(destRect2, color, uvRect, texture2.getTexture());
			spriteBatch.end();
			
			spriteBatch.renderBatches();
			
			shader.unbind();
			
			window.swapBuffers();
			
			t+=0.001f;
		}
	}

}
