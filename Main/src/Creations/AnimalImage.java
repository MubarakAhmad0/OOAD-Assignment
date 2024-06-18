/**
 * this file is the implementation of the class <code>AnimalImage</code>
 * the class provide a new type of <code>CrationImage</code> which holds Animal images
 * 
 * @author:
 */
package Creations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/* specifc format of <code>CreationImage</code> that is images of Animals*/
public class AnimalImage extends CreationImage{

    /* this constructor rely on the parent constructor*/
    public AnimalImage(){super();}  
    
    /* concrete implementation to the <code>loadImage</code> method */
    @Override
    public BufferedImage loadImage(String filePath){
        try {
            File file = new File(filePath);

            if (file.exists()) {
                BufferedImage newImage = ImageIO.read(file);
                if (newImage != null) {
                    this.content = ImageIO.read(file);
                }
            } else {
                System.err.println("File not found: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.content;
    }

    /* concrete implementation of the <code>clearImage</code> method */
    @Override
    public void clearImage(){
        this.content = null; 
    }

    /* 2D scaling */
    @Override
    public void scale(double scaleFactor){
        //TODO: implementation and documentation
    }
   
    /* 2D rotation */
    @Override        
    public void rotate(double angleInRadians){
        //TODO: implementation and documentation
    } 
        
    /* 2D translation */
    @Override
    public void translate(int dx, int dy){
        //TODO: implementation and documentation  
    }
    
}
