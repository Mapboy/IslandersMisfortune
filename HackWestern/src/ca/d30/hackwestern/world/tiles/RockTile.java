package ca.d30.hackwestern.world.tiles;

import java.awt.Color;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.world.Tile;
import ca.d30.hackwestern.world.World;

public class RockTile extends Tile {

	public RockTile() {
		super(true);
	}
	
	public void render(int x, int y) {
		World w = Main.instance.world;
		
		boolean left = (w.getID(x-1, y) == id);
		boolean right = (w.getID(x+1, y) == id);
		boolean up = (w.getID(x, y-1) == id);
		boolean down = (w.getID(x, y+1) == id);
		
		if(!left && !right && !up && !down) Renderer.draw(x * 16, y * 16, 1, 0);

		if(!left && !right && up && !down) Renderer.draw(x * 16, y * 16, 2, 0);
		if(!left && right && !up && !down) Renderer.draw(x * 16, y * 16, 2, 1);
		if(!left && !right && !up && down) Renderer.draw(x * 16, y * 16, 2, 2);
		if(left && !right && !up && !down) Renderer.draw(x * 16, y * 16, 2, 3);
		
		if(!left && !right && up && down) Renderer.draw(x * 16, y * 16, 3, 0);
		if(left && right && !up && !down) Renderer.draw(x * 16, y * 16, 3, 1);
		
		if(!left && right && !up && down) Renderer.draw(x * 16, y * 16, 1, 1);
		if(left && !right && !up && down) Renderer.draw(x * 16, y * 16, 1, 2);
		if(left && !right && up && !down) Renderer.draw(x * 16, y * 16, 1, 3);
		if(!left && right && up && !down) Renderer.draw(x * 16, y * 16, 1, 4);
		
		if(left && right && up && !down) Renderer.draw(x * 16, y * 16, 3, 6);
		if(left && right && !up && down) Renderer.draw(x * 16, y * 16, 3, 4);
		if(left && !right && up && down) Renderer.draw(x * 16, y * 16, 3, 5);
		if(!left && right && up && down) Renderer.draw(x * 16, y * 16, 3, 3);
		
		if(left && right && up && down) Renderer.draw(x * 16, y * 16, 3, 2);
		
		/* ----- */
		
		boolean upperLeft = (w.getID(x-1, y-1) == id);
		boolean upperRight = (w.getID(x+1, y-1) == id);
		boolean bottomLeft = (w.getID(x-1, y+1) == id);
		boolean bottomRight = (w.getID(x+1, y+1) == id);
		
		if(left && up && upperLeft) Renderer.drawRect(x * 16, y * 16, 5, 5, new Color(160, 160, 160));
		if(right && up && upperRight) Renderer.drawRect((x * 16) + 11, y * 16, 5, 5, new Color(160, 160, 160));
		if(left && down && bottomLeft) Renderer.drawRect(x * 16, (y * 16) + 11, 5, 5, new Color(160, 160, 160));
		if(right && down && bottomRight) Renderer.drawRect((x * 16) + 11, (y * 16) + 11, 5, 5, new Color(160, 160, 160));
	}
}
