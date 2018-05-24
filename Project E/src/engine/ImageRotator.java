package engine;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
/**
 * a class that can be used to rotate images
 * @author Mr Wehnes
 *
 */
public class ImageRotator {

	/**
	 * rotates the specified image by the specified angle clockwise
	 * @param original original image
	 * @param angle angle to rotate (in degrees)
	 * @return the rotated image
	 */
	public static Image rotate(Image original, int angle) {
	    		
	    AffineTransform tx = new AffineTransform();
	    tx.rotate(Math.toRadians(angle), original.getWidth(null) / 2, original.getHeight(null) / 2);
	    	    
	    AffineTransformOp op = new AffineTransformOp(tx,
	            AffineTransformOp.TYPE_BILINEAR);
	    Image rotated = op.filter((BufferedImage)original, null);
	    
	    return rotated;
	    
	}
	
}
