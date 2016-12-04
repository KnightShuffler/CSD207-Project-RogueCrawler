package game;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import engine.Camera;
import engine.ColorRGBA8;
import engine.GameScreen;
import engine.Glyph;
import engine.ShaderProgram;
import engine.SoundBuffer;
import engine.SoundListener;
import engine.SoundManager;
import engine.SoundSource;
import engine.Sprite;
import engine.SpriteBatch;
import engine.Texture;
import engine.Timer;
import engine.UV;
import engine.Window;

public class TestScreen extends GameScreen {

	public TestScreen(Window w, Timer timer, SoundManager soundMgr) {
		this.window = w;
		spriteBatch = new SpriteBatch();
		basicShader = new ShaderProgram("basicShader");
		basicShader.bindAttributes(0, "vertexPosition");
		basicShader.bindAttributes(1, "vertexColor");
		basicShader.bindAttributes(2, "vertexUV");
		basicShader.linkShaders();
		camera = new Camera(window.getWidth(), window.getHeight());
		this.timer = timer;

		this.soundMgr = soundMgr;

		screenIndex = 0;
	}

	private Window window;
	private SpriteBatch spriteBatch;
	private ShaderProgram basicShader;
	private Camera camera;
	private Timer timer;

	private Texture testTexture, testTexture2;

	private SoundManager soundMgr;

	Matrix4f target = new Matrix4f();
	Vector4f destRect1 = new Vector4f(0f, 0f, 400f, 500f), destRect2 = new Vector4f(20f, 0f, 100f, 100f),
			destRect3 = new Vector4f(1000f, 1000f, 10f, 10f), uvRect1 = new Vector4f(0.25f, 0.25f, 0.5f, 0.5f);
	Glyph g1, g2, g3;

	Player p;

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
		p = new Player(0f, 0f);

		testTexture = new Texture("./textures/1.png");
		testTexture2 = new Texture("./textures/2.png");
		g1 = new Glyph(destRect1, uvRect1, ColorRGBA8.BLUE, testTexture.getTexture(), 1f);
		g2 = new Glyph(destRect2, UV.DEFAULT_UV_RECT, ColorRGBA8.CYAN, testTexture2.getTexture(), 2f);
		g3 = new Glyph(destRect3, UV.DEFAULT_UV_RECT, ColorRGBA8.CYAN, testTexture2.getTexture(), 1f);

		try {
			soundMgr.setListener(new SoundListener());
			SoundBuffer buff = new SoundBuffer("./audio/Title.ogg");
			soundMgr.addSoundBuffer(buff);

			SoundBuffer buff1 = new SoundBuffer("./audio/Confuse.ogg");
			soundMgr.addSoundBuffer(buff1);

			SoundSource testSource = new SoundSource(true, false);
			testSource.setBuffer(buff.getBufferID());
//			testSource.setGain(0.001f);
			testSource.setPosition(new Vector3f(1000f, 1000f, 1f));

			SoundSource testSource1 = new SoundSource(false, true);
			testSource1.setBuffer(buff1.getBufferID());

			soundMgr.addSoundSource("test", testSource);
			soundMgr.addSoundSource("test1", testSource1);
			// soundMgr.getSoundSource("test").setPosition(new Vector3f(0f, 0f,
			// 0f));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		soundMgr.playSoundSource("test");
		
		camera.setScale(1);
//		camera.setPosition(new Vector3f(0, 0, 0));
	}

	@Override
	public void onExit() {
		soundMgr.removeSoundSource("test");
		soundMgr.removeSoundSource("test1");
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (window.getInputManager().isReleased(GLFW_KEY_ESCAPE)) {
			state = GameScreen.ScreenState.EXIT_APPLICATION;
		}

		if (window.getInputManager().isPressed(GLFW_KEY_Q)) {
			if (soundMgr.getSoundSource("test").isPlaying()) {
				soundMgr.pauseSoundSource("test");
			} else {
				soundMgr.playSoundSource("test");
			}
		}

		if (window.getInputManager().isPressed(GLFW_KEY_E)) {
			soundMgr.playSoundSource("test1");
		}
		
		p.processInput(window.getInputManager());
		
		float totalDeltaTime = timer.getTotalDeltaTime();
		int frameSimulation = 0;
		while (totalDeltaTime > 0.0f && frameSimulation < Timer.MAX_FRAME_SIMULATIONS) {
			float deltaTime = Math.min(totalDeltaTime, Timer.MAX_DELTA_TIME);
//			p.move(deltaTime, timer.targetFrameRate);
			p.move(deltaTime, timer.targetFrameRate);
//			camera.setPosition(new Vector3f(-p.getPosition().x, -p.getPosition().y, 0));
			
//			System.out.println("totalDeltaTime: " + totalDeltaTime);
//			System.out.println("deltaTime: " + deltaTime);
//			System.out.println("i: " + i);
			
			totalDeltaTime -= deltaTime;
			frameSimulation++;
		}
	}

	@Override
	public void draw() {
		basicShader.bind();
		basicShader.setUniform("mySampler", 0);
		basicShader.setUniform("projection", camera.getProjection().mul(target));

		spriteBatch.begin(SpriteBatch.SORT_BACK_TO_FRONT);
//		spriteBatch.addGlyph(g1);
//		spriteBatch.addGlyph(g2);
		spriteBatch.addGlyph(g3);
		p.draw(spriteBatch);
		spriteBatch.end();

		spriteBatch.render();

		basicShader.unbind();
	}

}
