package game;

import static org.lwjgl.glfw.GLFW.*;

import engine.GameScreen;
import engine.InputManager;
import engine.MainGame;

public class App extends MainGame {
	
	GameScreen test;

	public App() {
		super(60.0);
		windowTitle = "Rogue Crawler";
	}

	@Override
	public void onInit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void addScreens() {
		// TODO Auto-generated method stub
		test = new TestScreen(window);
		screenList.addScreen(test);
		screenList.setScreen(test.getScreenIndex());
	}

	@Override
	public void onExit() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void addKeys() {
		// TODO Auto-generated method stub
		InputManager.addKey(GLFW_KEY_ESCAPE);
		InputManager.addKey(GLFW_KEY_W);
		InputManager.addKey(GLFW_KEY_A);
		InputManager.addKey(GLFW_KEY_S);
		InputManager.addKey(GLFW_KEY_D);
		InputManager.addKey(GLFW_KEY_Q);
		InputManager.addKey(GLFW_KEY_E);
	}
	
	public static void main(String[] args) {
		App a = new App();
		
		a.run();
	}

}
