package sprites;

import engine.GameLoop;

public abstract class Baddie extends MovingSprite{

	protected int score;
	
	public Baddie(int score, double speed){
		super(50, 50, speed);
		this.score = score;
	}
	
	public Baddie(int x, int y, int score, double speed){
		super(x, y, speed);
		this.score = score;
	}
	
	public Baddie(int x, int y, int score, double speed, int lives){
		super(x,y,speed);
		this.score = score;
		setLives(lives);
	}
	
	public int getScore() {
		return this.score;
	}
	
	public abstract void collideWithPlayer(Player1 player);
	
	protected void move(double xDistance, double yDistance, GameLoop game) {
		this.addX(xDistance);
		this.addY(yDistance);
		if (!hittingPlayer(1, game)) {
			hittingPlayer(2, game);
		}
		
		this.addX(-xDistance);
		this.addY(-yDistance);
		super.move(xDistance, yDistance, game);
	}
	
	private boolean hittingPlayer(int playerID, GameLoop game) {
		if (game.getPlayer(playerID).isAlive() == false) {
			return false;
		}
		if (this.checkCollisions(game.getPlayer(playerID))) {
			this.collideWithPlayer(game.getPlayer(playerID));
			return true;
		} else {
			return false;
		}
	}
	
}
