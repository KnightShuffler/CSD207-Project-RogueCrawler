package engine;

import java.nio.FloatBuffer;
import java.util.Vector;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import sun.font.CreatedFontTracker;

//Small structures for holding vertex position, color and UV coordinates
class Position {
	public float x, y;
	
	public Position() {
		//Empty
	}
	
	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}
}

class ColorRGBA8 {
	public byte r, g, b, a;
	
	public ColorRGBA8() {
		//Empty
	}
	
	public ColorRGBA8(byte r, byte g, byte b, byte a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
}

class UV {
	public float u, v;
	
	public UV() {
		//Empty
	}
	
	public UV(float u, float v) {
		this.u = u;
		this.v = v;
	}
}

//Class that holds all of a single vertex's properties
public class Vertex {
	public Position position;
	public ColorRGBA8 color;
	public UV uv;
	
	public static final int POSITION_SIZE = 2;
	public static final int COLOR_SIZE = 4;
	public static final int UV_SIZE = 2;
	
	public static final int VERTEX_SIZE = 2 + 4;
	
	public static FloatBuffer getBuffer(Vector<Vertex> vertices) {
		float [] bufferData = new float[vertices.size() * (VERTEX_SIZE)];
		
		for (int i = 0; i < vertices.size(); i++) {
			int pointer = 0;
			//Position data
			bufferData[(i * VERTEX_SIZE) + pointer++] = vertices.elementAt(i).position.x;
			bufferData[(i * VERTEX_SIZE) + pointer++] = vertices.elementAt(i).position.y;
			//Color data
			bufferData[(i * VERTEX_SIZE) + pointer++] = (float)vertices.elementAt(i).color.r / 255.0f;
			bufferData[(i * VERTEX_SIZE) + pointer++] = (float)vertices.elementAt(i).color.g / 255.0f;;
			bufferData[(i * VERTEX_SIZE) + pointer++] = (float)vertices.elementAt(i).color.b / 255.0f;;
			bufferData[(i * VERTEX_SIZE) + pointer++] = (float)vertices.elementAt(i).color.a / 255.0f;;
			//UV data
			bufferData[(i * VERTEX_SIZE) + pointer++] = vertices.elementAt(i).uv.u;
			bufferData[(i * VERTEX_SIZE) + pointer++] = vertices.elementAt(i).uv.v;
		}
		//Create the FloatBuffer object
		FloatBuffer buffer = BufferUtils.createFloatBuffer(bufferData.length);
		//Set the data in the FloatBuffer
		buffer.put(bufferData);
		//flip the buffer because that's how OpenGL likes it
		buffer.flip();
		
		return buffer;
	}
	
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
