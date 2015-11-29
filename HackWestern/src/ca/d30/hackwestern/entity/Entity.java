package ca.d30.hackwestern.entity;

public abstract class Entity {
	public int x, y;
	public String name;
	
	public Entity(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public abstract void render();
	public abstract void tick();
}
