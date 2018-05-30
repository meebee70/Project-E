package backgrounds;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ShopBackground extends Background {


	private Image img;
	private int width, height;
	
	public ShopBackground (String imgSource){
		try {
			img = ImageIO.read(new File(imgSource));
			width = img.getWidth(null);
			height = img.getHeight(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Tile getTile(int col, int row) {
		return new Tile(img, col * width, row * height, width, height, false);
	}

	@Override
	public int getCol(int x) {
		return width/x;
	}

	@Override
	public int getRow(int y) {
		return height/y;
	}

}
