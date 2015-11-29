package ca.d30.hackwestern.gfx;

import java.awt.image.BufferedImage;

public class Sprite {
	public int width, height;
	public BufferedImage image;
	
	public Sprite(BufferedImage image) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
}
