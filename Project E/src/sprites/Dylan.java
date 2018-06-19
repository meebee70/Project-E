package sprites;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;

/**
 * A basic enemy who chooses a player at random to follow
 * Dylans attempt to stop the player from moving, and thus can make it more challenging to dodge snowballs
 * @author Alexander Aldridge
 */
public class Dylan extends Baddie {
	private static final String spriteLocation = "res/baddies/Dylan V2.0.png";
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
	}

	public Dylan(int x, int y) {
		super(x, y, score, SPEED);
		this.setCollidable(true);
		setDefaultImage(spriteLocation);
		this.setSize(25, 25);
	}

	@Override
	public Image getImage() {
		return this.defaultImage;
	}

	public void update(KeyboardInput keyboard, GameLoop game) {
		if (game.getPlayer(targetPlayer).isAlive() == false) {
			if (game.getPlayer((targetPlayer + 1) % 2).isAlive()) {
				targetPlayer = (targetPlayer + 1) % 2;
			}
		}
		super.update(keyboard, game);
	}

	@Override
	protected void setDirection(KeyboardInput keyboard, GameLoop game) {
		Point goal = game.getPlayerLocationCentered(targetPlayer);
		if (goal.getX() > this.getXPos()) {
			this.setXDirection(1);
		} else if (goal.getX() < this.getXPos()){
			this.setXDirection(-1);
		} else {
			this.setXDirection(0);
		}

		if (goal.getY() > this.getYPos()) {
			this.setYDirection(1);
		} else if (goal.getY() < this.getYPos()){
			this.setYDirection(-1);
		} else {
			this.setYDirection(0);
		}
	}

	public void hitPlayer(Player1 player) {
		setXDirection(0);
		setYDirection(0);
	}

	@Override
	public String toString() {
		return "[Dylan]";
	}
}
