package game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Camera;
import engine.ColorRGBA8;
import engine.GameScreen;
import engine.Glyph;
import engine.ShaderProgram;
import engine.SpriteBatch;
import engine.Texture;
import engine.UV;
import engine.Window;

public class TestScreen extends GameScreen {

	public TestScreen(Window w) {
		this.window = w;
		spriteBatch = new SpriteBatch();
		shader = new ShaderProgram("./shaders/vertexShader.vs", "./shaders/fragmentShader.fs");
		camera = new Camera(window.getWidth(), window.getHeight());

		screenIndex = 0;
	}

	private Window window;
	private SpriteBatch spriteBatch;
	private ShaderProgram shader;
	private Camera camera;

	private Texture testTexture, testTexture2;

	Matrix4f target = new Matrix4f();
	Vector4f destRect1 = new Vector4f(0f, 0f, 400f, 500f), destRect2 = new Vector4f(20f, 0f, 1000f, 1000f);
	Glyph g1, g2;

	@Override
	public int getNextScreenIndex() {
		return GameScreen.NO_SCREEN_INDEX;
	}

	@Override
	public int getPreviousScreenIndex() {
		return GameScreen.NO_SCREEN_INDEX;
	}

	@Override
	public void build() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEntry() {
		// TODO Auto-generated method stub
		testTexture = new Texture("./textures/1.png");
		testTexture2 = new Texture("./textures/2.png");
		g1 = new Glyph(destRect1, UV.DEFAULT_UV_RECT, ColorRGBA8.BLUE, testTexture.getTexture(), 1f);
		g2 = new Glyph(destRect2, UV.DEFAULT_UV_RECT, ColorRGBA8.CYAN, testTexture2.getTexture(), 2f);
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (window.getInputManager().isReleased(GLFW_KEY_ESCAPE)) {
			state = GameScreen.ScreenState.EXIT_APPLICATION;
		}
		if (window.getInputManager().isDown(GLFW_KEY_W)) {
			camera.getPosition().sub(new Vector3f(0, 5, 0));
		}

		if (window.getInputManager().isDown(GLFW_KEY_A)) {
			camera.getPosition().sub(new Vector3f(-5, 0, 0));
		}

		if (window.getInputManager().isDown(GLFW_KEY_S)) {
			camera.getPosition().sub(new Vector3f(0, -5, 0));
		}

		if (window.getInputManager().isDown(GLFW_KEY_D)) {
			camera.getPosition().sub(new Vector3f(5, 0, 0));
		}
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		glClearDepth(1.0);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		shader.bind();
		shader.setUniform("mySampler", 0);
		shader.setUniform("projection", camera.getProjection().mul(target));

		spriteBatch.begin(SpriteBatch.SORT_BACK_TO_FRONT);
		spriteBatch.addGlyph(g1);
		spriteBatch.addGlyph(g2);
		spriteBatch.end();

		spriteBatch.render();

		shader.unbind();
	}

}
