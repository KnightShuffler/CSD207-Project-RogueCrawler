package engine;

import org.joml.Vector4i;

public class ColorRGBA8 {
	public int r, g, b, a;
	
	public ColorRGBA8() {
		//Empty
	}
	
	public ColorRGBA8(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public ColorRGBA8(Vector4i c) {
		this.r = c.x;
		this.g = c.y;
		this.b = c.z;
		this.a = c.w;
	}
	
	public static final ColorRGBA8 WHITE	=	new ColorRGBA8(255, 255, 255, 255);
	public static final ColorRGBA8 RED 		=	new ColorRGBA8(255, 0, 0, 255);
	public static final ColorRGBA8 GREEN	=	new ColorRGBA8(0, 255, 0, 255);
	public static final ColorRGBA8 BLUE		=	new ColorRGBA8(0, 0, 255, 255);
	public static final ColorRGBA8 YELLOW	=	new ColorRGBA8(255, 255, 0, 255);
	public static final ColorRGBA8 MAGENTA	=	new ColorRGBA8(255, 0, 255, 255);
	public final static ColorRGBA8 CYAN 	=	new ColorRGBA8(0, 255, 255, 255);
	public final static ColorRGBA8 BLACK 	=	new ColorRGBA8(0, 0, 0, 255);
	
	public String toString() {
		return String.format("R: %d, G: %d, B: %d, A: %d", r, g, b, a);
	}
}
