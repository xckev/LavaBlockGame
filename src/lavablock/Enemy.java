package lavablock;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends GameObject {

	private int width;
	private int height;
	private Handler handler;

	public Enemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);

		this.handler = handler;

		velX = -5;

		Random r = new Random();

		width = r.nextInt(64);
		height = r.nextInt(64);
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
		x += velX;
		y += velY;

		handler.addObject(new Trail(x, y, ID.Trail, Color.red, width, height, (float) 0.2, handler));

	}

	public void render(Graphics g) {
		// Graphics2D g2d = (Graphics2D) g;

		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}
}
