package ca.d30.hackwestern.ui;

import java.awt.Color;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.gfx.Font;
import ca.d30.hackwestern.gfx.Renderer;

public class DeadUI {
	public static void render() {
		Renderer.drawRect(0, 0, Main.width, Main.height, new Color(0, 0, 0, 150));
		
		Font.drawFont("You have died!", -Renderer.xOffset + 10, -Renderer.yOffset + 10, 1, 255, 255, 255);
		Font.drawFont("Press 'space' to return to", -Renderer.xOffset + 10, -Renderer.yOffset + 30, 1, 255, 255, 255);
		Font.drawFont("the main menu!", -Renderer.xOffset + 10, -Renderer.yOffset + 38, 1, 255, 255, 255);
	}
	
	public static void tick() {
		if(Main.instance.input.attack.down) {
			Main.instance.input.attack.down = false;
			
			Main.instance.mainMenu = true;
			
			Renderer.xOffset = 0;
			Renderer.yOffset = 0;
		}
	}
}
