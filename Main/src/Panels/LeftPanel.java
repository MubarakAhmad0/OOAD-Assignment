package Panels;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import Creations.CreationImage;
import TowDimensionTransformation.TowDimensionTransformation;

/**
 * Represents a JPanel that implements two-dimensional transformations.
 * This panel can display images and perform scaling, rotation, and translation operations.
 */
public class LeftPanel extends JPanel implements TowDimensionTransformation {
    private static final int WIDTH = 720;
    private static final int HEIGHT = 720;
    private CreationImage entity = new CreationImage();
    private BufferedImage content;

    /**
     * Initializes the panel with default settings.
     * Sets preferred size, background color, and initializes content.
     */
    public void initSettings() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.GRAY);
        this.setVisible(true);
        this.content = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Uploads an image to be displayed on the panel.
     * @param image The CreationImage object representing the image.
     * @param imagePath The path to the image file.
     */
    public void uploadImage(CreationImage image, String imagePath) {
        this.entity = image;
        this.content = entity.loadImage(imagePath);
    }

    /**
     * Clears the current image content from the panel
     */
    public void clearImage() {
        this.content = null;
        repaint();
    }

    /**
     * Composes the panel content
     * This method is currently not implemented
     */
    public void composePanel() {
        // TODO: Implement this function
    }

    /**
     * Scales the panel content by the specified factor
     * @param scaleFactor The factor by which to scale the content
     */
    @Override
    public void scale(double scaleFactor) {
        // TODO: Implement scaling functionality
    }

    /**
     * rotates the panel content by the specified angle
     * @param angleInRadians The angle in radians by which to rotate the content
     * positive values rotate clockwise, negative values rotate counterclockwise
     */
    @Override
    public void rotate(double angleInRadians) {
        // TODO: Implement rotation functionality
    }

    /**
     * translates the panel content by the specified amounts
     * @param dx The amount to translate along the x-axis
     * @param dy The amount to translate along the y-axis
     */
    @Override
    public void translate(int dx, int dy) {
        // TODO: Implement translation functionality
    }
}
