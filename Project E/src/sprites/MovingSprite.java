package sprites;

import engine.GameLoop;
import engine.KeyboardInput;

public abstract class MovingSprite extends Sprite{
	
	protected int xDirection, yDirection;
	private double moveSpeed, xSpeed, ySpeed;
	
	public MovingSprite(int startingX, int startingY, double moveSpeed) {
		super(startingX, startingY);
		this.moveSpeed = moveSpeed;
	}
	
	public void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game) {
		this.setDirection(keyboard, game);
		
		xSpeed = this.getXDirection() * moveSpeed;
		ySpeed = this.yDirection * moveSpeed;
		
		if (this.isWrapAround()) {
			if (this.getXPos() + this.getWidth() < 0) {	//Too far left
				xSpeed += game.SCREEN_WIDTH;
			} else if (this.getXPos() + this.getWidth() > game.SCREEN_WIDTH) {//Too far right
				xSpeed -= game.SCREEN_WIDTH;
			}
			
			if (this.getYPos() + this.getHeight() < 0) {
				ySpeed += game.SCREEN_HEIGHT;
			} else if (this.getYPos() + this.getHeight() > game.SCREEN_HEIGHT) {
				ySpeed -= game.SCREEN_HEIGHT;
			}
		}
		
		this.move(xSpeed, ySpeed, game);
	}
	
	protected abstract void setDirection(KeyboardInput keyboard, GameLoop game);
	
	protected int getXDirection() {
		return this.xDirection;
	}
	
	protected int getYDirection() {
		return this.yDirection;
	}
	
	protected void setXDirection(int xDirection) {
		this.xDirection = xDirection;
	}

	protected void setYDirection(int yDirection) {
		this.yDirection = yDirection;
	}

}
