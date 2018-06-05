package sprites;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;

/**
 * A basic enemy who chooses a player at random to follow
 * @author Alexander Aldridge
 */
public class Dylan extends Baddie {
	private static String spriteLocation = "res/baddies/Dylan V2.0.png";
	private static final int score = 50;
	private static final double SPEED = Constants.dylanSpeed;
	private static final int xDefault = 350;
	private static final int yDefault = -30;
	private static Random generator = new Random();
	private int targetPlayer = generator.nextInt(2) + 1;
	

	public Dylan() {
		super(xDefault, yDefault, score, SPEED);
		this.setCollidable(true);
		setDefaultImage(spriteLocation);
		this.setSize(32, 32);
		this.setCollidable(true);
	}

	public Dylan(int x, int y) {
		super(x, y, score, SPEED);
		this.setCollidable(true);
		setDefaultImage(spriteLocation);
		this.setSize(25, 25);
		this.setCollidable(true);
	}

	@Override
	public Image getImage() {
		return this.defaultImage;
	}

	public void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game) {
		if (game.getPlayer(targetPlayer).isAlive() == false) {
			targetPlayer = (targetPlayer + 1) % 2;
		}
		super.update(keyboard, actual_delta_time, game);
	}
	
	@Override
	protected void setDirection(KeyboardInput keyboard, GameLoop game) {
		Point goal = game.getPlayerLocationCentered(targetPlayer);
		int xDirection = 0;
		int yDirection = 0;
		if (goal.getX() > this.getXPos()) {
			xDirection++;
		} else if (goal.getX() < this.getXPos()){
			xDirection--;
		}
		
		if (goal.getY() > this.getYPos()) {
			yDirection++;
		} else if (goal.getY() < this.getYPos()){
			yDirection--;
		}
		
		this.setXDirection(xDirection);
		this.setYDirection(yDirection);
	}

}
