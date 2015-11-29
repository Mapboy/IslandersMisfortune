package ca.d30.hackwestern.world.tiles;

import java.awt.Color;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.world.Tile;
import ca.d30.hackwestern.world.World;

public class WaterTile extends Tile {
	public WaterTile() {
		super(false);
	}
	
	public void render(int x, int y) {
		World w = Main.instance.world;
		
		boolean left = (w.getID(x-1, y) == id);
		boolean right = (w.getID(x+1, y) == id);
		boolean up = (w.getID(x, y-1) == id);
		boolean down = (w.getID(x, y+1) == id);
		boolean upperLeft = (w.getID(x-1, y-1) == id);
		boolean upperRight = (w.getID(x+1, y-1) == id);
		boolean bottomLeft = (w.getID(x-1, y+1) == id);
		boolean bottomRight = (w.getID(x+1, y+1) == id);
		
		if(!left && x-1<0) {
			left = true;
			right = true;
			up = true;
			down = true;
			
			upperLeft = true;
			upperRight = true;
			bottomLeft = true;
			bottomRight = true;
		}
		
		if(!right && x+1>=w.mapWidth) {
			left = true;
			right = true;
			up = true;
			down = true;
			
			upperLeft = true;
			upperRight = true;
			bottomLeft = true;
			bottomRight = true;
		}
		
		if(!up && y-1<0) {
			left = true;
			right = true;
			up = true;
			down = true;
			
			upperLeft = true;
			upperRight = true;
			bottomLeft = true;
			bottomRight = true;
		}
		
		if(!down && y+1>=w.mapHeight) {
			left = true;
			right = true;
			up = true;
			down = true;
			
			upperLeft = true;
			upperRight = true;
			bottomLeft = true;
			bottomRight = true;
		}
		
		if(!left && !right && !up && !down) Renderer.draw(x * 16, y * 16, 5, 0);

		if(!left && !right && up && !down) Renderer.draw(x * 16, y * 16, 6, 0);
		if(!left && right && !up && !down) Renderer.draw(x * 16, y * 16, 6, 1);
		if(!left && !right && !up && down) Renderer.draw(x * 16, y * 16, 6, 2);
		if(left && !right && !up && !down) Renderer.draw(x * 16, y * 16, 6, 3);
		
		if(!left && !right && up && down) Renderer.draw(x * 16, y * 16, 7, 0);
		if(left && right && !up && !down) Renderer.draw(x * 16, y * 16, 7, 1);
		
		if(!left && right && !up && down) Renderer.draw(x * 16, y * 16, 5, 1);
		if(left && !right && !up && down) Renderer.draw(x * 16, y * 16, 5, 2);
		if(left && !right && up && !down) Renderer.draw(x * 16, y * 16, 5, 3);
		if(!left && right && up && !down) Renderer.draw(x * 16, y * 16, 5, 4);
		
		if(left && right && up && !down) Renderer.draw(x * 16, y * 16, 7, 6);
		if(left && right && !up && down) Renderer.draw(x * 16, y * 16, 7, 4);
		if(left && !right && up && down) Renderer.draw(x * 16, y * 16, 7, 5);
		if(!left && right && up && down) Renderer.draw(x * 16, y * 16, 7, 3);
		
		if(left && right && up && down) Renderer.draw(x * 16, y * 16, 7, 2);
		
		/* ----- */
		
		
		if(left && up && upperLeft) Renderer.drawRect(x * 16, y * 16, 5, 5, new Color(0, 19, 127));
		if(right && up && upperRight) Renderer.drawRect((x * 16) + 11, y * 16, 5, 5, new Color(0, 19, 127));
		if(left && down && bottomLeft) Renderer.drawRect(x * 16, (y * 16) + 11, 5, 5, new Color(0, 19, 127));
		if(right && down && bottomRight) Renderer.drawRect((x * 16) + 11, (y * 16) + 11, 5, 5, new Color(0, 19, 127));
	}
}
