package sprites;

import java.awt.Image;

import engine.GameLoop;
import engine.KeyboardInput;
/**
 * a generic barrier object that extends sprite so as to be easier to use
 * @author Chris Kozbial
 *
 */
public class Barrier extends Sprite{
	
	/**
	 * creates a barrier at the specified position with a default image
	 * @param x
	 * @param y
	 */
	public Barrier(double x, double y){
		super(x,y);
		this.setCollidable(true);
		
		setDefaultImage("res/barriers/test barrier.png");
		setLives(10);
	}
	
	/**
	 * creates a barrier at the specified point, with a specified image
	 * @param x
	 * @param y
	 * @param img
	 */
	public Barrier(double x, double y, Image img){
		super(x,y,img);
		
		setLives(10);
		
	}

	/**
	 * basic barriers have no need to update their state, as they are static objects in the game
	 */
	public void update(KeyboardInput keyboard, GameLoop game) {
	}

	/**
	 * returns this barriers image
	 */
	public Image getImage() {
		return defaultImage;
	}


	
	
}
