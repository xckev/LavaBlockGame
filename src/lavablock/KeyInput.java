package lavablock;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private boolean[] keyDown = new boolean[4];

	public KeyInput(Handler handler) {
		this.handler = handler;
		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		// System.out.println(key);

		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			if (tempObject.getID() == ID.Player) {
				if (key == KeyEvent.VK_UP) {
					tempObject.setVelY(-5);
					keyDown[0] = true;
				}
				if (key == KeyEvent.VK_DOWN) {
					tempObject.setVelY(5);
					keyDown[1] = true;
				}
				if (key == KeyEvent.VK_LEFT) {
					tempObject.setVelX(-5);
					keyDown[2] = true;
				}
				if (key == KeyEvent.VK_RIGHT) {
					tempObject.setVelX(5);
					keyDown[3] = true;
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		// System.out.println(key);

		for (int i = 0; i < handler.objects.size(); i++) {
			GameObject tempObject = handler.objects.get(i);
			if (tempObject.getID() == ID.Player) {
				if (key == KeyEvent.VK_UP) {
					// tempObject.setVelY(0);
					keyDown[0] = false;
				}
				if (key == KeyEvent.VK_DOWN) {
					// tempObject.setVelY(0);
					keyDown[1] = false;
				}
				if (key == KeyEvent.VK_LEFT) {
					// tempObject.setVelX(0);
					keyDown[2] = false;
				}
				if (key == KeyEvent.VK_RIGHT) {
					// tempObject.setVelX(0);
					keyDown[3] = false;
				}

				if (!keyDown[0] && !keyDown[1]) {
					tempObject.setVelY(0);
				}
				if (!keyDown[2] && !keyDown[3]) {
					tempObject.setVelX(0);
				}
			}
		}
	}

}
