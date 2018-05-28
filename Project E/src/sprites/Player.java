package sprites;

import java.awt.Image;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;

public class Player extends Sprite {

	
	private static double starterX = 300;
	private static double starterY = 300;
	private GameLoop game;
	
	public Player(GameLoop game){
		super (starterX, starterY);
		this.game = game;
		
		setDefaultImage("res/character sprites/snake.jpg");
	}
	
	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game) {
		double xDirection = 0.0;
		double yDirection = 0.0;
		
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
