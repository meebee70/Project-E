package sprites;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import engine.KeyboardInput;

public abstract class Sprite {

	protected Image defaultImage;
	protected double currentX = 0;
	protected double currentY = 0;
	protected int IMAGE_WIDTH = 50; // sprite.get_width()
	protected int IMAGE_HEIGHT = 50; //sprite.get_height()
	private boolean dispose = false;
	
//	double currentX = 0;
//	double currentY = 0;
//	protected Image image_default;
	
	public boolean getDispose() {
		return dispose;
	}

	public void setDispose() {
		this.dispose = true;
	}
	ArrayList<Rectangle> barriers;
	ArrayList<Sprite> sprites;
	KeyboardInput keyboard;
		
	public Sprite() {
//		try {
//			this.image_default = ImageIO.read(new File("res/player-left.png"));
//		}
//		catch (IOException e) {
//		}
	}

	public abstract void update(KeyboardInput keyboard, long actual_delta_time);
	
	public abstract double getMinX();
	public abstract double getMaxX();
	public abstract double getMinY();
	public abstract double getMaxY();
	public abstract Image getImage();
	
	public long getHeight(){
		return IMAGE_HEIGHT;
	}
	public long getWidth(){
		return IMAGE_WIDTH;
	}
	
	public final double getXPos(){
		return currentX;
	}
	public final double getYPos(){
		return currentY;
	}
	
	public abstract void setMinX(double currentX);
	public abstract void setMinY(double currentY);
	
	public void setBarriers(ArrayList<Rectangle> barriers){
		this.barriers = barriers;
	}
	
	public void setSprites(ArrayList<Sprite> staticSprites){
		this.sprites = staticSprites;
	}
	
	public void setKeyboard(KeyboardInput keyboard){
		this.keyboard = keyboard;
	}
	
	public abstract boolean checkCollisionWithSprites(double x, double y);
	public abstract boolean checkCollisionWithBarrier(double x, double y);
	
}
