package engine;

import static org.lwjgl.glfw.GLFW.*;

import java.util.HashMap;
import java.util.HashSet;

public class InputManager {
	//Holds the keys that are currently being pressed (including mouse buttons)
	private HashMap<Integer, Boolean> m_keyMap;
	
	//Holds the keys that were pressed in the previous frame (including mouse buttons)
	private HashMap<Integer, Boolean> m_prevKeyMap;
	
	//Holds the location of the mouse
	private long m_mousePosX, m_mousePosY;
	
	//the set that holds the keys that can be accessed by the input manager (includes mouse buttons)
	private static HashSet<Integer> keySet;
	
	//initializes all of the input managers' key set
	public static void init() {
		keySet = new HashSet<>();
		keySet.add(GLFW_KEY_W);
		keySet.add(GLFW_KEY_A);
		keySet.add(GLFW_KEY_S);
		keySet.add(GLFW_KEY_D);
		keySet.add(GLFW_KEY_SPACE);
		keySet.add(GLFW_KEY_ESCAPE);
		keySet.add(GLFW_KEY_ENTER);
		
		keySet.add(GLFW_MOUSE_BUTTON_LEFT);
		keySet.add(GLFW_MOUSE_BUTTON_RIGHT);
	}
	
	public InputManager() {
		m_keyMap = new HashMap<>();
		m_prevKeyMap = new HashMap<>();
		//No keys are pressed when initialized
		for (Integer key : keySet) {
			m_keyMap.put(key, false);
			m_prevKeyMap.put(key, false);
		}
	}
	
	public void takeInput(long window) {
		for (Integer key : keySet) {
			//If the key is for a mouse button press
			if (key == GLFW_MOUSE_BUTTON_1 || key == GLFW_MOUSE_BUTTON_2) {
				if (glfwGetMouseButton(window, key) == 1) {
					m_keyMap.put(key, true);
				}
				else {
					m_keyMap.put(key, false);
				}
			}
			//else the key is for a keyboard input
			else {
				if (glfwGetKey(window, key) == 1) {
					m_keyMap.put(key, true);
				}
				else {
					m_keyMap.put(key, false);
				}
			}
		}
		
		//fix this
		nglfwGetCursorPos(window, m_mousePosX, m_mousePosY);
	}
	
	public boolean isKeyDown(int keyID) {
		if (m_keyMap.containsKey(keyID)) {
			return m_keyMap.get(keyID);
		}
		return false;
	}
	
	public boolean isKeyUp(int keyID) {
		if (m_keyMap.containsKey(keyID)) {
			return !m_keyMap.get(keyID);
		}
		return false;
	}
	
	public boolean isKeyPressed(int keyID) {
		if (m_keyMap.containsKey(keyID)) {
			return !m_prevKeyMap.get(keyID) && m_keyMap.get(keyID);
		}
		return false;
	}
	
	public boolean isKeyReleased(int keyID) {
		if (m_keyMap.containsKey(keyID)) {
			return m_prevKeyMap.get(keyID) && !m_keyMap.get(keyID);
		}
		return false;
	}
	
	public void update() {
		for (Integer key : keySet) {
			m_prevKeyMap.put(key, m_keyMap.get(key));
		}
	}
}
