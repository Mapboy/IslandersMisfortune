package ca.d30.hackwestern.entity;

import java.util.Random;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.gfx.Num;
import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.item.Coin;
import ca.d30.hackwestern.item.Stone;
import ca.d30.hackwestern.ui.Inventory;
import ca.d30.hackwestern.world.Tile;

public class Golem extends Mob {

	public boolean targetPlayer = false;

	public Golem(int x, int y) {
		super("Golem", x, y, 10);
	}

	boolean drawWater = false;
	boolean drawAnim = false;

	public void render() {
		if (Inventory.visible || Wizard.trading)
			return;
		
		if(damageTime > 0) {
			if (dir == 2) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 1, 10, 12, 16, 8, 255, 255, 255);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 1, 9, 12, 16, 16, 255, 255, 255);
				else
					Renderer.draw(x - 6, y - 6, 1, 10, 12, 16, 16, 255, 255, 255);
			} else if (dir == 1) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 1, 8, 12, 16, 8, 255, 255, 255);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 1, 7, 12, 16, 16, 255, 255, 255);
				else
					Renderer.draw(x - 6, y - 6, 1, 8, 12, 16, 16, 255, 255, 255);
			} else if (dir == 3) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 2, 8, 12, 16, 8, 255, 255, 255);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 2, 7, 12, 16, 16, 255, 255, 255);
				else
					Renderer.draw(x - 6, y - 6, 2, 8, 12, 16, 16, 255, 255, 255);
			} else if (dir == 0) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 2, 10, 12, 16, 8, 255, 255, 255);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 2, 9, 12, 16, 16, 255, 255, 255);
				else
					Renderer.draw(x - 6, y - 6, 2, 10, 12, 16, 16, 255, 255, 255);
			}
		} else {
			if (dir == 2) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 1, 10, 12, 16, 8);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 1, 9, 12, 16, 16);
				else
					Renderer.draw(x - 6, y - 6, 1, 10, 12, 16, 16);
			} else if (dir == 1) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 1, 8, 12, 16, 8);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 1, 7, 12, 16, 16);
				else
					Renderer.draw(x - 6, y - 6, 1, 8, 12, 16, 16);
			} else if (dir == 3) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 2, 8, 12, 16, 8);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 2, 7, 12, 16, 16);
				else
					Renderer.draw(x - 6, y - 6, 2, 8, 12, 16, 16);
			} else if (dir == 0) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 2, 10, 12, 16, 8);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 2, 9, 12, 16, 16);
				else
					Renderer.draw(x - 6, y - 6, 2, 10, 12, 16, 16);
			}
		}

		for (int i = 0; i < numbers.size(); i++) {
			numbers.get(i).render();
		}
	}
	
	public void die() {
		if(rand.nextInt(10) < 5) {
			Main.instance.world.entities.add(new Stone(x + (rand.nextInt(16)-8), y + (rand.nextInt(16)-8)));
		} else {
			Main.instance.world.entities.add(new Coin(x + (rand.nextInt(16)-8), y + (rand.nextInt(16)-8)));
		}
		
		Main.numOfGolems--;
		Main.instance.world.mobs.remove(this);
	}
	
	boolean isWalking = false;
	Random rand = new Random();
	int step = 0;
	int tick = 0;
	
	int cooldown = 0;
	public void tick() {
		if(Inventory.visible || Wizard.trading) return;
		
		for(int q = 0; q < numbers.size(); q++) {
			numbers.get(q).tick();
		}
		
		updateKnockback();
		
		if(cooldown > 0) cooldown--;
		if(distanceToMob(Main.instance.player) < 32) targetPlayer = true;
		else targetPlayer = false;
		
		if(tick >= 60) tick = 0;
		tick++;
		if(!targetPlayer){
		if(rand.nextInt(60) == 1) isWalking = !isWalking;
		if(rand.nextInt(20) == 1) {
			int dirChange = rand.nextInt(50);
			if(dirChange == 10) dir = 0;
			if(dirChange == 20) dir = 1;
			if(dirChange == 30) dir = 2;
			if(dirChange == 40) dir = 3;
		}
		
		if(isWalking && dir==2 && tick % 2 == 0)if(move(0, -1)) step+=2;
		
		if(isWalking && dir==0 && tick % 2 == 0)if(move(0, 1)) step+=2;
		
		if(isWalking && dir==3 && tick % 2 == 0)if(move(-1, 0)) step+=2;
		
		if(isWalking && dir==1 && tick % 2 == 0)if(move(1, 0)) step+=2;
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
			
			if(distanceToMob(p) > 8) {if(tick % 3 == 0) move(x, y);}
			if(distanceToMob(p) < 6) {if(tick % 3 == 0) move(-x, -y);}
			if(distanceToMob(p) < 14) {
				if(cooldown == 0) {
					int dmg = rand.nextInt(2)+1;
					p.takeDamage(dmg, this);
					this.numbers.add(new Num(""+dmg, p.x-4, p.y-4, this, true));
					cooldown = 90;
				}
			}
		}
		
		if(step >= 4) {
			drawAnim = !drawAnim;
			step = 0;
		}
		
		int tX = (int) Math.floor(x / 16);
		int tY = (int) Math.floor(y / 16);
		
		if(Main.instance.world.getID(tX, tY) == Tile.WATER.id) {
			drawWater = true;
		} else {
			drawWater = false;
		}
	}
}
