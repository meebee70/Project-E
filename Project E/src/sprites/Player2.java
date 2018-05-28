package sprites;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;

public class Player2 extends Player1 {
	
	public Player2(){
		super();
		setDefaultImage("res/character sprites/snake.jpg");
	}
	
	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game) {
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
		
		this.move(xDirection, yDirection, game);
	}

}
