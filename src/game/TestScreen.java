package game;

import static org.lwjgl.glfw.GLFW.*;

import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector2f;
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

//		camera = new Camera(window.getWidth(), window.getHeight());
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
	
	private Room testRoom;
	
	private Vector2f cameraPos;

	Matrix4f target = new Matrix4f();
	Vector4f destRect1 = new Vector4f(0f, 0f, 256f, 128f), destRect2 = new Vector4f(20f, 0f, 100f, 100f),
			destRect3 = new Vector4f(1000f, 1000f, 10f, 10f), uvRect1 = new Vector4f(0.5f, 0.5f, .5f, .5f);
	Glyph g1, g2, g3, g4;

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
		p = new Player(Room.ROOM_WIDTH * Room.TILE_WIDTH * Room.TILE_SCALE / 2f - Player.CAMERA_OFFSET.x,
				Room.ROOM_HEIGHT * Room.TILE_WIDTH * Room.TILE_SCALE / 2f - Player.CAMERA_OFFSET.y);
//		camera.setPosition(new Vector3f(p.getPosition(), 0f));
//		camera.setPosition(new Vector3f(-320f, -240f, 1f));
		cameraPos = new Vector2f();
		
		try {
			testRoom = new Room(false, Room.DoorState.OPENABLE, Room.DoorState.OPENABLE, Room.DoorState.OPENABLE, Room.DoorState.OPENABLE);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		testTexture = new Texture("./textures/tiles.png");
		testTexture2 = new Texture("./textures/2.png");
		g1 = new Glyph(destRect1, UV.DEFAULT_UV_RECT, ColorRGBA8.WHITE, testTexture.getTexture(), 1f);
		g2 = new Glyph(destRect2, uvRect1, ColorRGBA8.CYAN, testTexture2.getTexture(), 2f);
		g3 = new Glyph(destRect3, UV.DEFAULT_UV_RECT, ColorRGBA8.CYAN, testTexture2.getTexture(), 1f);
		
		Vector4f destRect4 = new Vector4f(0f, 0f, 32f, 32f);
		Vector4f uvRect4 = new Vector4f(0f, 0f, 1f/8f, 1f/4f);
		
		g4 = new Glyph(destRect4, uvRect4, ColorRGBA8.WHITE, testTexture.getTexture(), 1f);

		try {
			soundMgr.setListener(new SoundListener());
			SoundBuffer buff = new SoundBuffer("./audio/Title.ogg");
			soundMgr.addSoundBuffer(buff);

			SoundBuffer buff1 = new SoundBuffer("./audio/Confuse.ogg");
			soundMgr.addSoundBuffer(buff1);

			SoundSource testSource = new SoundSource(true, false);
			testSource.setBuffer(buff.getBufferID());
			// testSource.setGain(0.001f);
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
		// soundMgr.playSoundSource("test");

		camera.setScale(1);
		// camera.setPosition(new Vector3f(0, 0, 0));
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
			// p.move(deltaTime, timer.targetFrameRate);
			p.move(deltaTime, timer.targetFrameRate);
//			if (p.collideWithRoom(testRoom)) {
//				System.out.println("collision");
//			}

			// System.out.println("totalDeltaTime: " + totalDeltaTime);
			// System.out.println("deltaTime: " + deltaTime);
			// System.out.println("i: " + i);

			totalDeltaTime -= deltaTime;
			frameSimulation++;
		}
		
		cameraPos = p.getPosition().add(Player.CAMERA_OFFSET, cameraPos);
		camera.setPosition(cameraPos);
	}

	@Override
	public void draw() {
		basicShader.bind();
		basicShader.setUniform("mySampler", 0);
		basicShader.setUniform("projection", camera.getProjection().mul(target));
		
		testRoom.render();

		spriteBatch.begin(SpriteBatch.SORT_BACK_TO_FRONT);
//		spriteBatch.addGlyph(g1);
//		spriteBatch.addGlyph(g2);
//		spriteBatch.addGlyph(g3);
//		spriteBatch.addGlyph(g4);
		p.draw(spriteBatch);
		spriteBatch.end();

		spriteBatch.render();

		basicShader.unbind();
	}

}
