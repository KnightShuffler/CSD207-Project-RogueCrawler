package engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.Collections;

import org.joml.Vector4f;

public class SpriteBatch {
	private int vaoID;
	private int vboID;
	private int indexID;
	
	private ArrayList<Glyph> glyphs;
	private ArrayList<RenderBatch> renderBatches;
	
	public static final int SORT_FRONT_TO_BACK = 0;
	public static final int SORT_BACK_TO_FRONT = 1;
	public static final int SORT_BY_TEXTURE = 2;
	
	private int sortType;
	
	public SpriteBatch() {
		glyphs = new ArrayList<>();
		renderBatches = new ArrayList<>();
		
		vaoID = glGenVertexArrays();
		vboID = glGenBuffers();
		indexID = glGenBuffers();
		
		glBindVertexArray(vaoID);
		
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 8 << 2, 0 << 2);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 8 << 2, 2 << 2);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 << 2, 6 << 2);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexID);
		
		glBindVertexArray(0);
	}
	
	public void begin(int sortType) {
		this.sortType = sortType;
		glyphs.clear();
		renderBatches.clear();
	}
	
	public void addGlyph(Vector4f destRect, Vector4f uvRect, ColorRGBA8 color, int texture, float depth) {
		glyphs.add(new Glyph(destRect, uvRect, color, texture, depth));
	}
	
	public void addGlyph(Glyph g) {
		glyphs.add(g);
	}
	
	public void end() {
		sortGlyphs();
		createRenderBatches();
	}
	
	public void render() {
		glBindVertexArray(vaoID);
		for (RenderBatch r : renderBatches) {
			Texture.bind(0, r.getTexture());
			glDrawElements(GL_TRIANGLES, r.getNumIndices(), GL_UNSIGNED_INT, r.getOffset() << 2);
		}
		glBindVertexArray(0);
	}
	
	private void sortGlyphs() {
		if (sortType == SORT_BY_TEXTURE) {
			glyphs.sort(new GlyphTextureComparator());
		}
		else {
			glyphs.sort(new GlyphDepthComparator(sortType == SORT_FRONT_TO_BACK));
		}
		
		/*for (Glyph g : glyphs) {
			System.out.print(g.depth + " ");
		}
		System.out.println();*/
	}
	
	private void createRenderBatches() {
		if (glyphs.isEmpty()) {
			return ;
		}
		
		Vertex[] vertices = new Vertex[glyphs.size() * 4];
		int cv = 0;	//current vertex
		int offset = 0;	//offset for the next render batch
		
		renderBatches.add(new RenderBatch(glyphs.get(0).getTexture(), 0, offset));
		
		vertices[cv++] = glyphs.get(0).getVertices()[0];
		vertices[cv++] = glyphs.get(0).getVertices()[1];
		vertices[cv++] = glyphs.get(0).getVertices()[2];
		vertices[cv++] = glyphs.get(0).getVertices()[3];
		renderBatches.get(renderBatches.size() - 1).addIndices();
		offset += 6;
		
		for (int i = 1; i < glyphs.size(); i++) {
			if (glyphs.get(i).getTexture() != glyphs.get(i - 1).getTexture()) {
				renderBatches.add(new RenderBatch(glyphs.get(i).getTexture(), 0, offset));
			}
			
			vertices[cv++] = glyphs.get(i).getVertices()[0];
			vertices[cv++] = glyphs.get(i).getVertices()[1];
			vertices[cv++] = glyphs.get(i).getVertices()[2];
			vertices[cv++] = glyphs.get(i).getVertices()[3];
			renderBatches.get(renderBatches.size() - 1).addIndices();
			offset += 6;
		}
		
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, Vertex.createBuffer(vertices), GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Vertex.createBuffer(offset), GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		/*for (RenderBatch r : renderBatches) {
			System.out.print(r.getTexture() + " ");
		}
		System.out.println();*/
	}
}
