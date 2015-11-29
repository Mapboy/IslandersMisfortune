package ca.d30.hackwestern.item;

import java.util.Random;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.entity.Mob;
import ca.d30.hackwestern.entity.Player;
import ca.d30.hackwestern.gfx.Num;
import ca.d30.hackwestern.world.Tile;
import ca.d30.hackwestern.world.World;

public class Axe extends Item {
	
	public Axe() {
		super("Axe", "Best used against trees", 2, item.getSprite(0, 0));
		this.inInventory = true;
	}
	
	public boolean equip() {
		return true;
	}
	
	Random rand = new Random();
	public int curX = -1, curY = -1, health;
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
		
		if(t.id == Tile.TREE.id) {
			if(tX != curX || tY != curY) {
				curX = tX;
				curY = tY;
				health = 10;
			} else {
				int damage = rand.nextInt(2)+1;
				
				health -= damage;
				p.numbers.add(new Num(""+damage, tX*16 + 7, tY*16 + 7, p, false));
				
				if(health <= 0) {
					int numOfDrops = rand.nextInt(3)+1;
					for(int i = 0; i < numOfDrops; i++) {
						int x = rand.nextInt(16);
						int y = rand.nextInt(16);
						
						w.entities.add(new Log((tX*16) + x, (tY*16) + y));
					}
					numOfDrops = 0;
					
					if(rand.nextInt(4) == 1) numOfDrops = rand.nextInt(2);
					for(int i = 0; i < numOfDrops; i++) {
						int x = rand.nextInt(16);
						int y = rand.nextInt(16);
						
						w.entities.add(new Apple((tX*16) + x, (tY*16) + y));
					}
					
					w.setID(tX, tY, Tile.GRASS.id);
				//	p.inv.addItem(new Log());
				}
			}
		} else if(t.id == Tile.LOG.id) {
			if(tX != curX || tY != curY) {
				curX = tX;
				curY = tY;
				health = 10;
			} else {
				int damage = rand.nextInt(2)+1;
				
				health -= damage;
				p.numbers.add(new Num(""+damage, tX*16 + 7, tY*16 + 7, p, false));
				
				if(health <= 0) {
					int x = rand.nextInt(16);
					int y = rand.nextInt(16);
						
					w.entities.add(new Log((tX*16) + x, (tY*16) + y));
					
					w.setID(tX, tY, Tile.GRASS.id);
				//	p.inv.addItem(new Log());
				}
			}
		}
	}
}
