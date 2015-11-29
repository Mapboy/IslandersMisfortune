package ca.d30.hackwestern.world.tiles;

import java.util.Random;

import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.world.Tile;

public class GrassTile extends Tile {

	public GrassTile() {
		super(false);
	}
	Random rand = new Random();
	public void render(int x, int y) {
		Renderer.draw(x * 16, y * 16, 0, 0);
	}
}
