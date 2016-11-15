package engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Sprite {
	private float x, y;
	private float width, height;
	
	private int vboID = 0;
	
	public Sprite() {
		vboID = 0;
	}
	
	protected void finalize() throws Throwable {
		if (vboID != 0) {
			glDeleteBuffers(vboID);
		}
	}
	
	public void init(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		if (vboID == 0) {
			vboID = glGenBuffers();
		}
		
		float[] vertexData = {
				x, y,
				x + width, y,
				x + width, y + height,
				
				x + height, y + height,
				x, y + height,
				x, y
		};
		
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, createBuffer(vertexData), GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
	}
	
	public void draw() {
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
		glDrawArrays(GL_TRIANGLES, 0, 6);
		glDisableVertexAttribArray(0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	private FloatBuffer createBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
