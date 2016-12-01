package engine;

import java.util.ArrayList;

/*
 * This has the list of screens the game will need
 */
public class ScreenList {
	// The parent game handle
	protected MainGame game;
	// The list of game screens
	protected ArrayList<GameScreen> screens;
	// The index of the currently active screen
	protected int currentScreenIndex = GameScreen.NO_SCREEN_INDEX;

	// Contstructor, takes the handle of the parent game
	public ScreenList(MainGame mg) {
		game = mg;
		screens = new ArrayList<>();
	}

	// Returns the currently active screen, null if the screen is non-existent
	public GameScreen getCurrentScreen() {
		try {
			if (currentScreenIndex != GameScreen.NO_SCREEN_INDEX) {
				return screens.get(currentScreenIndex);
			} else {
				throw new IllegalStateException("Non existant screen index");
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Move to the next screen and return it
	public GameScreen moveNext() {
		GameScreen currentScreen = getCurrentScreen();
		if (currentScreen.getNextScreenIndex() != GameScreen.NO_SCREEN_INDEX) {
			currentScreenIndex = currentScreen.getNextScreenIndex();
		}
		return getCurrentScreen();
	}

	// Move to the previous screen and return it
	public GameScreen movePrevious() {
		GameScreen currentScreen = getCurrentScreen();
		if (currentScreen.getPreviousScreenIndex() != GameScreen.NO_SCREEN_INDEX) {
			currentScreenIndex = currentScreen.getPreviousScreenIndex();
		}
		return getCurrentScreen();
	}

	// Set the current screen manually
	public void setScreen(int nextScreen) {
		currentScreenIndex = nextScreen;
	}

	// Add a screen to the list
	public void addScreen(GameScreen newScreen) {
		screens.add(newScreen);
		newScreen.build();
		newScreen.setParentGame(game);
	}

	// Destroy the list when its not needed
	public void destroy() {
		for (GameScreen s : screens) {
			s.destroy();
		}
		screens.clear();
		currentScreenIndex = GameScreen.NO_SCREEN_INDEX;
	}
}
