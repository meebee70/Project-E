package sprites;

import java.awt.Image;
import java.util.ArrayList;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;
import misc.Direction;

public class Fireball extends MovingSprite {
	
	private final Direction direction;
	private long age;
	
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
		this.age = 0;
	}
	
	public void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game){
		this.age++;
		
		if (this.getXPos() < 0 || this.getYPos() < 0 || this.getXPos() > game.SCREEN_WIDTH || this.getYPos() > game.SCREEN_HEIGHT) {
			this.setDispose();
			return;
		} else {
			super.update(keyboard, actual_delta_time, game);
		}
		
		//Does the fireball hit anything?
		if (age > 30) {
			ArrayList<Sprite> sprites = game.getSprites();
			for (Sprite object : sprites) {
				if (object != this) {
					if (object.checkCollisions(this)) {
						System.out.println(object.toString());
						if (object instanceof Player1) {
							((Player1) object).loseLife();
							this.setDispose();
							break;
						} else if (object instanceof Baddie) {
							((Baddie) object).loseLife();
							this.setDispose();
							break;
						}
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

}