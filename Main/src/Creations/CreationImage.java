/**
 * this file is the implementation of the class <code>CreationImage</code>
 * this class formulates the general layer where the specific forms of imaegs falls under
 * 
 * @author: 
 */


package Creations;

import TowDimensionTransformation.TowDimensionTransformation;
import java.awt.image.BufferedImage;

/* 
 * abstract layer for the images 
 * the functionalities on the images are declard yet not implemented 
*/
public class CreationImage implements TowDimensionTransformation{
    /* the dimensions of an Image */
    protected static final int WIDTH = 720; 
    protected static final int HEIGHT = 720; 
    /* container of the image content */
    protected BufferedImage content;

    /* constructor */
    public CreationImage(){
        /* initializing the <code>content</code> field */
        this.content = new BufferedImage(this.WIDTH, this.HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    /* loading an image to the <code>content</code> field */
    public abstract BufferedImage loadImage(String imagePath);

    /* free the object from the data */
    public abstract void clearImage();

    /* 2D scaling */
    @Override 
    public void scale(double scaleFactor){}
    
    /* 2D rotation */
    @Override
    public void rotate(double angleInRadians){}
    
    /* 2D translation */
    @Override
    public void translate(int dx, int dy){}
}