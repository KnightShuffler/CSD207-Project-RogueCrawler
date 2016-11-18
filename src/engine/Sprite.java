package engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Sprite {
	private float x, y;
	private float width, height;
	
	private int drawCount;
	
	private int vboID = 0;
	private int cID = 0;
	private int indexID = 0;
	
	public Sprite() {
		vboID = 0;
		cID = 0;
		indexID = 0;
	}
	
	protected void finalize() throws Throwable {
		if (vboID != 0) {
			glDeleteBuffers(vboID);
		}
	}
	
	public void init(float[] vertexData, float[] colorData, int[] indices) {
		if (vboID == 0) {
			vboID = glGenBuffers();
		}
		
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertexData), GL_STATIC_DRAW);
		
		if (cID == 0) {
			cID = glGenBuffers();
		}
		
		glBindBuffer(GL_ARRAY_BUFFER, cID);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(colorData), GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		if (indexID == 0) {
			indexID = glGenBuffers();
		}
		
		drawCount = indices.length;
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createBuffer(indices), GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void draw() {
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, /*stride*/(2) << 2, 0 << 2);
		
		glBindBuffer(GL_ARRAY_BUFFER, cID);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, /*stride*/(4) << 2, 0 << 2);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexID);
		glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	private FloatBuffer createBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private IntBuffer createBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}