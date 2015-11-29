package ca.d30.hackwestern.ui;

import java.awt.Color;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.gfx.Font;
import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.gfx.SpriteSheet;

public class MainMenuUI {
	private static SpriteSheet main = new SpriteSheet("/main.png", 35);
	private static int choice = 0;
	public static void render() {
		Renderer.drawRect(0, 0, Main.width, Main.height, Color.BLACK);
		
		for(int i = 0; i < 5; i++) {
			Renderer.drawSprite(main.getSprite(i, 0), 7 + i*35, 7, 35, 1, 255, 255, 255);
		}
		
		if(choice == 0) Font.drawFont("> Play", 10, Main.height/5-32, 1, 255, 255, 255);
		else Font.drawFont("Play", 10, Main.height/5-32, 1, 255, 255, 255);
		
		if(choice == 1) Font.drawFont("> Exit", 10, Main.height/5-24, 1, 255, 255, 255);
		else Font.drawFont("Exit", 10, Main.height/5-24, 1, 255, 255, 255);
	}
	
	public static void tick() {
		if(Main.instance.input.up.down && choice > 0) {
			choice--;
			Main.instance.input.up.down = false;
		}
		
		if(Main.instance.input.down.down && choice < 1) {
			choice++;
			Main.instance.input.down.down = false;
		}
		
		if(Main.instance.input.attack.down) {
			Main.instance.input.attack.down = false;
			if(choice == 0) {
				Main.instance.mainMenu = false;
				Main.instance.setupWorld();
			} else {
				System.exit(0);
			}
		}
		
	}
}
