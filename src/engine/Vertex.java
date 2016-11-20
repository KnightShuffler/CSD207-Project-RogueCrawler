package engine;

import java.nio.FloatBuffer;
import java.util.Vector;

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
	
	public Vertex(float x, float y, byte r, byte g, byte b, byte a, float u, float v) {
		position = new Position(x, y);
		color = new ColorRGBA8(r, g, b, a);
		uv = new UV(u, v);
	}
	
	void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}
	
	void setColor(byte r, byte g, byte b, byte a) {
		color.r = r;
		color.g = g;
		color.g = b;
		color.g = a;
	}
	
	void setUV(float u, float v) {
		uv.u = u;
		uv.v = v;
	}
}
