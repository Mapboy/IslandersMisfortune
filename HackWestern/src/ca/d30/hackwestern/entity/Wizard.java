package ca.d30.hackwestern.entity;

import java.awt.Color;

import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.gfx.Font;
import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.item.Blade;
import ca.d30.hackwestern.item.Coin;
import ca.d30.hackwestern.item.Item;
import ca.d30.hackwestern.ui.Inventory;
import ca.d30.hackwestern.ui.UIBox;
import ca.d30.hackwestern.world.Tile;
import ca.d30.hackwestern.world.World;

public class Wizard extends Mob {
	public Wizard() {
		super("Wizard", 0, 0, 9000);
		
		World w = Main.instance.world;
		
		boolean solving = true;
		while (solving) {
			for (int y1 = 0; y1 < w.mapHeight; y1++) {
				for (int x1 = 0; x1 < w.mapWidth; x1++) {
					if (w.getID(x1, y1) == Tile.GRASS.id) {
						if (rand.nextInt(50) == 25) {
							x = x1 * 16 + 8;
							y = y1 * 16 + 8;

							System.out.println(x1 + ", " + y1);
							solving = false;
							break;
						}
					}
				}
				if(!solving) break;
			}
		}
		
		
		int tX = (int)Math.floor(x/16);
		int tY = (int)Math.floor(y/16);
		
		w.setID(tX - 1, tY, Tile.GRASS.id);
		w.setID(tX, tY - 1, Tile.GRASS.id);
		w.setID(tX + 1, tY, Tile.GRASS.id);
		w.setID(tX, tY + 1, Tile.GRASS.id);

		w.setID(tX - 1, tY - 1, Tile.GRASS.id);
		w.setID(tX - 1, tY + 1, Tile.GRASS.id);
		w.setID(tX + 1, tY - 1, Tile.GRASS.id);
		w.setID(tX + 1, tY + 1, Tile.GRASS.id);

		w.setID(tX-2, tY+2, Tile.STONE.id);
		w.setID(tX-1, tY+2, Tile.STONE.id);
		w.setID(tX, tY+2, Tile.GRASS.id);
		w.setID(tX+1, tY+2, Tile.STONE.id);
		w.setID(tX+2, tY+2, Tile.STONE.id);

		w.setID(tX-2, tY-2, Tile.STONE.id);
		w.setID(tX-1, tY-2, Tile.STONE.id);
		w.setID(tX, tY-2, Tile.STONE.id);
		w.setID(tX+1, tY-2, Tile.STONE.id);
		w.setID(tX+2, tY-2, Tile.STONE.id);

		w.setID(tX - 2, tY-1, Tile.STONE.id);
		w.setID(tX - 2, tY, Tile.STONE.id);
		w.setID(tX - 2, tY+1, Tile.STONE.id);

		w.setID(tX + 2, tY-1, Tile.STONE.id);
		w.setID(tX + 2, tY, Tile.STONE.id);
		w.setID(tX + 2, tY+1, Tile.STONE.id);
	}

	@Override
	public void render() {
		if (Inventory.visible)
			return;
		
		Renderer.draw(x-8, y-8, 8, 4, 12);
		
		if(trading) {
			Renderer.drawRect(-Renderer.xOffset, -Renderer.yOffset, Main.width, Main.height, new Color(0, 0, 0, 150));
			b.render();
			
			Renderer.drawSprite(Item.item.getSprite(2, 0), -Renderer.xOffset + Main.width/Renderer.scale/2 - 40, -Renderer.yOffset + Main.height/Renderer.scale/2 - 20, 4);
			Font.drawFont("Enchanted Blade", -Renderer.xOffset + Main.width/Renderer.scale/2 - 34, -Renderer.yOffset + Main.height/Renderer.scale/2 - 20);
			if(canAfford) {
				Font.drawFont("16 coins", -Renderer.xOffset + Main.width/Renderer.scale/2 - 34, -Renderer.yOffset + Main.height/Renderer.scale/2 - 14, 1, -150, -50, -150);
				Font.drawFont("> trade", -Renderer.xOffset + Main.width/Renderer.scale/2 - 34, -Renderer.yOffset + Main.height/Renderer.scale/2 + 14);
				
			} else {
				Font.drawFont("16 coins", -Renderer.xOffset + Main.width/Renderer.scale/2 - 34, -Renderer.yOffset + Main.height/Renderer.scale/2 - 14, 1, -50, -150, -150);
				Font.drawFont("> exit", -Renderer.xOffset + Main.width/Renderer.scale/2 - 34, -Renderer.yOffset + Main.height/Renderer.scale/2 + 14);
			}
		}
	}
	
	public void trigger(){
		if(!canAfford) {
			trading = false;
		} else {
			trading = false;
			Main.instance.player.inv.addItem(new Blade());
		}
	}

	@Override
	public void tick() {
		if(tradingCooldown > 0) tradingCooldown--;
	}
	
	public void takeDamage(int damage, Mob m) {
	}
	
	boolean canAfford = false;
	public static boolean trading = false;
	public int tradingCooldown = 0;
	UIBox b = new UIBox(Main.width/Renderer.scale/2 - 46, Main.height/Renderer.scale/2 - 24, 10, 6);
	public void startTrade() {
		trading = true;
		
		Player p = Main.instance.player;
		
		for(int i = 0; i < p.inv.items.size(); i++) {
			if(p.inv.items.get(i) != null) {
				if(p.inv.items.get(i).get(0).getClass().equals(Coin.class)) {
					if(p.inv.items.get(i).size() >= 16) canAfford = true;
				}
			}
		}
		
		tradingCooldown = 60;
	}
}

