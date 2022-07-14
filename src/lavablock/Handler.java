package lavablock;
import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	LinkedList<GameObject> objects = new LinkedList<GameObject>();

	public void tick() {
		for (int i = 0; i < objects.size(); i++) {
			GameObject tempObject = objects.get(i);
			tempObject.tick();
			if (objects.get(i).getID().equals(ID.Enemy) && objects.get(i).getX() < 0) {
				removeObject(objects.get(i));
				// System.out.println("REMOVED");
			}
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < objects.size(); i++) {
			GameObject tempObject = objects.get(i);
			tempObject.render(g);
		}
	}
	/*
	public void clear() {
		for (int i = 0; i < objects.size(); i++) {
			GameObject tempObject = objects.get(i);
			if(tempObject.getID() == ID.Player) {
				objects.clear();
				if(Game.gameState != Game.STATE.End)
				addObject(new Player((int)tempObject.getX(), (int)tempObject.getY(), ID.Player, this));
			}
		}
	}
	*/
	public void addObject(GameObject object) {
		this.objects.add(object);
	}

	public void removeObject(GameObject object) {
		this.objects.remove(object);
	}
	
}
