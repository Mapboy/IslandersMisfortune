package ca.d30.hackwestern.world.tiles;

import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.world.Tile;

public class StoneTile extends Tile {
	public StoneTile() {
		super(true);
	}
	
	public void render(int x, int y) {
		Renderer.draw(x * 16, y * 16, 4, 4);
	}

}
