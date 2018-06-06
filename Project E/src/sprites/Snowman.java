package sprites;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;

public class Snowman extends Baddie {
	
	private static final int score = 175;
	
	private int coolDown = Constants.snowmanTeleportCooldown;
	private int count = 0;
	

	/**
	 * An evil snowman who happens to be a Wizard
	 * @param x
	 * @param y
	 */
	public Snowman(int x, int y) {
		super(score, 0);
		this.setDefaultImage("res/baddies/Snowman.png");
		this.setCollidable(false);
		this.setXDirection(0);
		this.setYDirection(0);
	}

	@Override
	public void collideWithPlayer(Player1 player) {
		player.loseLife();
	}

	public void update(KeyboardInput keyboard, GameLoop game) {
		count++;
		if (count % coolDown == 0) {
			this.teleport(game);
		}
	}
	
	private void teleport(GameLoop game) {
		Random generator = new Random();
		int newXLocation = generator.nextInt(35) * 25;
		int newYLocation = generator.nextInt(35) * 25;
		this.setLocation(new Point(newXLocation, newYLocation));
		if (this.checkCollisions(game.getPlayer(1)) || this.checkCollisions(game.getPlayer(2))) {
			this.teleport(game);
		} else {
			System.out.println("Teleportation Activated");
		}
	}

	@Override
	public Image getImage() {
		return defaultImage;
	}

	@Override
	protected void setDirection(KeyboardInput keyboard, GameLoop game) {
	}

}
