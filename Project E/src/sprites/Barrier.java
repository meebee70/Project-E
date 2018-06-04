package sprites;

import java.awt.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
		
		try {
			setDefaultImage(ImageIO.read(new File("res/barriers/test barrier.png")));
		} catch (IOException e) {e.printStackTrace();}
	}
	
	/**
	 * creates a barrier at the specified point, with a specified image
	 * @param x
	 * @param y
	 * @param img
	 */
	public Barrier(double x, double y, Image img){
		super(x,y,img);
		
	}

	/**
	 * basic barriers have no need to update their state, as they are static objects in the game
	 */
	public void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game) {
		
	}

	/**
	 * returns this barriers image
	 */
	public Image getImage() {
		return defaultImage;
	}


	
	
}
