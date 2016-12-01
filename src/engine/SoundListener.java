package engine;

import static org.lwjgl.openal.AL10.*;

import org.joml.Vector3f;

//The listener for the openAL sound
public class SoundListener {
	public SoundListener() {
		this(new Vector3f(0, 0, 0));
	}

	public SoundListener(Vector3f position) {
		alListener3f(AL_POSITION, position.x, position.y, position.z);
		alListener3f(AL_VELOCITY, 0, 0, 0);
	}

	public void setVelocity(Vector3f velocity) {
		alListener3f(AL_VELOCITY, velocity.x, velocity.y, velocity.z);
	}

	public void setPosition(Vector3f position) {
		alListener3f(AL_POSITION, position.x, position.y, position.z);
	}

	// Changes the orientation of the listener
	/*
	 * The at vector points to which direction the listener is facing default
	 * value is (0, 0, -1) (into the screen) The up vector points to direction
	 * that is up for the listener default value is (0, 1, 0) (perpendicular to
	 * the screen upwards
	 */
	public void setOrientation(Vector3f at, Vector3f up) {
		float[] data = new float[6];
		data[0] = at.x;
		data[1] = at.y;
		data[2] = at.z;
		data[3] = up.x;
		data[4] = up.y;
		data[5] = up.z;
		alListenerfv(AL_ORIENTATION, data);
	}
}