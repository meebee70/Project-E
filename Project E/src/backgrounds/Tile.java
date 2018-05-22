package backgrounds;
import java.awt.Image;

/**
 * A Tile is a repeating image used in backgrounds
 * @author Mr Wehnes
 *
 */
public class Tile {

	Image image = null;
	int x = 0;
	int y = 0;
	int height = 0;
	int width = 0;
	boolean outOfBounds = false;
		
	/**
	 *
	 * @param image the image for this tile
	 * @param x 
	 * @param y
	 * @param width
	 * @param height
	 * @param outOfBounds
	 */
	public Tile(Image image, int x, int y, int width, int height, boolean outOfBounds) {
		super();
		this.image = image;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.outOfBounds = outOfBounds;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public boolean isOutOfBounds() {
		return outOfBounds;
	}
		
}
