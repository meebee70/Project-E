package sprites;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.GameLoop;
import engine.KeyboardInput;

public abstract class Sprite {

	protected Image defaultImage;
	private double currentX = 0;
	private double currentY = 0;
	private int IMAGE_WIDTH = 50; // sprite.get_width()
	private int IMAGE_HEIGHT = 50; //sprite.get_height()
	private boolean dispose = false;
	private boolean collidable = false;
	ArrayList<Sprite> sprites;
	KeyboardInput keyboard;
	
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
	
	public void setDefaultImage(String location){
		try{
			setDefaultImage(ImageIO.read(new File(location)));
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	public boolean getDispose() {
		return dispose;
	}

	public void setDispose() {
		this.dispose = true;
	}
		
	public Sprite() {
//		try {
//			this.image_default = ImageIO.read(new File("res/player-left.png"));
//		}
//		catch (IOException e) {
//		}
	}

	public abstract void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game);
	
	
	public int getHeight(){
		return this.IMAGE_HEIGHT;
	}
	public int getWidth(){
		return this.IMAGE_WIDTH;
	}
	
	public final int getXPos(){
		return (int) Math.round(currentX);
	}
	public final int getYPos(){
		return (int) Math.round(currentY);
	}
	
	public final int getCenterX() {
		return this.getXPos() + this.getWidth() / 2;
	}
	
	public final int getCenterY() {
		return this.getYPos() + this.getHeight() / 2;
	}
	
	/**
	 * this methods defines how an object should present itself
	 * @return the image it is to be displayed as
	 */
	public abstract Image getImage();
	
	protected void setSize(int width, int height) {
		this.IMAGE_WIDTH = width;
		this.IMAGE_HEIGHT = height;
	}
	
	public void setSprites(ArrayList<Sprite> staticSprites){
		this.sprites = staticSprites;
	}
	
	public void setKeyboard(KeyboardInput keyboard){
		this.keyboard = keyboard;
	}
	
	/**
	 * Move the sprite in a direction
	 * Checks whether or not it can collide with objects
	 * @param xDirection
	 * @param yDirection
	 * @param game
	 */
	protected void move(int xDistance, int yDistance, GameLoop game) {
		this.currentX += xDistance;
		if (this.isCollideable()) {
			for (Barrier wall : game.getBarriers()) {
				//Potentially uses lazy evaluation
				while (this.checkCollisions(wall) || wall.checkCollisions(this)) {
					this.currentX -= xDistance;
				}
			}
		}
		
		this.currentY += yDistance;
		if (this.isCollideable()) {
			for (Barrier wall : game.getBarriers()) {
				//Potentially uses lazy evaluation
				while (this.checkCollisions(wall) || wall.checkCollisions(this)) {
					this.currentY -= yDistance;
				}
			} 
		}
	}
	
	public boolean isCollideable() {
		return this.collidable;
	}
	
	protected void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}
	
	/**
	 * This method checks to see if this object is colliding with another object
	 * @param other The object to check the collision with
	 */
	public boolean checkCollisions(Sprite other) {
		int xPos, yPos, width, height;
		xPos = other.getXPos();
		yPos = other.getYPos();
		width = other.getWidth();
		height = other.getHeight();
		
		if (isWithin(xPos, yPos)) {
			return true;
		}
		if (isWithin(xPos + width, yPos)){
			return true;
		}
		if (isWithin(xPos, yPos + height)){
			return true;
		}
		if (isWithin(xPos + width, yPos + height)){
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * This method is used for determining if a point is within this object's frame perimeter
	 */
	private boolean isWithin(int x, int y) {
		boolean xWithin, yWithin;
		xWithin = this.getXPos() <= x && x <= this.getXPos() + this.getWidth();
		yWithin = this.getYPos() <= y && y <= this.getYPos() + this.getHeight();
		return xWithin && yWithin;
	}
	
}
