package sprites;

import java.awt.Image;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;
import misc.Direction;

public class Fireball extends MovingSprite {
	
	private final Direction direction;
	
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
	}
	
	public void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game){
		if (this.getXPos() < 0 || this.getYPos() < 0 || this.getXPos() > game.SCREEN_WIDTH || this.getYPos() > game.SCREEN_HEIGHT) {
			this.setDispose();
			return;
		} else {
			super.update(keyboard, actual_delta_time, game);
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

}
