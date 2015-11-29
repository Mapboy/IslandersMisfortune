package ca.d30.hackwestern.item;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.entity.Mob;
import ca.d30.hackwestern.entity.Player;
import ca.d30.hackwestern.gfx.Num;
import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.ui.Inventory;
import ca.d30.hackwestern.world.Tile;
import ca.d30.hackwestern.world.World;

public class Ore extends Item {
	public Ore(int x, int y) {
		super("Ore", "Makes for a fine castle.", 0, item.getSprite(0, 3));
		this.x = x;
		this.y = y;
	}
	
	public void render() {
		if(Inventory.visible) return;
		if(!inInventory) {
			Renderer.drawSprite(icon, x, y, 4);
		}
	}
	
	public void tick() {
		if(Inventory.visible) return;
		if(inInventory) return;
		
		if(distanceToPlayer() < 8) {
			if(Main.instance.player.inv.addItem(this)) {
				Main.instance.player.numbers.add(new Num("+1 Ore", x, y, Main.instance.player, false));
				inInventory = true;
			}
		}
	}
	
	public boolean equip(){
		return false;
	}
}

