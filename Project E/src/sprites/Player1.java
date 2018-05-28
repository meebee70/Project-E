package sprites;

import java.awt.Image;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;

public class Player1 extends Sprite {

	
	private static double starterX = 300;
	private static double starterY = 300;
	
	public Player1(){
		super (starterX, starterY);
		
		setDefaultImage("res/character sprites/snake.jpg");
	}
	
	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game) {
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
		
		this.move(xDirection, yDirection, game);
	}
	
	protected void move(int xDirection, int yDirection, GameLoop game) {
		this.currentX += xDirection * Constants.moveSpeed;
		this.currentY += yDirection * Constants.moveSpeed;
		
		for (Barrier wall : game.getBarriers()) {
			while (this.checkCollisions(wall)) {
				this.currentX -= xDirection;
				this.currentY -= yDirection;
			}
		}
	}

	@Override
	public Image getImage() {
		return defaultImage;
	}

}
