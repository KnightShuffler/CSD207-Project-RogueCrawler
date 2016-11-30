package engine;

import static org.lwjgl.glfw.GLFW.*;

import java.util.HashMap;
import java.util.HashSet;

public class InputManager {
	private HashMap<Integer, Boolean> keyMap, prevKeyMap;
	private static HashSet<Integer> keySet;
	
	public static void init() {
		keySet = new HashSet<>();
	}
	
	public static void addKey(int key) {
		keySet.add(key);
	}
	
	public InputManager() {
		keyMap = new HashMap<>();
		prevKeyMap = new HashMap<>();
		
		for (Integer key : keySet) {
			keyMap.put(key, false);
			prevKeyMap.put(key, false);
		}
	}
	
	public void takeInput(long windowHandle) {
		for (Integer key : keySet) {
			keyMap.put(key, glfwGetKey(windowHandle, key) == 1);
		}
	}
	
	public boolean isDown(int key) {
		return keyMap.get(key);
	}
	
	public boolean isUP(int key) {
		return !keyMap.get(key);
	}
	
	public boolean isPressed(int key) {
		return keyMap.get(key) && !prevKeyMap.get(key);
	}
	
	public boolean isReleased(int key) {
		return !keyMap.get(key) && prevKeyMap.get(key);
	}
	
	public void update() {
		for (Integer key : keySet) {
			prevKeyMap.put(key, keyMap.get(key));
		}
	}
}
