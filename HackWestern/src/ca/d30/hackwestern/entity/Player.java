package ca.d30.hackwestern.entity;

import java.util.Random;

import ca.d30.hackwestern.Input;
import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.ui.Inventory;
import ca.d30.hackwestern.world.Tile;
import ca.d30.hackwestern.world.World;

public class Player extends Mob {
	Random rand = new Random();

	public Inventory inv;

	public Player() {
		super("Player", 0, 0, 16);
		int x = 0, y = 0;

		World w = Main.instance.world;

		boolean solving = true;
		while (solving) {
			for (int y1 = 0; y1 < w.mapHeight; y1++) {
				for (int x1 = 0; x1 < w.mapWidth; x1++) {
					if (w.getID(x1, y1) == Tile.GRASS.id) {
						if (rand.nextInt(50) == 25) {
							w.setID(x1 - 1, y1 - 1, Tile.GRASS.id);
							w.setID(x1 + 1, y1 - 1, Tile.GRASS.id);
							w.setID(x1 - 1, y1 + 1, Tile.GRASS.id);
							w.setID(x1 + 1, y1 + 1, Tile.GRASS.id);

							w.setID(x1 - 1, y1, Tile.GRASS.id);
							w.setID(x1 + 1, y1, Tile.GRASS.id);
							w.setID(x1, y1 - 1, Tile.GRASS.id);
							w.setID(x1, y1 + 1, Tile.GRASS.id);

							x = x1 * 16 + 8;
							y = y1 * 16 + 8;

							solving = false;
							break;
						}
					}
				}
			}
		}

		this.x = x;
		this.y = y;

		inv = new Inventory();
	}

	boolean drawWater = false;
	boolean drawAnim = false;

	public void render() {
		if (damageTime > 0) {
			if (dir == 2) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 0, 5, 12, 16, 8, 255, 255, 255);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 0, 4, 12, 16, 16, 255, 255, 255);
				else
					Renderer.draw(x - 6, y - 6, 0, 5, 12, 16, 16, 255, 255, 255);
			} else if (dir == 1) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 0, 3, 12, 16, 8, 255, 255, 255);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 0, 2, 12, 16, 16, 255, 255, 255);
				else
					Renderer.draw(x - 6, y - 6, 0, 3, 12, 16, 16, 255, 255, 255);
			} else if (dir == 3) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 0, 7, 12, 16, 8, 255, 255, 255);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 0, 6, 12, 16, 16, 255, 255, 255);
				else
					Renderer.draw(x - 6, y - 6, 0, 7, 12, 16, 16, 255, 255, 255);
			} else if (dir == 0) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 0, 9, 12, 16, 8, 255, 255, 255);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 0, 8, 12, 16, 16, 255, 255, 255);
				else
					Renderer.draw(x - 6, y - 6, 0, 9, 12, 16, 16, 255, 255, 255);
			}
		} else {
			if (dir == 2) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 0, 5, 12, 16, 8);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 0, 4, 12, 16, 16);
				else
					Renderer.draw(x - 6, y - 6, 0, 5, 12, 16, 16);
			} else if (dir == 1) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 0, 3, 12, 16, 8);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 0, 2, 12, 16, 16);
				else
					Renderer.draw(x - 6, y - 6, 0, 3, 12, 16, 16);
			} else if (dir == 3) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 0, 7, 12, 16, 8);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 0, 6, 12, 16, 16);
				else
					Renderer.draw(x - 6, y - 6, 0, 7, 12, 16, 16);
			} else if (dir == 0) {
				if (drawWater)
					Renderer.draw(x - 6, y - 6, 0, 9, 12, 16, 8);
				else if (drawAnim)
					Renderer.draw(x - 6, y - 6, 0, 8, 12, 16, 16);
				else
					Renderer.draw(x - 6, y - 6, 0, 9, 12, 16, 16);
			}
		}

		if (attackImg && inv.equipped != null) {
			switch (dir) {
			case 0:
				Renderer.draw(x - 8, y - 6, 1, 6, 16);
				break;
			case 1:
				Renderer.draw(x - 6, y - 8, 2, 5, 16);
				break;
			case 2:
				Renderer.draw(x - 8, y - 10, 1, 5, 16);
				break;
			case 3:
				Renderer.draw(x - 10, y - 8, 2, 6, 16);
				break;
			}
		}

		for (int i = 0; i < numbers.size(); i++) {
			numbers.get(i).render();
		}

		inv.render();
	}

	int step = 0;
	int attackTimer = 0;
	boolean lastAttack = false;

	public void tick() {
		if (attackImg) {
			attackTimer--;
			if (attackTimer <= 0) {
				attackImg = false;
				attackTimer = 0;
			}
		}
		Input i = Main.instance.input;

		inv.tick();

		for (int q = 0; q < numbers.size(); q++) {
			numbers.get(q).tick();
		}

		if (Main.instance.input.attack.down && !lastAttack) {
			attack();
		}

		if (Inventory.visible || Wizard.trading)
			return;
		int lastStep = step;

		if (i.up.down) {
			if (move(0, -1))
				step++;
		}

		if (i.down.down) {
			if (move(0, 1))
				step++;
		}

		if (i.left.down) {
			if (move(-1, 0))
				step++;
		}

		if (i.right.down) {
			if (move(1, 0))
				step++;
		}

		if (step == lastStep) {
			step = 0;
		}

		updateKnockback();

		if (step >= 15) {
			drawAnim = !drawAnim;
			step = 0;
		}

		int tX = (int) Math.floor(x / 16);
		int tY = (int) Math.floor(y / 16);

		if (Main.instance.world.getID(tX, tY) == Tile.WATER.id) {
			drawWater = true;
		} else {
			drawWater = false;
		}

		lastAttack = Main.instance.input.attack.down;
	}

	boolean attackImg = false;
	public void attack() {
		attackImg = true;
		attackTimer = 7;
		
		if(Wizard.trading & Main.instance.w.tradingCooldown == 0) {
			Main.instance.w.trigger();
		} else if(distanceToMob(Main.instance.w) < 16) {
			Main.instance.w.startTrade();
			return;
		}
		
		if (inv.equipped != null)
			inv.equipped.use(this);
	}
}
