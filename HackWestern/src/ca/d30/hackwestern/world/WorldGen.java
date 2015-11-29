package ca.d30.hackwestern.world;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class WorldGen {
	private static final Random random = new Random();
	public double[] values;
	private int w, h;

	public WorldGen(int w, int h, int featureSize) {
		this.w = w;
		this.h = h;
		values = new double[w * h];
		for (int y = 0; y < w; y += featureSize) {
			for (int x = 0; x < w; x += featureSize) {
				setSample(x, y, random.nextFloat() * 2 - 1);
			}
		}
		int stepSize = featureSize;
		double scale = 1.0 / w;
		double scaleMod = 1;
		do {
			int halfStep = stepSize / 2;
			for (int y = 0; y < w; y += stepSize) {
				for (int x = 0; x < w; x += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + stepSize, y + stepSize);
					double e = (a + b + c + d) / 4.0
							+ (random.nextFloat() * 2 - 1) * stepSize * scale;
					setSample(x + halfStep, y + halfStep, e);
				}
			}
			for (int y = 0; y < w; y += stepSize) {
				for (int x = 0; x < w; x += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + halfStep, y + halfStep);
					double e = sample(x + halfStep, y - halfStep);
					double f = sample(x - halfStep, y + halfStep);
					double H = (a + b + d + e) / 4.0
							+ (random.nextFloat() * 2 - 1) * stepSize * scale
							* 0.5;
					double g = (a + c + d + f) / 4.0
							+ (random.nextFloat() * 2 - 1) * stepSize * scale
							* 0.5;
					setSample(x + halfStep, y, H);
					setSample(x, y + halfStep, g);
				}
			}
			stepSize /= 2;
			scale *= (scaleMod + 0.8);
			scaleMod *= 0.3;
		} while (stepSize > 1);
	}

	private double sample(int x, int y) {
		return values[(x & (w - 1)) + (y & (h - 1)) * w];
	}

	private void setSample(int x, int y, double value) {
		values[(x & (w - 1)) + (y & (h - 1)) * w] = value;
	}

	public static int[] createAndValidateTopMap(int w, int h) {
		do {
			int[] result = createTopMap(w, h);
			int[] count = new int[256];
			for (int i = 0; i < w * h; i++) {
				count[result[i] & 0xff]++;
			}
			if (count[Tile.ROCK.id] < 100)
				continue;
			if (count[Tile.GRASS.id] < 100)
				continue;
			return result;
		} while (true);
	}


	private static int[] createTopMap(int w, int h) {
		WorldGen mnoise1 = new WorldGen(w, h, 16);
		WorldGen mnoise2 = new WorldGen(w, h, 16);
		WorldGen mnoise3 = new WorldGen(w, h, 16);
		WorldGen noise1 = new WorldGen(w, h, 32);
		WorldGen noise2 = new WorldGen(w, h, 32);
		int[] map = new int[w * h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int i = x + y * w;
				double val = Math.abs(noise1.values[i] - noise2.values[i]) * 3 - 2;
				double mval = Math.abs(mnoise1.values[i] - mnoise2.values[i]);
				mval = Math.abs(mval - mnoise3.values[i]) * 3 - 2;
				double xd = x / (w - 1.0) * 2 - 1;
				double yd = y / (h - 1.0) * 2 - 1;
				if (xd < 0)
					xd = -xd;
				if (yd < 0)
					yd = -yd;
				double dist = xd >= yd ? xd : yd;
				dist = dist * dist * dist;
				dist = dist * dist * dist;
				val = val + 1 - dist * 20;
				if (val < -0.3) {
					map[i] = Tile.WATER.id;
				} else if (val > 0 && mval < -1) {
					map[i] = Tile.ROCK.id;
				} else {
					map[i] = Tile.GRASS.id;
				}
			}
		}
		for (int i = 0; i < w * h / 2800; i++) {
			int xs = random.nextInt(w);
			int ys = random.nextInt(h);
			for (int k = 0; k < 10; k++) {
				int x = xs + random.nextInt(21) - 10;
				int y = ys + random.nextInt(21) - 10;
				for (int j = 0; j < 100; j++) {
					int xo = x + random.nextInt(5) - random.nextInt(5);
					int yo = y + random.nextInt(5) - random.nextInt(5);
					for (int yy = yo - 1; yy <= yo + 1; yy++)
						for (int xx = xo - 1; xx <= xo + 1; xx++)
							if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
								if (map[xx + yy * w] == Tile.GRASS.id) {
								//	map[xx + yy * w] = Tile.sand.id;
								}
							}
				}
			}
		}
		
		for (int i = 0; i < w * h / 400; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			for (int j = 0; j < 200; j++) {
				int xx = x + random.nextInt(15) - random.nextInt(15);
				int yy = y + random.nextInt(15) - random.nextInt(15);
				if (xx >= 0 && yy >= 0 && xx < w && yy < h) {
					if (map[xx + yy * w] == Tile.GRASS.id) {
						map[xx + yy * w] = Tile.TREE.id;
					}
				}
			}
		}
		
		return map;
	}

	public static void main(String[] args) {
		while (true) {
			int w = 256;
			int h = 256;
			int[] map = WorldGen.createAndValidateTopMap(w, h);
			BufferedImage img = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
			int[] pixels = new int[w * h];
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int i = x + y * w;
					if (map[i] == Tile.WATER.id)
						pixels[i] = 0x000080;
					if (map[i] == Tile.GRASS.id)
						pixels[i] = 0x208020;
					if (map[i] == Tile.ROCK.id)
						pixels[i] = 0xa0a0a0;
					if(map[i] == -2)
						pixels[i] = 0x107010;
				}
			}
			img.setRGB(0, 0, w, h, pixels, 0, w);
			JOptionPane.showMessageDialog(
					null,
					null,
					"Another",
					JOptionPane.YES_NO_OPTION,
					new ImageIcon(img.getScaledInstance(w * 4, h * 4,
							Image.SCALE_AREA_AVERAGING)));
		}
	}
}
