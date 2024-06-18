/**
 * this file is the declartion of the interface <code>TowDimensionTransformation</code> 
 * this abstract interface provides the constract for any GUI component that can be 
 * physically mainpulated in a 2D space
 * 
 * @author:   
 */

package TowDimensionTransformation;

public abstract interface TowDimensionTransformation {
    /**
     * the scaling will expand or shrink the size of a component 
     * based on the value of the factor. Let the factor be <code>scaleFactor</code>
     * @param scaleFactor The factor by which to scale the content
     * shrink the component if it was below 1 and expand it proportionally otherwise   
     */
    public abstract void scale(double scaleFactor);
    
    /**
     * rotation revolves a component in the coordinate system
     * the rotation degree is set to the input parameter
     * @param angleInRadians The angle in radians by which to rotate the content
     * Positive values rotate clockwise, negative values rotate counterclockwise
     */
    public abstract void rotate(double angleInRadians);
    
    /**
     * translation reposition a GUI component from its position to another coordinate location
     * @param dx The amount to translate along the x-axis
     * @param dy The amount to translate along the y-axis
     */
    public abstract void translate(int dx, int dy);
}