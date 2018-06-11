package sprites;

import java.awt.Image;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;
import misc.Direction;

public class Snowball extends Baddie {
	
	private final Direction direction;
	private boolean armed = false;
	private final Snowman snowman;
	private final static int maxSize = 8;
	
	public Snowball(Snowman snowman, Direction direction) {
		super(snowman.getCenterX(), snowman.getCenterY(), 0, Constants.fireballSpeed * 0.5);
		this.setSize(25, 25);
		this.setWrapAround(true);
		this.setCollidable(false);
		this.setDefaultImage("res/Baddies/Snowball.png");
		this.snowman = snowman;
		this.direction = direction;
	}
	
	@Override
	public void update(KeyboardInput keyboard, GameLoop game){
		if (!armed) {
			armed = !(this.checkCollisions(snowman));
		} else {
			for (Sprite object : game.getSprites()) {
				if (object != this) {
					if (object instanceof Fireball) {
						object.setCollidable(true);
						this.setCollidable(true);
						if (object.checkCollisions(this)) {
							this.loseLife();
							object.loseLife();
							break;
						}
						object.setCollidable(false);
						this.setCollidable(false);
						
					} else if (object instanceof Snowball) {
						object.setCollidable(true);
						this.setCollidable(true);
						if (object.checkCollisions(this)) {
							object.setLives(object.getLives() + this.getLives());
							while (this.getLives() > 0) {
								this.loseLife();
							}
							break;
						}
						object.setCollidable(false);
						this.setCollidable(false);
					} else if (object instanceof Player1) {
						if (this.checkCollisions(object)) {
							this.hitPlayer((Player1) object);
						}
					}
				}
			}
		}
		
		super.update(keyboard, game);
	}

	@Override
	public Image getImage() {
		return defaultImage;
	}

	@Override
	public void hitPlayer(Player1 player) {
		player.loseLife();
		this.loseLife();
	}
	
	@Override
	public void loseLife() {
		this.setSize(25 * this.getLives(), 25 * this.getLives());
		super.loseLife();
	}
	
	@Override
	public void setLives(int newLives) {
		if (newLives > maxSize) {
			newLives = maxSize;
		}
		this.setSize(25 * newLives, 25 * newLives);
		super.setLives(newLives);
	}
	
	@Override
	protected void setDirection(KeyboardInput keyboard, GameLoop game) {
		switch (this.direction) {
		case BOTTOM_RIGHT:
			this.setXDirection(1);
			this.setYDirection(1);
			break;
		case BOTTOM_LEFT:
			this.setXDirection(-1);
			this.setYDirection(1);
			break;
		case TOP_RIGHT:
			this.setXDirection(1);
			this.setYDirection(-1);
			break;
		case TOP_LEFT:
			this.setXDirection(-1);
			this.setYDirection(-1);
			break;
		default:
			this.setXDirection(0);
			this.setYDirection(0);
			break;
		}
	}

	@Override
	public String toString() {
		return "[Snowball]";
	}
}
