package ca.d30.hackwestern.world.tiles;

import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.world.Tile;

public class TreeTile extends Tile {
	public TreeTile() {
		super(true);
	}
	
	public void render(int x, int y) {
		Tile.GRASS.render(x, y);
		Renderer.draw(x*16, y*16, 0, 1);
	}

}
