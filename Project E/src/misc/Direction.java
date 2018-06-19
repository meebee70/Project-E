package misc;

import sprites.MovingSprite;

public enum Direction {
	UP,
	DOWN,
	LEFT,
	RIGHT,
	BOTTOM_RIGHT,
	BOTTOM_LEFT,
	TOP_RIGHT,
	TOP_LEFT,
	NULL;
	
	public void setMovingSpriteDirection(MovingSprite sprite) {
		switch (this) {
		case UP:
			sprite.setXDirection(0);
			sprite.setYDirection(-1);
			break;
		case DOWN:
			sprite.setXDirection(0);
			sprite.setYDirection(1);
			break;
		case LEFT:
			sprite.setXDirection(-1);
			sprite.setYDirection(0);
			break;
		case RIGHT:
			sprite.setXDirection(1);
			sprite.setYDirection(0);
			break;
		case BOTTOM_RIGHT:
			sprite.setXDirection(1);
			sprite.setYDirection(1);
			break;
		case BOTTOM_LEFT:
			sprite.setXDirection(-1);
			sprite.setYDirection(1);
			break;
		case TOP_RIGHT:
			sprite.setXDirection(1);
			sprite.setYDirection(-1);
			break;
		case TOP_LEFT:
			sprite.setXDirection(-1);
			sprite.setYDirection(-1);
			break;
		default:
			sprite.setXDirection(0);
			sprite.setYDirection(0);
			break;
		}
	}
}
