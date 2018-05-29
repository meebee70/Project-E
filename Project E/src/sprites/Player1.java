package sprites;

import java.awt.Image;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;

public class Player1 extends MovingSprite {

	
	protected static int starterX = 300;
	protected static int starterY = 300;
	
	public Player1() {
		super(starterX, starterY, Constants.moveSpeed);
		setCollidable(true);
		setDefaultImage("res/character sprites/snake.jpg");
	}
	
	public Player1(int starterX, int starterY) {
		super(starterX, starterY, Constants.moveSpeed);
		setCollidable(true);
		setDefaultImage("res/character sprites/snake.jpg");
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

	public Image getImage() {
		return defaultImage;
	}

}
