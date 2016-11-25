package engine;

import org.joml.Vector4i;

public class ColorRGBA8 {
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
	
	public ColorRGBA8(Vector4i c) {
		this.r = (byte)c.x;
		this.g = (byte)c.y;
		this.b = (byte)c.z;
		this.a = (byte)c.w;
	}
	
	public static final ColorRGBA8 WHITE	=	new ColorRGBA8((byte)255, (byte)255, (byte)255, (byte)255);
	public static final ColorRGBA8 RED 		=	new ColorRGBA8((byte)255, (byte)0, (byte)0, (byte)255);
	public static final ColorRGBA8 BLUE		=	new ColorRGBA8((byte)0, (byte)255, (byte)0, (byte)255);
	public static final ColorRGBA8 GREEN	=	new ColorRGBA8((byte)0, (byte)0, (byte)255, (byte)255);
	public static final ColorRGBA8 YELLOW	=	new ColorRGBA8((byte)255, (byte)255, (byte)0, (byte)255);
	public static final ColorRGBA8 MAGENTA	=	new ColorRGBA8((byte)255, (byte)0, (byte)255, (byte)255);
	public final static ColorRGBA8 CYAN 	=	new ColorRGBA8((byte)0, (byte)255, (byte)255, (byte)255);
	public final static ColorRGBA8 BLACK 	=	new ColorRGBA8((byte)0, (byte)0, (byte)0, (byte)255);
}
