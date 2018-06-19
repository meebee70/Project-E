package sprites;

import engine.GameLoop;
import engine.KeyboardInput;

public abstract class MovingSprite extends Sprite{
	
	private int xDirection, yDirection;
	private double moveSpeed, baseMoveSpeed, xDistance, yDistance;
	
	public MovingSprite(int startingX, int startingY, double moveSpeed) {
		super(startingX, startingY);
		this.baseMoveSpeed = moveSpeed;
		this.moveSpeed = moveSpeed;
	}
	
	public void update(KeyboardInput keyboard, GameLoop game) {
		this.setDirection(keyboard, game);
		
		xDistance = this.getXDirection() * moveSpeed;
		yDistance = this.yDirection * moveSpeed;
		
		if (this.isWrapAround()) {
			if (this.getXPos() + this.getWidth() < 0) {	//Too far left
				xDistance += game.SCREEN_WIDTH;
			} else if (this.getXPos() + this.getWidth() > game.SCREEN_WIDTH) {//Too far right
				xDistance -= game.SCREEN_WIDTH;
			}
			
			if (this.getYPos() + this.getHeight() < 0) {
				yDistance += game.SCREEN_HEIGHT;
			} else if (this.getYPos() + this.getHeight() > game.SCREEN_HEIGHT) {
				yDistance -= game.SCREEN_HEIGHT;
			}
		}
		
		this.move(xDistance, yDistance, game);
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
	
	public void setXDirection(int xDirection) {
		this.xDirection = xDirection;
	}

	public void setYDirection(int yDirection) {
		this.yDirection = yDirection;
	}
	
	protected void setSpeed(double newSpeed){
		this.baseMoveSpeed = newSpeed;
	}
	
	public void speedMult(double newMult){
		this.moveSpeed = baseMoveSpeed * newMult;
	}
	
	public double getSpeedMult(){
		return this.moveSpeed / this.baseMoveSpeed;
	}

}
