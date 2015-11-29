package ca.d30.hackwestern.item;

import java.util.Random;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.entity.Mob;
import ca.d30.hackwestern.entity.Player;
import ca.d30.hackwestern.gfx.Num;
import ca.d30.hackwestern.world.World;

public class Blade extends Item {
	
	public Blade() {
		super("Blade", "Best used against baddies.", 2, item.getSprite(2, 0));
		this.inInventory = true;
	}
	
	public boolean equip() {
		return true;
	}
	
	Random rand = new Random();
	public void use(Mob m) {
		Player p = (Player)m;
		World w = Main.instance.world;
		
		for(int i = 0; i < w.mobs.size(); i++) {
			if(p.distanceToMob(w.mobs.get(i)) < 16 && w.mobs.get(i) != p) {
				Mob mo = w.mobs.get(i);
				int dmg = rand.nextInt(7)+1;
				mo.takeDamage(dmg, m);
				p.numbers.add(new Num(""+dmg, mo.x-4, mo.y-4, p, false));
			}
		}
	}
}
