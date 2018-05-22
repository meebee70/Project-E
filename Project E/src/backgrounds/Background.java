package backgrounds;

/**
 * this is a generic superclass to group together various forms of backgrounds
 * @author Mr Wehnes
 *
 */
public abstract class Background {

    /**
     * returns the tile located at the4 specified position
     * @param col
     * @param row
     * @return
     */
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
