package sprites;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import engine.GameLoop;
import engine.KeyboardInput;

/**
 * A basic enemy who chooses a player at random to follow
 * @author Alexander Aldridge
 */
public class Dylan extends Baddie {
	private String spriteLocation = "res/baddies/fireball.png";
	private static double score = 5.0;
	private Random generator = new Random();
	private int targetPlayer;
	

	public Dylan() {
		super(score);
		setDefaultImage(spriteLocation);
		targetPlayer = generator.nextInt(2) + 1;
	}

	public Dylan(int x, int y) {
		super(x, y, score);
		setDefaultImage(spriteLocation);
		targetPlayer = generator.nextInt(2) + 1;
	}

	@Override
	public Image getImage() {
		return this.defaultImage;
	}

	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game) {
		Point goal = game.getPlayerLocation(targetPlayer);
		if (goal.getX() > this.getXPos()) {
			this.currentX++;
		} else if (goal.getX() < this.getXPos()){
			this.currentX--;
		}
		
		if (goal.getY() > this.getYPos()) {
			this.currentY++;
		} else if (goal.getY() < this.getYPos()){
			this.currentY--;
		}
		
	}

}
