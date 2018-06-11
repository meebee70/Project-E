package sprites;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.GameLoop;
import engine.KeyboardInput;

public abstract class Sprite {

	protected Image defaultImage;
	private Point position;
	private int IMAGE_WIDTH = 50; // sprite.get_width()
	private int IMAGE_HEIGHT = 50; //sprite.get_height()
	//private boolean dispose = false;
	private boolean collidable = false;
	private boolean wrapAround = false;
	protected int lives = 1;


	ArrayList<Sprite> sprites;
	KeyboardInput keyboard;
	
	/**
	 * a constructor to be used for any object that instantiates at a specific spot
	 * @param x left boundary
	 * @param y upper boundary
	 */
	public Sprite(double x, double y){
		this.position = new Point();
		position.setLocation(x, y);
	}
	
	/**
	 * Instantiate an object at a point with an image
	 * @param x
	 * @param y
	 * @param img
	 */
	public Sprite(double x, double y, Image img){
		this.position = new Point();
		position.setLocation(x, y);
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
		return this.lives < 1;
	}

	public void loseLife(){
		this.lives--;
	}	

	public abstract void update(KeyboardInput keyboard, GameLoop game);
	
	public int getLives(){
		return lives;
	}
	
	public void setLives(int newLives){
		lives = newLives;
	}
	
	public int getHeight(){
		return this.IMAGE_HEIGHT;
	}
	public int getWidth(){
		return this.IMAGE_WIDTH;
	}
	
	public final int getXPos(){
		return (int) position.getX();
	}
	public final int getYPos(){
		return (int) position.getY();
	}
	
	public final int getCenterX() {
		return this.getXPos() + (this.getWidth() / 2);
	}
	
	public final int getCenterY() {
		return this.getYPos() + (this.getHeight() / 2);
	}
	
	protected void addX(double xDistance) {
		position.setLocation(position.getX() + xDistance, position.getY());
	}
	
	protected void addY(double yDistance) {
		position.setLocation(position.getX(), position.getY() + yDistance);
	}
	
	protected void setLocation(Point point) {
		this.position = point;
	}
	
	protected Point getPos() {
		return position;
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
		xWithin = this.getXPos() < x && x <= this.getXPos() + this.getWidth();
		yWithin = this.getYPos() < y && y <= this.getYPos() + this.getHeight();
		return xWithin && yWithin;
	}
	
	/**
	 * Does this sprite wrap around the universe?
	 * @return
	 */
	public boolean isWrapAround() {
		return wrapAround;
	}
	
	/**
	 * Set whether or not this sprite wraps around the universe
	 * @param wrapAround
	 */
	public void setWrapAround(boolean wrapAround) {
		this.wrapAround = wrapAround;
	}
	
	public abstract String toString();
}
