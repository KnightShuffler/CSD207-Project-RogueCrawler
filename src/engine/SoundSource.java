package engine;

import static org.lwjgl.openal.AL10.*;

import org.joml.Vector3f;

public class SoundSource {

	// The ID for the source
	private final int sourceId;

	public SoundSource(boolean loop, boolean relative) {
		sourceId = alGenSources();
		// Should the source's sound loop or not
		if (loop) {
			alSourcei(sourceId, AL_LOOPING, AL_TRUE);
		}
		// Should the position of the source be relative to the listener or not
		if (relative) {
			alSourcei(sourceId, AL_SOURCE_RELATIVE, AL_TRUE);
		}
	}

	public void setBuffer(int bufferId) {
		// stop the sound playing (if any)
		stop();
		// give the source a buffer
		alSourcei(sourceId, AL_BUFFER, bufferId);
	}

	// give the source a position
	public void setPosition(Vector3f position) {
		alSource3f(sourceId, AL_POSITION, position.x, position.y, position.z);
	}

	// set the speed of the source
	public void setSpeed(Vector3f speed) {
		alSource3f(sourceId, AL_VELOCITY, speed.x, speed.y, speed.z);
	}

	// set the gain of the source's sound
	public void setGain(float gain) {
		alSourcef(sourceId, AL_GAIN, gain);
	}

	// set any particular property for the source
	public void setProperty(int param, float value) {
		alSourcef(sourceId, param, value);
	}

	// play the source's sound
	public void play() {
		alSourcePlay(sourceId);
	}

	// is the source playing a sound
	public boolean isPlaying() {
		return alGetSourcei(sourceId, AL_SOURCE_STATE) == AL_PLAYING;
	}

	// pause the sound of the source
	public void pause() {
		alSourcePause(sourceId);
	}

	// stop the sound of the source
	public void stop() {
		alSourceStop(sourceId);
	}

	// destroy the source
	public void destroy() {
		// stop the sound playing (if any)
		stop();
		// delete the source
		alDeleteSources(sourceId);
	}
}