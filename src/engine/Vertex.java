package engine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

//Class that holds all of a single vertex's properties
public class Vertex {
	public Position position;
	public ColorRGBA8 color;
	public UV uv;

	public static final int POSITION_SIZE = 2;
	public static final int COLOR_SIZE = 4;
	public static final int UV_SIZE = 2;

	public static final int VERTEX_SIZE = POSITION_SIZE + COLOR_SIZE + UV_SIZE;

	public Vertex() {
		position = new Position();
		color = new ColorRGBA8();
		uv = new UV();
	}

	public Vertex(float x, float y, int r, int g, int b, int a, float u, float v) {
		position = new Position(x, y);
		color = new ColorRGBA8(r, g, b, a);
		uv = new UV(u, v);
	}

	void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}

	void setColor(int r, int g, int b, int a) {
		color.r = r;
		color.g = g;
		color.g = b;
		color.g = a;
	}

	void setUV(float u, float v) {
		uv.u = u;
		uv.v = v;
	}

	public static FloatBuffer createBuffer(Vertex[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length * VERTEX_SIZE);
		for (int i = 0; i < data.length; i++) {
			buffer.put(data[i].position.x);
			buffer.put(data[i].position.y);

			buffer.put((float) data[i].color.r / 255f);
			buffer.put((float) data[i].color.g / 255f);
			buffer.put((float) data[i].color.b / 255f);
			buffer.put((float) data[i].color.a / 255f);

			buffer.put(data[i].uv.u);
			buffer.put(data[i].uv.v);
		}
		buffer.flip();
		return buffer;
	}

	public static IntBuffer createBuffer(int numIndices) {
		IntBuffer buffer = BufferUtils.createIntBuffer(numIndices);

		for (int i = 0; i < numIndices / 6; i++) {
			buffer.put(i * 4 + 0);
			buffer.put(i * 4 + 1);
			buffer.put(i * 4 + 2);
			buffer.put(i * 4 + 2);
			buffer.put(i * 4 + 3);
			buffer.put(i * 4 + 0);
		}
		buffer.flip();
		return buffer;
	}
}
