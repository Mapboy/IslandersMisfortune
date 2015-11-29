package ca.d30.hackwestern.item;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.entity.Entity;
import ca.d30.hackwestern.entity.Mob;
import ca.d30.hackwestern.gfx.Sprite;
import ca.d30.hackwestern.gfx.SpriteSheet;

public class Item extends Entity {
	public static SpriteSheet item = new SpriteSheet("/item.png", 8);
	public Sprite icon;
	
	public int damage;
	public String details;
	
	public boolean inInventory = false;
	
	public Item(String name, String details, int damage, Sprite icon) {
		super(name, 0, 0);
		this.icon = icon;
		
		this.details = details;
		this.damage = damage;
	}

	public void render() {
		
	}

	public void tick() {
		
	}
	
	public boolean equip() {
		return false;
	}
	
	public void use(Mob mob){
		
	}
	
	public float distanceToPlayer() {
		int x1 = x - Main.instance.player.x;
		int y1 = y - Main.instance.player.y;
		
		return (float)Math.sqrt((x1 * x1) + (y1 * y1));
	}
}
