package lavablock;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -1442798787354930462L;

	public static final int WIDTH = 640;
	public static final int HEIGHT = WIDTH / 12 * 9;
	private Thread thread;
	private boolean running = false;

	private Random r;
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;

	public enum STATE {
		Menu, Help, Game, End
	};

	public static STATE gameState = STATE.Menu;

	public Game() {

		handler = new Handler();
		hud = new HUD();
		menu = new Menu(this, handler, hud);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);
		
		AudioPlayer.load();
		AudioPlayer.getMusic("music").loop();

		new Window(WIDTH, HEIGHT, "LavaBlock", this);
		
		spawner = new Spawn(handler, hud);
		r = new Random();

		if (gameState == STATE.Game) {
			handler.addObject(new Player(0, 10, ID.Player, handler));
		}

		// handler.addObject(new Enemy(WIDTH, r.nextInt(HEIGHT), ID.Enemy));

		// handler.addObject(new Player(100, 100, ID.Player));
		// handler.addObject(new Player(200, 200, ID.Player));
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: "+ frames);
				frames = 0;
				if (gameState == STATE.Game) {
					if (hud.getLevel() <= 6) {
						for (int i = 0; i < hud.getLevel(); i++) {
							handler.addObject(new Enemy(WIDTH + r.nextInt(50), r.nextInt(HEIGHT), ID.Enemy, handler));
						}
						handler.addObject(new Roamer(WIDTH + r.nextInt(30), r.nextInt(HEIGHT) - 50, ID.Enemy, handler));
						// handler.addObject(new Enemy(WIDTH, r.nextInt(HEIGHT), ID.Enemy, handler));
					} else if (hud.getLevel() > 6 && hud.getLevel() <= 12) {
						for (int i = 0; i < hud.getLevel(); i++) {
							handler.addObject(new Enemy(WIDTH + r.nextInt(50), r.nextInt(HEIGHT), ID.Enemy, handler));
						}
						handler.addObject(new Roamer(WIDTH + r.nextInt(30), r.nextInt(HEIGHT - 50), ID.Enemy, handler));
						// handler.addObject(new Roamer(WIDTH + r.nextInt(30), r.nextInt(HEIGHT-50),
						// ID.Enemy, handler));
						// handler.addObject(new Enemy(WIDTH, r.nextInt(HEIGHT), ID.Enemy, handler));
					} else {
						for (int i = 0; i < 12; i++) {
							handler.addObject(new Enemy(WIDTH + r.nextInt(50), r.nextInt(HEIGHT), ID.Enemy, handler));
						}
						handler.addObject(new Roamer(WIDTH + r.nextInt(30), r.nextInt(HEIGHT) - 50, ID.Enemy, handler));
						handler.addObject(new Roamer(WIDTH + r.nextInt(30), r.nextInt(HEIGHT) - 50, ID.Enemy, handler));
						// handler.addObject(new Roamer(WIDTH + r.nextInt(30), r.nextInt(HEIGHT)-50,
						// ID.Enemy, handler));
					}
				}

			}

		}
		stop();
	}

	private void tick() {
		handler.tick();
		if (gameState == STATE.Game) {
			hud.tick();
			spawner.tick();
			if(HUD.HEALTH <= 0) {
				HUD.HEALTH = 100;
				
				handler.objects.clear();
				gameState = STATE.End;
			}
		} else if (gameState == STATE.Menu || gameState == STATE.End) {
			menu.tick();
		}

		/*
		 * for (int i = 0; i < handler.objects.size(); i++) { if
		 * (handler.objects.get(i).getID().equals(ID.Enemy) &&
		 * handler.objects.get(i).getX() < 0) {
		 * handler.removeObject(handler.objects.get(i)); } }
		 */
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);

		if (gameState == STATE.Game) {
			hud.render(g);
		} else if (gameState == STATE.Menu) {
			g.setColor(Color.white);
			menu.render(g);
		}else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End) {
			g.setColor(Color.white);
			menu.render(g);
		}

		g.dispose();
		bs.show();
	}

	public static int clamp(int var, int min, int max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		return var;
	}

	public static void main(String[] args) {
		new Game();
	}
}
