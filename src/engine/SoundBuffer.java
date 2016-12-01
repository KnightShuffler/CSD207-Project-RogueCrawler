package engine;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBVorbisInfo;

public class SoundBuffer {
	private final int bufferID;

	public SoundBuffer(String file) throws IOException {
		// Generate the buffer
		bufferID = alGenBuffers();

		// Allocate space for the audio buffer
		STBVorbisInfo info = STBVorbisInfo.malloc();

		try {
			ShortBuffer pcm = readVorbis(file, 4 * 1024, info);

			alBufferData(bufferID, info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16, pcm,
					info.sample_rate());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getBufferID() {
		return bufferID;
	}

	public void destroy() {
		alDeleteBuffers(bufferID);
	}

	/*
	 * Following Code taken directly from
	 * https://www.gitbook.com/book/lwjglgamedev/3d-game-development-with-lwjgl
	 */

	// Read an OGG file and convert it to PCM
	private ShortBuffer readVorbis(String resource, int bufferSize, STBVorbisInfo info) throws Exception {
		ByteBuffer vorbis;
		vorbis = engine.Utils.ioResourceToByteBuffer(resource, bufferSize);

		IntBuffer error = BufferUtils.createIntBuffer(1);
		long decoder = stb_vorbis_open_memory(vorbis, error, null);
		if (decoder == NULL) {
			throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error.get(0));
		}

		stb_vorbis_get_info(decoder, info);

		int channels = info.channels();

		int lengthSamples = stb_vorbis_stream_length_in_samples(decoder);

		ShortBuffer pcm = BufferUtils.createShortBuffer(lengthSamples);

		pcm.limit(stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm) * channels);
		stb_vorbis_close(decoder);

		return pcm;
	}
}
