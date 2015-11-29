package ca.d30.hackwestern.gfx;

public class Font {
	public static SpriteSheet fontSheet;
	
	private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ  " +
											 "0123456789.,!?'\"-+=/\\%()<>:;";
	
	public static void drawFont(String text, int x, int y) {
		text = text.toUpperCase();
		
		for(int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			int index = characters.indexOf(c);
			
			if(index >= 28) {
				index -= 28;
				Renderer.drawSprite(fontSheet.getSprite(index, 1), x + (i * 4), y, 4);
			} else {
				Renderer.drawSprite(fontSheet.getSprite(index, 0), x + (i * 4), y, 4);
			}
		}
	}
	
	public static void drawFont(String text, int x, int y, float opacity, int red, int green, int blue) {
		text = text.toUpperCase();
		
		for(int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			int index = characters.indexOf(c);
			
			if(index >= 28) {
				index -= 28;
				Renderer.drawSprite(fontSheet.getSprite(index, 1), x + (i * 4), y, 4, opacity, red, green, blue);
			} else {
				Renderer.drawSprite(fontSheet.getSprite(index, 0), x + (i * 4), y, 4, opacity, red, green, blue);
			}
		}
	}
}
