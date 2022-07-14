package lavablock;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Menu extends MouseAdapter {

	Game game;
	private Handler handler;
	private HUD hud;

	public Menu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.hud = hud;
		this.handler = handler;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if(game.gameState == Game.STATE.Menu) {
			if (mouseOver(mx, my, 240, 300, 150, 50)) {
				game.gameState = Game.STATE.Game;
				handler.addObject(new Player(0, 10, ID.Player, handler));
			}
			
			if (mouseOver(mx, my, 240, 245, 150, 50)) {
				game.gameState = Game.STATE.Help;
			}
			if(mouseOver(mx, my, 10, 10, 30, 10)) {
				if (AudioPlayer.getMusic("music").playing()) {
					AudioPlayer.getMusic("music").pause();
				}
				else {
					AudioPlayer.getMusic("music").resume();
				}
			}
		}
		
		if(game.gameState == Game.STATE.Help) {
			if (mouseOver(mx, my, 400, 40, 150, 64)) {
				game.gameState = Game.STATE.Menu;
				return;
			} 
		}
		
		if(game.gameState == Game.STATE.End) {
			if (mouseOver(mx, my, 400, 40, 150, 64)) {
				game.gameState = Game.STATE.Game;
				hud.setLevel(1);
				hud.setScore(0);
				handler.addObject(new Player(0, 10, ID.Player, handler));
			}
			if (mouseOver(mx, my, 400, 100, 150, 64)) {
				game.gameState = Game.STATE.Menu;
				hud.setLevel(1);
				hud.setScore(0);
				return;
			} 
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void tick() {

	}

	public void render(Graphics g) {
		if(game.gameState == Game.STATE.Menu) {
			g.drawString("mute", 10, 20);
			
			g.drawString("Made by Kevin Xiao", 20, 420);
			g.setColor(Color.red);
			Font fnt = new Font("arial", 1, 50);
			g.setFont(fnt);
			g.drawString("LAVABLOCK", 170, 150);

			g.setColor(Color.orange);
			g.drawRect(240, 300, 150, 64);
			g.drawString("PLAY", 250, 350);

			g.drawRect(240, 245, 150, 50);
			g.drawString("Help", 260, 285);
			
			
		}else if(game.gameState == Game.STATE.Help) {
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 20);
			g.setFont(fnt2);
			g.drawString("HELP", 50, 50);
			g.setFont(fnt3);
			g.drawString("Use arrow keys to move player", 50, 150);
			g.drawString("Red and orange blocks are enemies", 50, 175);
			g.drawString("Passing through enemies will decrease your health", 50, 200);
			g.drawString("The player dies when health is fully depleted", 50, 225);
			g.drawString("The goal is to stay alive as long as possible", 50, 250);
			g.drawString("The longer you stay alive, the higher your score and level", 50, 275);
			g.drawString("More enemies spawn when the level increases", 50, 300);
			
			g.setColor(Color.orange);
			g.drawRect(400, 40, 100, 50);
			g.drawString("Back", 410, 70);
		}else if(game.gameState == Game.STATE.End) {
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 20);
			g.setFont(fnt2);
			g.drawString("Game Over", 50, 50);
			g.setFont(fnt3);
			g.drawString("You lost with a score of: " + (hud.getScore()/100), 50, 150);
			
			g.setColor(Color.orange);
			g.drawRect(400, 40, 100, 50);
			g.drawString("Try Again", 405, 70);
			
			g.setColor(Color.orange);
			g.drawRect(400, 100, 100, 50);
			g.drawString("Back", 410, 130);
		}
		
		

	}
}
