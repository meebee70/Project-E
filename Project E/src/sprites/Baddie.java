package sprites;

public abstract class Baddie extends MovingSprite{

	protected double score;
	
	public Baddie(double score, double speed){
		super(50, 50, speed);
		this.score = score;
	}
	
	public Baddie(int x, int y, double score, double speed){
		super(x, y, speed);
		this.score = score;
	}
	
	public void loseLife() {
		//TODO Lose a life when hit
	}
	
}
