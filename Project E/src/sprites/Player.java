package sprites;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import engine.KeyboardInput;

public class Player extends Sprite {

	
	private static double starterX = 300;
	private static double starterY = 300;
	
	public Player(){
		super (starterX,starterY);
		
		try{
			setDefaultImage(ImageIO.read(new File("res/download.jpg")));
		}catch(Exception e){e.printStackTrace();}
	}
	
	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {
		if (keyboard.keyDown(65)){//a
			currentX--;
		}
		if (keyboard.keyDown(83)){//s
			currentY++;
		}
		if (keyboard.keyDown(68)){//d
			currentX++;
		}
		if (keyboard.keyDown(87)){//w
			currentY--;
		}
	}

	@Override
	public Image getImage() {
		return defaultImage;
	}

	@Override
	public boolean checkCollisions(double x, double y) {
		return false;
	}

}
