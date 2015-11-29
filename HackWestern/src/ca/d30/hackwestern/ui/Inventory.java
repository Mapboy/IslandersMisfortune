package ca.d30.hackwestern.ui;

import java.awt.Color;
import java.util.ArrayList;

import ca.d30.hackwestern.Input;
import ca.d30.hackwestern.Main;
import ca.d30.hackwestern.gfx.Font;
import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.item.Axe;
import ca.d30.hackwestern.item.Item;
import ca.d30.hackwestern.item.Pickaxe;
import ca.d30.hackwestern.item.Sword;

public class Inventory {
	public ArrayList<ArrayList<Item>> items = new ArrayList<ArrayList<Item>>();
	public final int width = 6, height = 4;
	
	public int xSelect = 0, ySelect = 0;
	
	public static boolean visible = false;
	private UIBox box, details;
	
	public Item equipped;
	
	public Inventory() {
		box = new UIBox(Main.width/Renderer.scale/2 - 46, Main.height/Renderer.scale/2 - 24, 8, 6);
		details = new UIBox(Main.width/Renderer.scale/2 + 22, Main.height/Renderer.scale/2 - 24, 3, 6);
		
		for(int i = 0; i < width * height; i++) items.add(null);

		addItem(new Axe());
		addItem(new Sword());
		addItem(new Pickaxe());
	}
	
	public void render() {
		if(visible) {
			Renderer.drawRect(-Renderer.xOffset, -Renderer.yOffset, Main.width, Main.height, new Color(0, 0, 0, 150));
			box.render();
			details.render();
			Font.drawFont("inventory", -Renderer.xOffset + Main.width/Renderer.scale/2 - 44, -Renderer.yOffset + Main.height/Renderer.scale/2 - 28);
			Font.drawFont("info", -Renderer.xOffset + Main.width/Renderer.scale/2 +24, -Renderer.yOffset + Main.height/Renderer.scale/2 - 28);
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					Renderer.drawRect(-Renderer.xOffset + Main.width/Renderer.scale/2 - 43 + x*10, -Renderer.yOffset + Main.height/Renderer.scale/2 - 19 + y*10, 8, 8, new Color(255, 255, 255, 20));
					if(items.get((y * width) + x) != null) Renderer.drawSprite(items.get((y * width) + x).get(0).icon, -Renderer.xOffset + Main.width/Renderer.scale/2 - 42 + x*10, -Renderer.yOffset + Main.height/Renderer.scale/2 - 18 + y*10, 6);
					if(x == xSelect && y == ySelect) {
						Renderer.draw(-Renderer.xOffset + Main.width/Renderer.scale/2 - 43 + x*10, -Renderer.yOffset + Main.height/Renderer.scale/2 - 19 + y*10, 6, 4, 8);
					}
				}
			}
			
			if(items.get((ySelect * width) + xSelect) != null) {
				Item i = items.get((ySelect * width) + xSelect).get(0);
				Font.drawFont(i.name, -Renderer.xOffset + Main.width/Renderer.scale/2 + 22 + (12 - ((i.name.length() * 4)/2)), -Renderer.yOffset + Main.height/Renderer.scale/2 - 20);
				
				Renderer.drawSprite(i.icon, -Renderer.xOffset + Main.width/Renderer.scale/2 + 30, -Renderer.yOffset + Main.height/Renderer.scale/2 - 13, 8);
				if(items.get((ySelect * width) + xSelect).size() > 1) Font.drawFont("x"+items.get((ySelect * width) + xSelect).size(), -Renderer.xOffset + Main.width/Renderer.scale/2 + 22 + (12 - ((("x"+items.get((ySelect * width) + xSelect).size()).length() * 4)/2)), -Renderer.yOffset + Main.height/Renderer.scale/2);
				if(i.damage > 0) Font.drawFont(i.damage + " DMG", -Renderer.xOffset + Main.width/Renderer.scale/2 + 22 + (12 - (((i.damage + " DMG").length() * 4)/2)), -Renderer.yOffset + Main.height/Renderer.scale/2 + 16);
			}
		}
	}
	
	private boolean lastPressed = false;
	
	private boolean lastKey = false;
	public void tick() {
		if(Main.instance.input.inventory.down != lastKey) {
			if(Main.instance.input.inventory.down) {
				visible = !visible;
				if(visible) equipped = null;
			}
		}
		
		Input i = Main.instance.input;
		if(visible) {
			if(i.attack.down && items.get((ySelect * width) + xSelect) != null) {
				Item it = items.get((ySelect * width) + xSelect).get(0);
				if(it.equip()) { 
					visible = false;
					equipped = it;
					i.attack.down = false;
				} else {
					items.get((ySelect * width) + xSelect).remove(it);
					if(items.get((ySelect * width) + xSelect).size() <= 0) items.set((ySelect * width) + xSelect, null);
					i.attack.down = false;
				}
			}
			
			if(i.up.down && !lastPressed && ySelect > 0) {
				ySelect--;
				lastPressed = true;
			}

			if(i.down.down && !lastPressed && ySelect < height-1) {
				ySelect++;
				lastPressed = true;
			}

			if(i.left.down && !lastPressed && xSelect > 0) {
				xSelect--;
				lastPressed = true;
			}

			if(i.right.down && !lastPressed && xSelect < width-1) {
				xSelect++;
				lastPressed = true;
			}
			
			if(!i.right.down && !i.left.down && !i.up.down && !i.down.down) lastPressed = false;
		}
			
		lastKey = Main.instance.input.inventory.down;
	}
	
	public boolean addItem(Item i) {
		int nullLoc = -1;
		
		for(int a = 0; a < width * height; a++) {
			if(items.get(a) != null) {
				if(items.get(a).get(0).getClass().equals(i.getClass())) {
					items.get(a).add(i);
					return true;
				}
			} else {
				if(nullLoc == -1) nullLoc = a;
			}
		}
		
		if(nullLoc != -1) {
			ArrayList<Item> item = new ArrayList<Item>();
			item.add(i);
			
			items.set(nullLoc, item);
		}
		
		return false;
	}
	
	public boolean removeItem(Item i) {
		for(int a = 0; a < width * height; a++) {
			if(items.get(a) != null) {
				if(items.get(a).get(0).getClass().equals(i.getClass())) {
					items.get(a).remove(i);
					if(items.get(a).size() == 0) {
						items.set(items.indexOf(items.get(a)), null);
						Main.instance.player.inv.equipped = null;
					}
					return true;
				}
			}
		}
		
		return false;
	}
}
