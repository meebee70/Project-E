package sprites;

import java.awt.Image;

public abstract class Baddie extends Sprite{

	protected double score;
	
	public Baddie(double score){
		super(50,50);
		this.score = score;
	}
	
	public Baddie(int x, int y, double score){
		super(x,y);
		this.score = score;
	}
	
	public Baddie(int x, int y, Image img, double score){
		super(x,y,img);
		this.score = score;
	}
	
}
