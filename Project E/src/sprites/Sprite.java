package sprites;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import engine.KeyboardInput;

public abstract class Sprite {

	protected Image defaultImage;
	protected double currentX = 0;
	protected double currentY = 0;
	protected int IMAGE_WIDTH = 50; // sprite.get_width()
	protected int IMAGE_HEIGHT = 50; //sprite.get_height()
	private boolean dispose = false;
	
	/**
	 * a constructor to be used for any object that instantiates at a specific spot
	 * @param x left boundary
	 * @param y upper boundary
	 */
	public Sprite(double x, double y){
		currentX = x;
		currentY = y;
	}
	/**
	 * Instantiate an object at a point with an image
	 * @param x
	 * @param y
	 * @param img
	 */
	public Sprite(double x, double y, Image img){
		currentX = x;
		currentY = y;
		defaultImage = img;
	}
	
	public void setDefaultImage(Image img){
		defaultImage = img;
	}
	
	
	public boolean getDispose() {
		return dispose;
	}

	public void setDispose() {
		this.dispose = true;
	}
	ArrayList<Sprite> sprites;
	KeyboardInput keyboard;
		
	public Sprite() {
//		try {
//			this.image_default = ImageIO.read(new File("res/player-left.png"));
//		}
//		catch (IOException e) {
//		}
	}

	public abstract void update(KeyboardInput keyboard, long actual_delta_time);
	
	
	public double getHeight(){
		return IMAGE_HEIGHT;
	}
	public double getWidth(){
		return IMAGE_WIDTH;
	}
	
	public final double getXPos(){
		return currentX;
	}
	public final double getYPos(){
		return currentY;
	}
	
	/**
	 * this methods defines how an object should present itself
	 * @return the image it is to be displayed as
	 */
	public abstract Image getImage();
	
	public void setSprites(ArrayList<Sprite> staticSprites){
		this.sprites = staticSprites;
	}
	
	public void setKeyboard(KeyboardInput keyboard){
		this.keyboard = keyboard;
	}
	
	/**
	 * This method checks to see if this object is colliding with another object
	 * @param other The object to check the collision with
	 */
	public boolean checkCollisions(Sprite other) {
		return false;
	}
	
	/**
	 * This method is used for determining if a point is within this object's frame perimeter
	 */
	private boolean isWithin(int x, int y) {
		return false;
	}
	
}
