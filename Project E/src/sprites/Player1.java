package sprites;

import java.awt.Image;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;

public class Player1 extends MovingSprite {

	
	private static int starterX = 300;
	private static int starterY = 300;
	private int COOLDOWN_MAX = Constants.shotCooldown;
	private int cooldown;
	
	public Player1() {
		super(starterX, starterY, Constants.moveSpeed);
		setCollidable(true);
		setDefaultImage("res/character sprites/player one.png");
    this.cooldown = 0;
    this.setWrapAround(true);
	}
	
	public Player1(int starterX, int starterY) {
		super(starterX, starterY, Constants.moveSpeed);
		setCollidable(true);
		setDefaultImage("res/character sprites/snake.jpg");
		this.cooldown = 0;
		this.setWrapAround(true);
	}
	
	public void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game) {
		this.cooldown--;
		super.update(keyboard, actual_delta_time, game);
	}
	
	/**
	 * Use the keyboard inputs to determin the movement direction
	 * @param keyboard
	 */
	protected void setDirection(KeyboardInput keyboard, GameLoop game) {
		int xDirection = 0;
		int yDirection = 0;
		
		//Get Inputs
		if (keyboard.keyDown(Constants.playerOneLeft)){
			xDirection--;
		}
		if (keyboard.keyDown(Constants.playerOneDown)){
			yDirection++;
		}
		if (keyboard.keyDown(Constants.playerOneRight)){
			xDirection++;
		}
		if (keyboard.keyDown(Constants.playerOneUp)){
			yDirection--;
		}
		
		this.setXDirection(xDirection);
		this.setYDirection(yDirection);
	}
	
	public boolean isAlive() {
		return this.getDispose() == false;
	}
	
	public int getCooldown() {
		return this.cooldown;
	}
	
	public void resetCooldown() {
		this.cooldown = COOLDOWN_MAX;
	}
	
	public void loseLife() {
		//TODO Lose a life :(
		this.setDispose();
	}

	public Image getImage() {
		return defaultImage;
	}
	
	public String toString() {
		return "[Player 1]";
	}

}
