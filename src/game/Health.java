package game;

public class Health {
	private int max;
	private int current;

	public Health(int m) {
		max = current = m;
	}

	public int getMaxHealth() {
		return max;
	}

	public int getCurrentHealth() {
		return current;
	}

	public void addMaxHealth() {
		max += 1;
	}

	public void reduceMaxHealth() {
		max -= 1;
	}

	public void addHealth(int h) {
		current = (current + h) > max ? max : current + h;
	}

	public void reduceHealth(int h) {
		current = (current - h) < 0 ? 0 : current - h;
	}
}
