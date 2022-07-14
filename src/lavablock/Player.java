package lavablock;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Player extends GameObject {

	Random r = new Random();
	Handler handler;

	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

	public void tick() {
		x += velX;
		y += velY;

		if (y <= 0) {
			y = Game.HEIGHT;
		} else if (y >= Game.HEIGHT) {
			y = 0;
		}
		if (x <= 0) {
			x = 0;
		}
		if (x >= 600) {
			x = 600;
		}

		handler.addObject(new Trail(x, y, ID.Trail, Color.white, 32, 32, (float) 0.2, handler));

		collision();
	}

	private void collision() {
		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			if (tempObject.getID() == ID.Enemy) {
				if (getBounds().intersects(tempObject.getBounds())) {
					HUD.HEALTH -= 2;
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, 32, 32);
	}

}
