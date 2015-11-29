package ca.d30.hackwestern.ui;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.gfx.Renderer;

public class HealthUI {
	private static UIBox box;
	public static void draw() {
		if(box == null) box = new UIBox(-12, -4, 10, 2);
		box.render();
		
		int fullHearts = (int)Math.floor(Main.instance.player.health/2);
		boolean halfHeart = Main.instance.player.health % 2 == 1;

		for(int i = 0; i < 8; i++) {
			if(i < fullHearts) Renderer.draw(2 -Renderer.xOffset + i * 8, -Renderer.yOffset + 2, 3, 7, 6);
			else if(i == fullHearts && halfHeart) Renderer.draw(2 -Renderer.xOffset + i * 8, -Renderer.yOffset + 2, 3, 8, 6);
			else Renderer.draw(2 -Renderer.xOffset + i * 8, -Renderer.yOffset + 2, 3, 9, 6);
		}
	}
}
