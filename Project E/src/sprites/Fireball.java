package sprites;

import java.awt.Image;
import java.util.ArrayList;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;
import misc.Direction;

public class Fireball extends MovingSprite {
	
	private final Direction direction;
	private boolean armed = false;
	
	/**
	 * This is a fireball that gets fired at baddies and/or other players
	 * @author Alexander Aldridge
	 * @param player that shot the fireball
	 * @param direction for the fireball to move
	 */
	public Fireball(Player1 player, Direction direction) {
		super(player.getCenterX(), player.getCenterY(), Constants.fireballSpeed);
		this.direction = direction;
		this.setDefaultImage("res/character sprites/fireball.png");
		this.setSize(20, 20);
		this.setWrapAround(true);
		player.resetCooldown();
	}
	
	public void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game){
		super.update(keyboard, actual_delta_time, game);
		
		if (!armed) {
			armed = !(game.getPlayer(1).checkCollisions(this) || game.getPlayer(2).checkCollisions(this));
		} else {
			ArrayList<Sprite> sprites = game.getSprites();
			for (Sprite object : sprites) {
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
					} else if (object.checkCollisions(this)) {
//						if (object instanceof Player1) {
//							((Player1) object).loseLife();
//						} else if (object instanceof Baddie) {
//							((Baddie) object).loseLife();
//						}
						object.loseLife();
						this.loseLife();
						break;
					}
				}
			}
		}
	}

	@Override
	protected void setDirection(KeyboardInput keyboard, GameLoop game) {
		switch (this.direction) {
		case UP:
			this.setXDirection(0);
			this.setYDirection(-1);
			break;
		case DOWN:
			this.setXDirection(0);
			this.setYDirection(1);
			break;
		case LEFT:
			this.setXDirection(-1);
			this.setYDirection(0);
			break;
		case RIGHT:
			this.setXDirection(1);
			this.setYDirection(0);
			break;
		default:
			this.setXDirection(0);
			this.setYDirection(0);
			break;
		}
	}

	@Override
	public Image getImage() {
		return defaultImage;
	}
	
	public String toString() {
		return "[FireBall]";
	}

}
