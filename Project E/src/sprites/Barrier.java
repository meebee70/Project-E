package sprites;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.KeyboardInput;

public class Barrier extends Sprite{
	
	public Barrier(double x, double y){
		super(x,y);
		
		try {
			setDefaultImage(ImageIO.read(new File("res/test barrier.png")));
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public Barrier(double x, double y, Image img){
		super(x,y,img);
		
	}

	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {
		
		
	}

	@Override
	public double getMinX() {
		return currentX;
	}

	@Override
	public double getMaxX() {
		return currentX + getWidth();
	}

	@Override
	public double getMinY() {
		return currentX;
	}

	@Override
	public double getMaxY() {
		return currentY + getHeight();
	}

	@Override
	public Image getImage() {
		return defaultImage;
	}

	@Override
	public void setMinX(double currentX) {
		
	}

	@Override
	public void setMinY(double currentY) {
		
	}

	@Override
	public boolean checkCollisions(double x, double y) {
		return false;
	}


	
	
}
