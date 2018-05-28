package backgrounds;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameBackGround extends Background{

	
	private Image grass;
	private int width,height;
	
	public GameBackGround(String imageLocation){
		try {
			grass = ImageIO.read(new File(imageLocation));
			width = grass.getWidth(null);
			height = grass.getHeight(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Tile getTile(int col, int row) {
		return new Tile(grass, col * width, row * height, width, height, false);
	}

	@Override
	public int getCol(int x) {
		return x/grass.getWidth(null);
	}

	@Override
	public int getRow(int y) {
		return y/grass.getHeight(null);
	}

}
