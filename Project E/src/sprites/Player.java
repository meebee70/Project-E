package sprites;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import engine.GameLoop;
import engine.KeyboardInput;
import misc.Constants;

public class Player extends Sprite {

	
	private static double starterX = 300;
	private static double starterY = 300;
	private GameLoop game;
	
	public Player(GameLoop game){
		super (starterX, starterY);
		this.game = game;
		
		try{
			setDefaultImage(ImageIO.read(new File("res/character sprites/download.jpg")));
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(KeyboardInput keyboard, long actual_delta_time) {
		double xSpeed = 0;
		double ySpeed = 0;
		
		//Get Inputs
		if (keyboard.keyDown(Constants.playerOneLeft)){
			xSpeed--;
		}
		if (keyboard.keyDown(Constants.playerOneDown)){
			ySpeed++;
		}
		if (keyboard.keyDown(Constants.playerOneRight)){
			xSpeed++;
		}
		if (keyboard.keyDown(Constants.playerOneUp)){
			ySpeed--;
		}
		
		this.currentX += xSpeed;
		this.currentY += ySpeed;
		
		for (Barrier wall : game.getBarriers()) {
			if (this.checkCollisions(wall)) {
				this.currentX -= xSpeed;
				this.currentY -= ySpeed;
				break;
			}
		}
		

	}

	@Override
	public Image getImage() {
		return defaultImage;
	}

}
