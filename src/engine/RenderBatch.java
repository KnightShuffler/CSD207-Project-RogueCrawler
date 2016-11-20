package engine;

public class RenderBatch {
	private int texture;
	private int numIndices;
	private int offset;
	
	public RenderBatch(int texture, int numIndices, int offset) {
		this.texture = texture;
		this.numIndices = numIndices;
		this.offset = offset;
	}
	
	public int getTexture() { return texture; }
	public int getNumIndices() { return numIndices; }
	public int getOffset() { return offset; }
	
	public void addIndices() { 
		numIndices += 6;
	}
}
