package ca.d30.hackwestern.gfx;

import ca.d30.hackwestern.entity.Mob;

public class Num {
	public float x, y;
	public float opacity = 1f;
	
	private String text;
	
	private Mob m;
	private boolean isHostile;
	
	public Num(String text, int x, int y, Mob m, boolean isHostile) {
		this.x = x;
		this.y = y;
		this.m = m;
		this.text = text;
		this.isHostile = isHostile;
	}
	
	public void render() {
		if(isHostile)Font.drawFont(text, (int)x, (int)y, opacity, -50, -150, -150);
		else Font.drawFont(text, (int)x, (int)y, opacity, 0, 0, 0);
	}
	
	public void tick() {
		y-= 0.5;
		opacity -= 0.02f;
		
		if(opacity <= 0) {
			m.numbers.remove(this);
		}
	}
}
