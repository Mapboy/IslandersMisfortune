package ca.d30.hackwestern.item;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.entity.Mob;
import ca.d30.hackwestern.entity.Player;
import ca.d30.hackwestern.gfx.Num;
import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.ui.Inventory;
import ca.d30.hackwestern.world.Tile;
import ca.d30.hackwestern.world.World;

public class Log extends Item {
	public Log(int x, int y) {
		super("Log", "Good for housing.", 0, item.getSprite(0, 2));
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
				Main.instance.player.numbers.add(new Num("+1 Log", x, y, Main.instance.player, false));
				inInventory = true;
			}
		}
	}
	
	public boolean equip(){
		return true;
	}
	
	public void use(Mob m) {
		Player p = (Player)m;
		
		int tX = (int)Math.floor(p.x/16);
		int tY = (int)Math.floor(p.y/16);
		
		Tile t = null;
		World w = Main.instance.world;
		
		switch(p.dir) {
		case 0: 
			t = Tile.tiles.get(w.getID(tX, tY + 1));
			tY += 1;
			break;
		case 1:
			t = Tile.tiles.get(w.getID(tX + 1, tY));
			tX += 1;
			break;
		case 2:
			t = Tile.tiles.get(w.getID(tX, tY - 1));
			tY -= 1;
			break;
		case 3:
			t = Tile.tiles.get(w.getID(tX - 1, tY));
			tX -= 1;
			break;
		}
		
		if(t.id == Tile.GRASS.id) {
			if(p.inv.removeItem(this)) w.setID(tX, tY, Tile.LOG.id);
		}
	}
}