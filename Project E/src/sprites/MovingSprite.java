package sprites;

import engine.GameLoop;
import engine.KeyboardInput;

public abstract class MovingSprite extends Sprite{
	
	protected int xDirection, yDirection;
	private double baseMoveSpeed;
	private double moveSpeed, xSpeed, ySpeed;
	
	public MovingSprite(int startingX, int startingY, double moveSpeed) {
		super(startingX, startingY);
		this.baseMoveSpeed = moveSpeed;
		this.moveSpeed = moveSpeed;
	}
	
	public void update(KeyboardInput keyboard, long actual_delta_time, GameLoop game) {
		this.setDirection(keyboard, game);
		
		xSpeed = this.xDirection * moveSpeed;
		ySpeed = this.yDirection * moveSpeed;
		
		if (this.isWrapAround()) {
			if (this.getXPos() + this.getWidth() < 0) {	//Too far left
				xSpeed += game.SCREEN_WIDTH + this.getWidth()/2;
			} else if (this.getXPos() > game.SCREEN_WIDTH) {//Too far right
				xSpeed -= game.SCREEN_WIDTH + this.getWidth()/2;
			}
			
			if (this.getYPos() + this.getHeight() < 0) {
				ySpeed += game.SCREEN_HEIGHT + this.getHeight() /2;
			} else if (this.getYPos() > game.SCREEN_HEIGHT) {
				ySpeed -= game.SCREEN_HEIGHT + this.getHeight()/2;
			}
		}
		
		this.move(xSpeed, ySpeed, game);
	}
	
	/**
	 * Move the sprite in a direction
	 * Checks whether or not it can collide with objects
	 * @param xDirection
	 * @param yDirection
	 * @param game
	 */
	protected void move(double xDistance, double yDistance, GameLoop game) {
		if (xDistance != 0) {
			this.addX(xDistance);
			game.repaint();
			if (this.isCollideable()) {
				for (Sprite wall : game.getBarriers()) {
					if (wall != this) {
						//Potentially uses lazy evaluation
						while (this.checkCollisions(wall) || wall.checkCollisions(this)) {
							this.addX(-xDirection);
						}
					}
				}
			}
		}
		
		if (yDistance != 0) {
			this.addY(yDistance);
			game.repaint();
			if (this.isCollideable()) {
				for (Sprite wall : game.getBarriers()) {
					if (wall != this) {
						//Potentially uses lazy evaluation
						while (this.checkCollisions(wall) || wall.checkCollisions(this)) {//there is a problem where is wall is moving into us, we cannot move
							this.addY(-yDirection);
						}
					}
				} 
			}
		}
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
	
	protected void setSpeed(double newSpeed){
		this.baseMoveSpeed = newSpeed;
	}
	
	public void speedMult(double newMult){
		this.moveSpeed = baseMoveSpeed * newMult;
	}

}
