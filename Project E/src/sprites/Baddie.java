package sprites;

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
	
}
