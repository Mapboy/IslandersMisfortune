package ca.d30.hackwestern;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

import ca.d30.hackwestern.entity.Golem;
import ca.d30.hackwestern.entity.Player;
import ca.d30.hackwestern.entity.Slime;
import ca.d30.hackwestern.entity.Wizard;
import ca.d30.hackwestern.gfx.Font;
import ca.d30.hackwestern.gfx.Renderer;
import ca.d30.hackwestern.gfx.SpriteSheet;
import ca.d30.hackwestern.ui.DeadUI;
import ca.d30.hackwestern.ui.HealthUI;
import ca.d30.hackwestern.ui.MainMenuUI;
import ca.d30.hackwestern.world.Tile;
import ca.d30.hackwestern.world.World;

public class Main extends Canvas implements Runnable {
	public static Main instance;
	private static final long serialVersionUID = 1L;
	public final static int width = 820, height = 560;
	public static String title = "Game.";

	private boolean running = false;

	public BufferedImage pixels = new BufferedImage(width, height,
			BufferedImage.TYPE_INT_ARGB);
	public SpriteSheet spritesheet;
	public Renderer renderer;
	public World world;
	public Input input;

	public Player player;
	public Wizard w;

	public static JFrame frame;

	public Main() {
		if (instance == null)
			instance = this;
	}

	public synchronized void start() {
		running = true;
		new Thread(this, "Game Thread").start();
	}

	public static void main(String[] args) {
		frame = new JFrame(title);

		Main main = new Main();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(main);
		frame.setResizable(false);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		main.start();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();

		init();

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;

			boolean shouldRender = true;
			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
				shouldRender = true;
			}

			/*
			 * try { Thread.sleep(1); } catch (InterruptedException e) {
			 * e.printStackTrace(); }
			 */

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				System.out.println(ticks + " ticks, " + frames + " fps");
				frames = 0;
				ticks = 0;
			}
		}
	}

	public boolean mainMenu = true;

	public void init() {
		renderer = new Renderer(5, width, height);

		input = new Input();

		spritesheet = new SpriteSheet("/sheet.png", 16);
		Font.fontSheet = new SpriteSheet("/font.png", 8);
	}
	
	public void setupWorld() {
		world = new World(128, 128);
		player = new Player();
		world.mobs.add(player);
		w = new Wizard();
		world.mobs.add(w);
	}

	Random rand = new Random();
	public static int numOfGolems = 0;
	public static int numOfSlimes = 0;

	public void tick() {
		if (mainMenu) {
			MainMenuUI.tick();
		} else {
			world.tick();

			while (numOfGolems <= 50) {
				boolean noSolve = true;
				for (int y = 0; y < world.mapHeight; y++) {
					for (int x = 0; x < world.mapWidth; x++) {
						if (world.mapData.get((y * world.mapWidth) + x) == Tile.GRASS.id) {
							if (rand.nextInt(800) == 5) {
								world.mobs.add(new Golem(x * 16, y * 16));
								numOfGolems++;
								noSolve = false;
								break;
							}
						}
					}
					if (!noSolve)
						break;
				}
			}

			while (numOfSlimes <= 150) {
				boolean noSolve = true;
				for (int y = 0; y < world.mapHeight; y++) {
					for (int x = 0; x < world.mapWidth; x++) {
						if (world.mapData.get((y * world.mapWidth) + x) == Tile.GRASS.id) {
							if (rand.nextInt(800) == 5) {
								world.mobs.add(new Slime(x * 16, y * 16));
								numOfSlimes++;
								noSolve = false;
								break;
							}
						}
					}
					if (!noSolve)
						break;
				}
			}

			Renderer.xOffset = -player.x + width / Renderer.scale / 2;
			Renderer.yOffset = -player.y + height / Renderer.scale / 2;

			if (Renderer.xOffset > 0)
				Renderer.xOffset = 0;
			if (Renderer.yOffset > 0)
				Renderer.yOffset = 0;

			if (Renderer.xOffset < -(world.mapWidth * 16) + width
					/ Renderer.scale)
				Renderer.xOffset = -(world.mapWidth * 16) + width
						/ Renderer.scale;
			if (Renderer.yOffset < -(world.mapHeight * 16) + height
					/ Renderer.scale)
				Renderer.yOffset = -(world.mapHeight * 16) + height
						/ Renderer.scale;

			if(player.health <= 0) DeadUI.tick();
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		pixels.getGraphics().fillRect(0, 0, width, height);

		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, width, height);
		if (mainMenu) {
			MainMenuUI.render();
		} else {
			world.render();
			HealthUI.draw();
			
			if(player.health <= 0) DeadUI.render();
		}
		g.drawImage(pixels, 0, 0, Color.WHITE, null);
		g.dispose();
		bs.show();
	}
}
