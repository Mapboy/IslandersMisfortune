package ca.d30.hackwestern.ui;

import ca.d30.hackwestern.gfx.Renderer;

public class UIBox {
	public int x, y, width, height;
	
	public UIBox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void render() {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(x == 0) {
					if(y == 0) {
						Renderer.draw((x * 8) + this.x - Renderer.xOffset, (y * 8) + this.y - Renderer.yOffset, 4, 5, 8);
					} else if(y == height-1) {
						Renderer.draw((x * 8) + this.x - Renderer.xOffset, (y * 8) + this.y - Renderer.yOffset, 4, 7, 8);
					} else {
						Renderer.draw((x * 8) + this.x - Renderer.xOffset, (y * 8) + this.y - Renderer.yOffset, 4, 6, 8);
					}
				} else if(x == width-1) {
					if(y == 0) {
						Renderer.draw((x * 8) + this.x - Renderer.xOffset, (y * 8) + this.y - Renderer.yOffset, 6, 5, 8);
					} else if(y == height-1) {
						Renderer.draw((x * 8) + this.x - Renderer.xOffset, (y * 8) + this.y - Renderer.yOffset, 6, 7, 8);
					} else {
						Renderer.draw((x * 8) + this.x - Renderer.xOffset, (y * 8) + this.y - Renderer.yOffset, 6, 6, 8);
					}
				} else if(y == 0) {
					Renderer.draw((x * 8) + this.x - Renderer.xOffset, (y * 8) + this.y - Renderer.yOffset, 5, 5, 8);
				} else if(y == height-1) {
					Renderer.draw((x * 8) + this.x - Renderer.xOffset, (y * 8) + this.y - Renderer.yOffset, 5, 7, 8);
				} else {
					Renderer.draw((x * 8) + this.x - Renderer.xOffset, (y * 8) + this.y - Renderer.yOffset, 5, 6, 8);
				}
			}
		}
	}
}
