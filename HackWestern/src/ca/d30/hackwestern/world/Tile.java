package ca.d30.hackwestern.world;

import java.util.ArrayList;

import ca.d30.hackwestern.world.tiles.GrassTile;
import ca.d30.hackwestern.world.tiles.LogTile;
import ca.d30.hackwestern.world.tiles.RockTile;
import ca.d30.hackwestern.world.tiles.StoneTile;
import ca.d30.hackwestern.world.tiles.TreeTile;
import ca.d30.hackwestern.world.tiles.WaterTile;

public class Tile {
	public static ArrayList<Tile> tiles = new ArrayList<Tile>();
	
	public static Tile GRASS = new GrassTile();
	public static Tile ROCK = new RockTile();
	public static Tile WATER = new WaterTile();
	public static Tile TREE = new TreeTile();
	public static Tile LOG = new LogTile();
	public static Tile STONE = new StoneTile();
	
	public int id;
	public boolean solid;
	
	public Tile(boolean solid) {
		id = tiles.size();
		tiles.add(this);
		
		this.solid = solid;
	}
	
	public void render(int x, int y) {
		
	}
	
	public void tick() {
		
	}
}
