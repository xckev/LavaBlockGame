package lavablock;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Roamer extends GameObject {
	private int width;
	private int height;
	private Handler handler;
	private int swap;

	public Roamer(int x, int y, ID id, Handler handler) {
		super(x, y, id);

		this.handler = handler;

		velX = -3;

		if (Math.random() < 0.5) {
			velY = -3;
		} else {
			velY = 3;
		}
		swap = 0;

		width = 32;
		height = 32;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void tick() {
		if (swap >= 30) {
			velY *= -1;
			swap = 0;
		}
		x += velX;
		y += velY;
		swap++;
		handler.addObject(new Trail(x, y, ID.Trail, Color.orange, width, height, (float) 0.2, handler));

	}

	public void render(Graphics g) {
		// Graphics2D g2d = (Graphics2D) g;

		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}
}
