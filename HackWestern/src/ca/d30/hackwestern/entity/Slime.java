package ca.d30.hackwestern.entity;

import java.util.Random;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.gfx.Num;
import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.item.Coin;
import ca.d30.hackwestern.ui.Inventory;

public class Slime extends Mob {
	public int animPhase = 0;
	public boolean targetPlayer = false;
	public Slime(int x, int y) {
		super("Slime", x, y, 8);
	}

	public void render() {
		if (Inventory.visible || Wizard.trading)
			return;
		if (dir == 0) {
			if (animPhase == 0 || animPhase == 2)
				Renderer.draw(x - 6, y - 6, 8, 0, 12, 16, 16);
			else if(animPhase == 1)
				Renderer.draw(x - 6, y - 6, 9, 0, 12, 16, 16);
			else
				Renderer.draw(x - 6, y - 6, 10, 0, 12, 16, 16);
		} else if (dir == 3) {
			if (animPhase == 0 || animPhase == 2)
				Renderer.draw(x - 6, y - 6, 8, 1, 12, 16, 16);
			else if(animPhase == 1)
				Renderer.draw(x - 6, y - 6, 9, 1, 12, 16, 16);
			else
				Renderer.draw(x - 6, y - 6, 10, 1, 12, 16, 16);
		} else if (dir == 1) {
			if (animPhase == 0 || animPhase == 2)
				Renderer.draw(x - 6, y - 6, 8, 2, 12, 16, 16);
			else if(animPhase == 1)
				Renderer.draw(x - 6, y - 6, 9, 2, 12, 16, 16);
			else
				Renderer.draw(x - 6, y - 6, 10, 2, 12, 16, 16);
		} else if (dir == 2) {
			if (animPhase == 0 || animPhase == 2)
				Renderer.draw(x - 6, y - 6, 8, 3, 12, 16, 16);
			else if(animPhase == 1)
				Renderer.draw(x - 6, y - 6, 9, 3, 12, 16, 16);
			else
				Renderer.draw(x - 6, y - 6, 10, 3, 12, 16, 16);
		}
		
		for (int i = 0; i < numbers.size(); i++) {
			numbers.get(i).render();
		}
	}
	

	boolean isWalking = false;
	Random rand = new Random();
	int step = 0;
	int tick = 0;

	int cooldown = 0;
	public void tick() {
		if (Inventory.visible || Wizard.trading)
			return;
		updateKnockback();
		for (int i = 0; i < numbers.size(); i++) {
			numbers.get(i).tick();
		}
		if(cooldown > 0) cooldown--;
		if(distanceToMob(Main.instance.player) < 48) targetPlayer = true;
		else targetPlayer = false;
		
		if(tick >= 60) tick = 0;
		tick++;
		if(!targetPlayer) {
		if (rand.nextInt(60) == 1)
			isWalking = !isWalking;
		if (rand.nextInt(20) == 1) {
			int dirChange = rand.nextInt(50);
			if (dirChange == 10)
				dir = 0;
			if (dirChange == 20)
				dir = 1;
			if (dirChange == 30)
				dir = 2;
			if (dirChange == 40)
				dir = 3;
		}

		if (isWalking && dir == 2 && tick % 2 == 0)
			move(0, -1);

		if (isWalking && dir == 0 && tick % 2 == 0)
			move(0, 1);

		if (isWalking && dir == 3 && tick % 2 == 0)
			move(-1, 0);

		if (isWalking && dir == 1 && tick % 2 == 0)
			move(1, 0);
		} else {
			Player p = Main.instance.player;
			
			int x = 0;
			int y = 0;
			
			if(p.x < this.x) x = -1;
			else if(p.x > this.x) x = 1;
			
			if(p.y < this.y) y = -1;
			else if(p.y > this.y) y = 1;
			if(rand.nextInt(30) == 1) x *= -1;
			if(rand.nextInt(30) == 1) y *= -1;
			
			if(distanceToMob(p) > 8) {if(tick % 2 == 0) move(x, y);}
			if(distanceToMob(p) < 6) {if(tick % 2 == 0) move(-x, -y);}
			if(distanceToMob(p) < 14) {
				if(cooldown == 0) {
					int dmg = rand.nextInt(2)+1;
					p.takeDamage(dmg, this);
					this.numbers.add(new Num(""+dmg, p.x-4, p.y-4, this, true));
					cooldown = 90;
				}
			}
		}
		if(tick % 10 == 0) {
			animPhase++;
			if(animPhase > 3) animPhase = 0;
		}
	}
	
	public void die() {
		if(rand.nextInt(10) > 6) {
			Main.instance.world.entities.add(new Coin(x + (rand.nextInt(16)-8), y + (rand.nextInt(16)-8)));
		}
		
		Main.numOfSlimes--;
		Main.instance.world.mobs.remove(this);
	}
}
