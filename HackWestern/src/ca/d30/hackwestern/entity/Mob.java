package ca.d30.hackwestern.entity;

import java.util.ArrayList;
import java.util.Random;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.gfx.Num;
import ca.d30.hackwestern.world.Tile;

public abstract class Mob extends Entity {
	public int dir = 0;
	public int health;
	
	protected int xKnockback, yKnockback;
	
	public ArrayList<Num> numbers = new ArrayList<Num>();
	
	public Mob(String name, int x, int y, int health) {
		super(name, x, y);
		this.health = health;
	}
	
	public boolean move(int dx, int dy) {
		if(dx < 0) dir = 3;
		if(dx > 0) dir = 1;
		if(dy < 0) dir = 2;
		if(dy > 0) dir = 0;
		
		if(x + dx < 0 || y + dy < 0) return false;
		if(x + dx >= Main.instance.world.mapWidth * 16 || y + dy >= Main.instance.world.mapHeight * 16) return false;
		
		int tX = (int) Math.floor((x + dx) / 16);
		int tY = (int) Math.floor((y + dy) / 16);
		
		if(Tile.tiles.get(Main.instance.world.getID(tX, tY)).solid) return false;
		
		x += dx;
		y += dy;
		
		return true;
	}
	
	public void updateKnockback() {
		if(damageTime > 0) damageTime--;
		
		if(health <= 0) die();
		
		if(xKnockback < 0) {
			move(-2, 0);
			xKnockback += 2;
			dir = 1;
		}
		
		if(xKnockback > 0) {
			move(2, 0);
			xKnockback -= 2;
			dir = 3;
		}
		
		if(yKnockback < 0) {
			move(0, -2);
			yKnockback += 2;
			dir = 0;
		}
		
		if(yKnockback > 0) {
			move(0, 2);
			yKnockback -= 2;
			dir = 2;
		}
	}
	
	public float distanceToMob(Mob m){
		int x1 = this.x - m.x;
		int y1 = this.y - m.y;
		
		return (float)Math.sqrt((x1 * x1) + (y1 * y1));
	}
	
	public float getAngleToMob(Mob m) {
		float side1 = (m.x - this.x);
		float side2 = (m.y - this.y);
		
		float angle = (float)Math.atan2(side2, side1);

		return angle;
	}
	
	public void die() {
		Main.instance.world.mobs.remove(this);
	}
	
	Random rand = new Random();
	public void takeDamage(int damage, Mob m) {
		health-=damage;
		System.out.println(m.toString());
		float angle = getAngleToMob(m);
		System.out.println(angle);
		angle -= Math.PI;
		
		if(angle < 0) angle += 2 * Math.PI;
		
		angle += ((rand.nextDouble()/2)-0.25) * Math.PI;
		
		double dx = Math.cos(angle);
		double dy = Math.sin(angle);
		
		dx*=6 + (rand.nextInt(3)-1);
		dy*=6 + (rand.nextInt(3)-1);
		
		xKnockback = ((int)dx)*2;
		yKnockback = ((int)dy)*2;
		
		damageTime = 10;
	}
	
	int damageTime = 0;
	
	public abstract void render();
	public abstract void tick();
}
