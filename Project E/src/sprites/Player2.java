package sprites;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;
import misc.Direction;

public class Player2 extends Player1 {
	
	protected static int starterX = 364;
	protected static int starterY = 300;
	
	public Player2(){
		super(starterX, starterY);
		setDefaultImage("res/character sprites/player two.png");
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
	
	protected void generateFireballs(KeyboardInput keyboard, GameLoop game) {
		Direction direction = Direction.NULL;
		if (this.isAlive() && this.getCooldown() <= 0) {
			if (keyboard.keyDown(Constants.playerTwoFireUp)) {
				direction = Direction.UP;
			} else if (keyboard.keyDownOnce(Constants.playerTwoFireDown)) {
				direction = Direction.DOWN;
			} else if (keyboard.keyDownOnce(Constants.playerTwoFireRight)) {
				direction = Direction.RIGHT;
			} else if (keyboard.keyDownOnce(Constants.playerTwoFireLeft)) {
				direction = Direction.LEFT;
			}
			
			if (direction != Direction.NULL) {
				game.addSprite(new Fireball(this, direction));
			}
		}
	}
	
	public String toString() {
		return "[Player 2]";
	}

}
