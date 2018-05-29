package sprites;

import engine.GameLoop;
import engine.KeyboardInput;

public abstract class MovingSprite extends Sprite{
	
	protected int xSpeed, ySpeed, xDirection, yDirection;
	private double moveSpeed;
	
	public MovingSprite(int startingX, int startingY, double moveSpeed) {
		super(startingX, startingY);
		this.moveSpeed = moveSpeed;
	}
	
	public void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game) {
		this.setDirection(keyboard, game);
		
		xSpeed = (int) (this.getXDirection() * moveSpeed);
		ySpeed = (int) (this.yDirection * moveSpeed);
		
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
