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
}
