package ca.d30.hackwestern;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Input implements KeyListener, MouseMotionListener {
	public class Key {
		public int presses, absorbs;
		public boolean down, clicked;
		
		public Key() {
			keys.add(this);
		}
		
		public void toggle(boolean pressed) {
			if (pressed != down) {
				down = pressed;
			}
			
			if (pressed) {
				presses++;
			}
		}
		
		public void tick() {
			if (absorbs < presses) {
			absorbs++;
			clicked = true;
			} else {
				clicked = false;
			}
		}
	}
	
	public ArrayList<Key> keys = new ArrayList<Key>();
	
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key attack = new Key();
	public Key inventory = new Key();
	
	public void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).down = false;
		}
	}
	
	public void tick() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).tick();
		}
	}
	
	public Input() {
		Main.instance.addKeyListener(this);
		Main.instance.addMouseMotionListener(this);
	}
	
	public void keyPressed(KeyEvent ke) {
		toggle(ke, true);
	}
	
	public void keyReleased(KeyEvent ke) {
		toggle(ke, false);
	}
	
	private void toggle(KeyEvent ke, boolean pressed) {
		if (ke.getKeyCode() == KeyEvent.VK_UP) up.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_DOWN) down.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_LEFT) left.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_RIGHT) right.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_W) up.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_S) down.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_A) left.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_D) right.toggle(pressed);
		
		if (ke.getKeyCode() == KeyEvent.VK_I) inventory.toggle(pressed);
		if (ke.getKeyCode() == KeyEvent.VK_E) inventory.toggle(pressed);
		
		if (ke.getKeyCode() == KeyEvent.VK_SPACE) attack.toggle(pressed);
	}
	
	public int xMouse, yMouse;
	
	public void keyTyped(KeyEvent ke) {
	
	}

	public void mouseDragged(MouseEvent arg0) {
		
	}

	public void mouseMoved(MouseEvent e) {
		xMouse = e.getX();
		yMouse = e.getY();
	}
}
