package engine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
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
	
	public static FloatBuffer createVertexBuffer(Vector<Vertex> vertices) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.size() * VERTEX_SIZE);
		for (Vertex v : vertices) {
			buffer.put(v.position.x);
			buffer.put(v.position.y);
			
			buffer.put(v.color.r / 255f);
			buffer.put(v.color.g / 255f);
			buffer.put(v.color.b / 255f);
			buffer.put(v.color.a / 255f);
			
			buffer.put(v.uv.u);
			buffer.put(v.uv.v);
		}
		buffer.flip();
		
		return buffer;
	}
	
	public static IntBuffer createIndexBuffer(int numGlyphs) {
		IntBuffer buffer = BufferUtils.createIntBuffer(numGlyphs * 6);
		for (int i = 0; i < numGlyphs; i++) {
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
