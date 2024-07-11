package Actors;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TransformativeImage {
    private final ImageIcon imageIcon;
    private final Point screenPosition;
    private double angle;
    private double size;
    private boolean flippedVertically;

    public TransformativeImage(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        this.screenPosition = new Point(0, 0);
        this.angle = 0.0;
        this.size = 1.0;
        this.flippedVertically = false;
    }

    public ImageIcon getImageIcon() {
        Image img = imageIcon.getImage();
        int width = (int) (img.getWidth(null) * size);
        int height = (int) (img.getHeight(null) * size);

        BufferedImage transformedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = transformedImg.createGraphics();

        if (flippedVertically) {
            g2d.scale(1, -1);
            g2d.translate(0, -height);
        }

        g2d.rotate(angle, (double) width / 2, (double) height / 2);
        g2d.drawImage(img, 0, 0, width, height, null);
        g2d.dispose();

        return new ImageIcon(transformedImg);
    }

    public int getX() {
        return (int) screenPosition.getX();
    }

    public int getY() {
        return (int) screenPosition.getY();
    }

    public void translate(int dx, int dy) {
        screenPosition.setLocation(screenPosition.getX() + dx, screenPosition.getY() + dy);
    }

    public void rotate(double rotationAngle) {
        angle += rotationAngle;
    }

    public void scale(double scaleFactor) {
        size *= scaleFactor;
    }

    public void flipVertically() {
        flippedVertically = !flippedVertically;
    }

    public void setPosition(int x, int y) {
        this.screenPosition.setLocation(x, y);
    }

    public boolean contains(Point p) {
        int imgWidth = (int) (imageIcon.getIconWidth() * size);
        int imgHeight = (int) (imageIcon.getIconHeight() * size);
        return p.x >= getX() && p.x <= getX() + imgWidth && p.y >= getY() && p.y <= getY() + imgHeight;
    }
}
