package backgrounds;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Background {

    private Image background;
    private int backgroundWidth = 0;
    private int backgroundHeight = 0;

	public abstract Tile getTile(int col, int row);
	/**
	 * which column is position x siting in? 
	 * @param x
	 * @return
	 */
	public abstract int getCol(int x);
	
	/**
	 * which row is position y siting in?
	 * @param y
	 * @return
	 */
	public abstract int getRow(int y);
	
}
