package engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;


public class Texture {
	private int id;		//texture id
	private int width;	//width of image
	private int height;	//height of image
	
	public Texture(String filename) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(new File(filename));	//read the image
			
			//get dimensions
			width = bi.getWidth();
			height = bi.getHeight();
			
			//raw pixels data from the image
			int [] pixels_raw = new int[width * height * 4];	//width * height is the number of pixels, 4 fields (RGBA) for each pixel
			pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);
			
			//pixel buffer
			ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
			
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					int pixel = pixels_raw[i * width + j];
					pixels.put((byte)((pixel >> 16) & 0xFF));	//Red
					pixels.put((byte)((pixel >>  8) & 0xFF));	//Green
					pixels.put((byte)((pixel      ) & 0xFF));	//Blue
					pixels.put((byte)((pixel >> 24) & 0xFF));	//Alpha
				}
			}
			
			//flip the buffer because that's how opengl likes it
			pixels.flip();
			
			//generate texture id
			id = glGenTextures();
			
			glBindTexture(GL_TEXTURE_2D, id);
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	protected void finalize() throws Throwable {
		glDeleteTextures(id);
		super.finalize();
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
			//change the active texture, sampler goes from 0-31 and so we'll get the required sampler
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
		}
	}
}
