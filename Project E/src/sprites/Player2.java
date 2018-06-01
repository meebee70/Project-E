package sprites;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;

public class Player2 extends Player1 {
	
	protected static int starterX = 364;
	protected static int starterY = 300;
	
	public Player2(){
		super(starterX, starterY);
		setDefaultImage("res/character sprites/snake.jpg");
	}
	
	protected void setDirection(KeyboardInput keyboard, GameLoop game) {
		int xDirection = 0;
		int yDirection = 0;
		
		//Get Inputs
		if (keyboard.keyDown(Constants.playerTwoLeft)){
			xDirection--;
		}
		if (keyboard.keyDown(Constants.playerTwoDown)){
			yDirection++;
		}
		if (keyboard.keyDown(Constants.playerTwoRight)){
			xDirection++;
		}
		if (keyboard.keyDown(Constants.playerTwoUp)){
			yDirection--;
		}
		
		this.setXDirection(xDirection);
		this.setYDirection(yDirection);
	}
	
	public String toString() {
		return "[Player 2]";
	}

}
