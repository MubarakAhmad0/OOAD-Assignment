/**
 * this file is a concrete implementation of the class <code>RightPanel</code> which play's the role of the drawing canvas
 * 
 * @author:
 */

package Panels;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

import TowDimensionTransformation.TowDimensionTransformation;

/**
 * Represents a JPanel for drawing and image manipulation.
 * Implements two-dimensional transformations and supports drawing functionality.
 */
public class RightPanel extends JPanel implements TowDimensionTransformation {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    private BufferedImage image;
    private Graphics2D g2d;
    private Point mousePnt = new Point();
    private Color drawColor = Color.BLACK;
    private int lineSize = 4;
    private String saveForm = ".png";
    private boolean eraserMode = false;

    /**
     * Constructs a RightPanel with default settings and initializes components.
     */
    public RightPanel() {
        this.initSettings();
        this.clearImage();
    }

    /**
     * Initializes panel settings, including size, background color, and mouse listeners.
     * Sets up initial image and graphics context.
     */
    public void initSettings() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);
        this.setVisible(true);

        this.addMouseMotionListener(new MouseInteraction());
        this.addMouseListener(new MouseInteraction());

        this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        this.g2d = (Graphics2D) image.getGraphics();
        this.g2d.setColor(drawColor);
    }

    /**
     * Overrides the paintComponent method to draw the current image.
     * @param g The Graphics context.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    /**
     * Clears the current drawing on the panel.
     * Resets the image to a blank white canvas.
     */
    public void clearImage() {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(drawColor);
        this.repaint();
    }

    /**
     * Saves the current image to a file.
     * @param filePath The path where the image will be saved.
     * @param extension The file extension (e.g., ".png", ".jpg").
     */
    public void saveImage(String filePath, String extension) {
        if (!filePath.toLowerCase().endsWith(extension.toLowerCase())) {
            filePath += extension;
        }

        try {
            File outputFile = new File(filePath);
            ImageIO.write(image, extension.substring(1), outputFile);
            JOptionPane.showMessageDialog(this, "The image has been saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "The image couldn't be saved!");
            e.printStackTrace();
        }
    }

    /**
     * Handles mouse interactions for drawing on the panel.
     * Implements MouseMotionListener and MouseListener interfaces.
     */
    private class MouseInteraction implements MouseMotionListener, MouseListener {
        @Override
        public void mouseDragged(MouseEvent e) {
            Point currentPoint = e.getPoint();
            g2d.setStroke(new BasicStroke(lineSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            if (eraserMode) {
                g2d.setColor(getBackground());
            } else {
                g2d.setColor(drawColor);
            }
            g2d.drawLine(mousePnt.x, mousePnt.y, currentPoint.x, currentPoint.y);
            mousePnt = currentPoint;
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mousePnt = e.getPoint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mousePnt = e.getPoint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    /**
     * Placeholder for scaling functionality.
     * @param scaleFactor The factor by which to scale the panel content.
     */
    @Override
    public void scale(double scaleFactor) {
        // TODO: Implement scaling functionality.
    }

    /**
     * Placeholder for rotation functionality.
     * @param angleInRadians The angle in radians by which to rotate the panel content.
     */
    @Override
    public void rotate(double angleInRadians) {
        // TODO: Implement rotation functionality.
    }

    /**
     * Placeholder for translation functionality.
     * @param dx The amount to translate along the x-axis.
     * @param dy The amount to translate along the y-axis.
     */
    @Override
    public void translate(int dx, int dy) {
        // TODO: Implement translation functionality.
    }

    /**
     * Sets the color used for drawing.
     * @param color The color to set for drawing.
     */
    public void setDrawColor(Color color) {
        this.drawColor = color;
    }

    /**
     * Sets the size of the line used for drawing.
     * @param size The size of the line to set.
     */
    public void setLineSize(int size) {
        this.lineSize = size;
    }

    /**
     * Sets the eraser mode for drawing.
     * @param mode true to enable eraser mode, false otherwise.
     */
    public void setEraserMode(boolean mode) {
        this.eraserMode = mode;
    }

    /**
     * Sets the file format for saving images.
     * @param form The file format extension (e.g., ".png", ".jpg").
     */
    public void setSaveForm(String form) {
        this.saveForm = form;
    }

    /**
     * Gets the current file format for saving images.
     * @return The current file format extension.
     */
    public String getSaveForm() {
        return this.saveForm;
    }
}
