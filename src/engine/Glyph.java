package engine;

import java.util.Comparator;

import org.joml.Vector4f;

public class Glyph {
	private int texture;
	public float depth;
	private Vertex[] vertices;
	
	public Glyph(Vector4f destRect, Vector4f uvRect, ColorRGBA8 color, int texture, float depth) {
		this.texture = texture;
		this.depth = depth;
		
		vertices = new Vertex[4];
		
		//Bottom Left
		vertices[0] = new Vertex(
				destRect.x,
				destRect.y,
				color.r,
				color.g,
				color.b,
				color.a,
				uvRect.x,
				uvRect.y
				);
		
		//Top Left
		vertices[1] = new Vertex(
				destRect.x,
				destRect.y + destRect.w,
				color.r,
				color.g,
				color.b,
				color.a,
				uvRect.x,
				uvRect.y + uvRect.w
				);
		
		//Top Right
		vertices[2] = new Vertex(
						destRect.x + destRect.z,
						destRect.y + destRect.w,
						color.r,
						color.g,
						color.b,
						color.a,
						uvRect.x + uvRect.z,
						uvRect.y + uvRect.w
						);
		//Bottom Right
		vertices[3] = new Vertex(
						destRect.x + destRect.z,
						destRect.y,
						color.r,
						color.g,
						color.b,
						color.a,
						uvRect.x + uvRect.z,
						uvRect.y
						);
	}
	
	public int getTexture() { return texture; }
	
	public Vertex[] getVertices() { return vertices; }
}

class GlyphDepthComparator implements Comparator<Glyph> {
	boolean frontToBack;
	
	public GlyphDepthComparator(boolean f) {
		frontToBack  = f;
	}

	@Override
	public int compare(Glyph g1, Glyph g2) {
		if (frontToBack) {
			if (g1.depth < g2.depth) {
				return 1;
			}
			else if (g1.depth > g2.depth) {
				return -1;
			}
			else {
				return 0;
			}
		}
		else {
			if (g1.depth < g2.depth) {
				return -1;
			}
			else if (g1.depth > g2.depth) {
				return 1;
			}
			else {
				return 0;
			}
		}
		
	}
	
}

class GlyphTextureComparator implements Comparator<Glyph> {

	@Override
	public int compare(Glyph g1, Glyph g2) {
		return g1.getTexture() - g2.getTexture();
	}

}
