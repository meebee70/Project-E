package sprites;

import java.awt.Image;

public abstract class Barrier extends Sprite{

	protected double x,y;
	protected double width,height;
	protected Image sprite;
	
	public Barrier(int x, int y, int width, int height, Image img){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		sprite = img;
	}
}
