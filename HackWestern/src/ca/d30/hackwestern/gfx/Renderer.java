package ca.d30.hackwestern.gfx;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import ca.d30.hackwestern.Main;

public class Renderer {
	public static int width, height;
	public static int scale;
	
	public static int xOffset, yOffset;
	
	public Renderer(int scale, int width, int height) {
		Renderer.width = width;
		Renderer.height = height;
		Renderer.scale = scale;
	}
	
	public static void draw(int x, int y, int sx, int sy) {
		Graphics g = Main.instance.pixels.getGraphics();
		Sprite s = Main.instance.spritesheet.getSprite(sx, sy);
		
		x += xOffset;
		y += yOffset;
		
		g.drawImage(s.image, (x * scale), (y * scale), s.image.getWidth() * scale, s.image.getHeight() * scale, null, null);
		
		g.dispose();
	}
	
	public static void drawSprite(Sprite s, int x, int y, int size) {
		Graphics g = Main.instance.pixels.getGraphics();
		
		x += xOffset;
		y += yOffset;
		
		g.drawImage(s.image, (x * scale), (y * scale), size * scale, size * scale, null, null);
		
		g.dispose();
	}
	
	public static void drawSprite(Sprite s, int x, int y, int size, float opacity) {
		Graphics2D g = Main.instance.pixels.createGraphics();
		
		x += xOffset;
		y += yOffset;
		
		int rule = AlphaComposite.SRC_OVER;
        Composite comp = AlphaComposite.getInstance(rule, opacity);
		
        g.setComposite(comp);
		g.drawImage(s.image, (x * scale), (y * scale), size * scale, size * scale, null, null);
		
		g.dispose();
	}
	
	public static void drawSprite(Sprite s, int x, int y, int size, float opacity, int red, int green, int blue) {
		Graphics2D g = Main.instance.pixels.createGraphics();
		
		x += xOffset;
		y += yOffset;
		
		BufferedImage image = new BufferedImage(s.image.getWidth(), s.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for(int y1 = 0; y1 < image.getHeight(); y1++) {
			for(int x1 = 0; x1 < image.getWidth(); x1++) {
				Color a = new Color(s.image.getRGB(x1, y1), true);
				
				if(a.getAlpha() < 255) continue;
				
				int re = a.getRed() + red;
				int gr = a.getGreen() + green;
				int bl = a.getBlue() + blue;
				
				if(re < 0) re = 0;
				if(gr < 0) gr = 0;
				if(bl < 0) bl = 0;

				if(re > 255) re = 255;
				if(gr > 255) gr = 255;
				if(bl > 255) bl = 255;
				
				a = new Color(re, gr, bl, (int)(a.getAlpha() * opacity));
				image.setRGB(x1, y1, a.getRGB());
			}
		}
		
		g.drawImage(image, (x * scale), (y * scale), size * scale, size * scale, null, null);
		
		g.dispose();
	}
	
	public static void draw(int x, int y, int sx, int sy, int size) {
		Graphics g = Main.instance.pixels.getGraphics();
		Sprite s = Main.instance.spritesheet.getSprite(sx, sy);
		
		x += xOffset;
		y += yOffset;
		
		g.drawImage(s.image, (x * scale), (y * scale), size * scale, size * scale, null, null);
		
		g.dispose();
	}
	
	public static void draw(int x, int y, int sx, int sy, int size, int clippedWidth, int clippedHeight) {
		Graphics g = Main.instance.pixels.getGraphics();
		Sprite s = Main.instance.spritesheet.getSprite(sx, sy);
		
		BufferedImage fin = new BufferedImage(s.image.getWidth(), s.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		fin.getGraphics().drawImage(s.image.getSubimage(0, 0, clippedWidth, clippedHeight), 0, 0, null);
		
		x += xOffset;
		y += yOffset;
		
		g.drawImage(fin, (x * scale), (y * scale), size * scale, size * scale, null, null);
		
		g.dispose();
	}
	
	public static void draw(int x, int y, int sx, int sy, int size, int clippedWidth, int clippedHeight, int red, int green, int blue) {
		Graphics g = Main.instance.pixels.getGraphics();
		Sprite s = Main.instance.spritesheet.getSprite(sx, sy);
		
		BufferedImage fin = new BufferedImage(s.image.getWidth(), s.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		fin.getGraphics().drawImage(s.image.getSubimage(0, 0, clippedWidth, clippedHeight), 0, 0, null);
		
		x += xOffset;
		y += yOffset;
		
		for(int y1 = 0; y1 < fin.getHeight(); y1++) {
			for(int x1 = 0; x1 < fin.getWidth(); x1++) {
				Color a = new Color(fin.getRGB(x1, y1), true);
				
				if(a.getAlpha() < 255) continue;
				
				int re = a.getRed() + red;
				int gr = a.getGreen() + green;
				int bl = a.getBlue() + blue;
				
				if(re < 0) re = 0;
				if(gr < 0) gr = 0;
				if(bl < 0) bl = 0;

				if(re > 255) re = 255;
				if(gr > 255) gr = 255;
				if(bl > 255) bl = 255;
				
				a = new Color(re, gr, bl, a.getAlpha());
				fin.setRGB(x1, y1, a.getRGB());
			}
		}
		
		g.drawImage(fin, (x * scale), (y * scale), size * scale, size * scale, null, null);
		
		g.dispose();
	}
	
	public static void drawRect(int x, int y, int width, int height, Color c) {
		Graphics g = Main.instance.pixels.getGraphics();
		g.setColor(c);
		
		x += xOffset;
		y += yOffset;
		
		g.fillRect(x * scale, y * scale, width * scale, height * scale);
		
		g.dispose();
	}
}