package sprites;

import java.awt.Image;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;
import misc.Direction;

public class Player1 extends MovingSprite {

	
	private static int starterX = 300;
	private static int starterY = 300;
	private int BASE_COOLDOWN_MAX = Constants.shotCooldown;
	private int cooldownMax = BASE_COOLDOWN_MAX;
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
	
	public void update(KeyboardInput keyboard, GameLoop game) {
		this.generateFireballs(keyboard, game);
		this.cooldown--;
		super.update(keyboard, game);
	}
	
	protected void generateFireballs(KeyboardInput keyboard, GameLoop game) {
		Direction direction = Direction.NULL;
		if (this.isAlive() && this.getCooldown() <= 0) {
			if (keyboard.keyDown(Constants.playerOneFireUp)) {
				direction = Direction.UP;
			} else if (keyboard.keyDownOnce(Constants.playerOneFireDown)) {
				direction = Direction.DOWN;
			} else if (keyboard.keyDownOnce(Constants.playerOneFireRight)) {
				direction = Direction.RIGHT;
			} else if (keyboard.keyDownOnce(Constants.playerOneFireLeft)) {
				direction = Direction.LEFT;
			}
			
			if (direction != Direction.NULL) {
				game.addSprite(new Fireball(this, direction));
			}
		}
	}
	
	/**
	 * Use the keyboard inputs to determine the movement direction
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
		this.cooldown = cooldownMax;
	}

	public Image getImage() {
		return defaultImage;
	}
	
	public String toString() {
		return "[Player 1]";
	}
	
	public void setCooldownMult(double newMult){
		cooldownMax = (int) Math.max((int) (BASE_COOLDOWN_MAX / newMult),25/Constants.fireballSpeed);
	}
	
	public double getCooldownMult(){
		return ((double)BASE_COOLDOWN_MAX)/((double)cooldownMax);
	}

}
