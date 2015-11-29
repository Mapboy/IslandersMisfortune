package ca.d30.hackwestern.item;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.entity.Mob;
import ca.d30.hackwestern.gfx.Num;
import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.ui.Inventory;

public class Apple extends Item {
	public Apple(int x, int y) {
		super("Apple", "Good for the heart!", 0, item.getSprite(1, 2));
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
				Main.instance.player.numbers.add(new Num("+1 Apple", x, y, Main.instance.player, false));
				inInventory = true;
			}
		}
	}
	
	public boolean equip(){
		if(Main.instance.player.health < 16) Main.instance.player.health += 2;
		if(Main.instance.player.health > 16) Main.instance.player.health = 16;
		return false;
	}
	
	public void use(Mob m){
		
	}
}
