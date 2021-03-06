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
	
	public void update(KeyboardInput keyboard, GameLoop game){
		super.update(keyboard, game);
		
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
		direction.setMovingSpriteDirection(this);
	}

	@Override
	public Image getImage() {
		return defaultImage;
	}
	
	public String toString() {
		return "[Fire Ball]";
	}

}
