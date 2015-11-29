package ca.d30.hackwestern.world;

import java.util.ArrayList;
import java.util.Random;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.entity.Entity;
import ca.d30.hackwestern.entity.Mob;
import ca.d30.hackwestern.gfx.Renderer;

public class World {
	public int mapWidth, mapHeight;
	public ArrayList<Integer> mapData = new ArrayList<Integer>();
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Mob> mobs = new ArrayList<Mob>();
	
	Random random = new Random();
	public World(int mapWidth, int mapHeight) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		
		int[] map = WorldGen.createAndValidateTopMap(mapWidth, mapHeight);
		
		for(int i = 0; i < map.length; i++) {
			mapData.add(map[i]);
		}
	}
	
	public void render() {
		int yStart = (int)(-Renderer.yOffset/16) - 1;
		int xStart = (int)(-Renderer.xOffset/16) - 1;
		
		if(yStart < 0) yStart = 0;
		if(xStart < 0) xStart = 0;
		
		int xWidth = (int)(Main.width/16) + 2;
		int yHeight = (int)(Main.height/16) + 2;
		
		if(xStart + xWidth > mapWidth) xWidth = mapWidth-xStart;
		if(yStart + yHeight > mapHeight) yHeight = mapHeight-yStart;
		
		for(int y = yStart; y < yStart + yHeight; y++) {
			for(int x = xStart; x < xStart + xWidth; x++) {
				int id = mapData.get((y * mapWidth) + x);
				if(id < 0) continue;
				Tile.tiles.get(id).render(x, y);;
			}
		}
		
		for(int i = 0; i < mobs.size(); i++) {
			mobs.get(i).render();
		}
		
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).render();
		}
		
		
	}
	
	public void tick(){
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}
		
		for(int i = 0; i < mobs.size(); i++) {
			mobs.get(i).tick();
		}
	}
	
	public int getID(int x, int y) {
		if(x < 0 || y < 0) return -1;
		if(x >= mapWidth || y >= mapHeight) return -1;
		
		return mapData.get((y * mapWidth) + x);
	}
	
	public void setID(int x, int y, int id) {
		if(x < 0 || y < 0) return;
		if(x >= mapWidth || y >= mapHeight) return;
		
		mapData.set((y * mapWidth) + x, id);
	}
}
