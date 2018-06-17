package sprites;

import java.awt.Image;
import java.awt.Point;
import java.util.Random;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;
import misc.Direction;

public class Snowman extends Baddie {
	
	private static final int score = 175;
	
	private static int coolDown = Constants.snowmanTeleportCooldown;
	private static int snowballCooldown = Constants.snowballCooldown;
	private static Random generator = new Random();
	private int count = 0;
	

	/**
	 * An evil snowman who happens to be a Wizard
	 * @param x
	 * @param y
	 */
	public Snowman(int x, int y) {
		super(x, y, score, 0);
		this.setDefaultImage("res/baddies/Snowman.png");
		this.setCollidable(false);
		this.setXDirection(0);
		this.setYDirection(0);
	}

	@Override
	public void hitPlayer(Player1 player) {
		player.loseLife();
	}

	public void update(KeyboardInput keyboard, GameLoop game) {
		count++;
		if (count % coolDown == 1) {
			this.teleport(game);
		}
		
		if (count % snowballCooldown == 0) {
			Direction snowballDirection;
			Player1 target;
			if (game.getPlayerLocation(1).distance(this.getPos()) < game.getPlayerLocation(2).distance(this.getPos())) {
				target = game.getPlayer(2);
			} else {
				target = game.getPlayer(1);
			}
			
			if (target.getCenterX() > this.getCenterX()) {
				if (target.getCenterY() > this.getCenterY()) {
					snowballDirection = Direction.BOTTOM_RIGHT;
				} else {
					snowballDirection = Direction.TOP_RIGHT;
				}
			} else {
				if (target.getCenterY() > this.getCenterY()) {
					snowballDirection = Direction.BOTTOM_LEFT;
				} else {
					snowballDirection = Direction.TOP_LEFT;
				}
			}
			game.addSprite(new Snowball(this, snowballDirection));
		}
	}
	/**
	 * A recursive method to move the snowman to a
	 * new, random space on the screen
	 * @param gameLoop
	 */
	private void teleport(GameLoop game) {

		int newXLocation = generator.nextInt(game.SCREEN_WIDTH / this.getWidth()) * this.getWidth();
		int newYLocation = generator.nextInt(game.SCREEN_HEIGHT / this.getHeight()) * this.getHeight();
		this.setLocation(new Point(newXLocation, newYLocation));
		
		if (this.checkCollisions(game.getPlayer(1)) || this.checkCollisions(game.getPlayer(2))) {
			this.teleport(game);
			return;
		}
		
		for (Sprite wall : game.getBarriers()) {
			if (this.checkCollisions(wall)) {
				this.teleport(game);
				return;
			}
		}
	}

	@Override
	public Image getImage() {
		return defaultImage;
	}

	@Override
	protected void setDirection(KeyboardInput keyboard, GameLoop game) {
	}
	
	public String toString() {
		return "[Snowman]";
	}

}
