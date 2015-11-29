package ca.d30.hackwestern.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	public int width, height;
	public int scale;
	
	private BufferedImage image;
	
	public SpriteSheet(String filePath, int scale) {
		this.scale = scale;
		
		try {
			this.image = ImageIO.read(SpriteSheet.class.getResourceAsStream(filePath));
			
			this.width = image.getWidth();
			this.height = image.getHeight();
		} catch (IOException e) {
			System.err.println("Unable to load spritesheet from: " + filePath);
			System.exit(1);
		}
	}
	
	public Sprite getSprite(int x, int y) {
		return new Sprite(image.getSubimage(x * scale, y * scale, scale, scale));
	}
}
