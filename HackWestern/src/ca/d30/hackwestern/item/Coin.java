package ca.d30.hackwestern.item;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.gfx.Num;
import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.ui.Inventory;

public class Coin extends Item {
	public Coin(int x, int y) {
		super("Coin", "It'll be worth something someday ..", 0, item.getSprite(3, 2));
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
				Main.instance.player.numbers.add(new Num("+1 Coin", x, y, Main.instance.player, false));
				inInventory = true;
			}
		}
	}
}
