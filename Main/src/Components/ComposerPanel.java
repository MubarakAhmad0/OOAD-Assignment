package Components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ComposerPanel extends JPanel {
    private static List<TransformativeImage> images;
    private TransformativeImage selectedImage;
    private Point prevPt;
    private int compositionCount = 0;  // Counter to differentiate between saved compositions
    private final int FLIP_THRESHOLD = 100;  // Minimum pixels to drag before triggering a flip
    private final double ROTATION_FACTOR = 0.005;  // Factor to slow down rotation

    public ComposerPanel() {
        images = new ArrayList<>();
        this.setBackground(Color.LIGHT_GRAY);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (TransformativeImage img : images) {
                    if (img.contains(e.getPoint())) {
                        selectedImage = img;
                        prevPt = e.getPoint();
                        return;
                    }
                }
                selectedImage = null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedImage != null) {
                    Point currentPt = e.getPoint();
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        selectedImage.translate(
                                (int) (currentPt.getX() - prevPt.getX()),
                                (int) (currentPt.getY() - prevPt.getY())
                        );
                        prevPt = currentPt;
                        repaint();
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        double dx = currentPt.getX() - prevPt.getX();
                        double dy = currentPt.getY() - prevPt.getY();

                        if (Math.abs(dx) > Math.abs(dy)) {
                            // Horizontal drag: rotate
                            double angle = Math.toRadians(dx) * ROTATION_FACTOR;  // Rotate based on horizontal mouse movement
                            selectedImage.rotate(angle);
                        } else if (Math.abs(dy) > FLIP_THRESHOLD) {
                            // Vertical drag exceeding threshold: flip
                            selectedImage.flipVertically();
                            prevPt = currentPt;  // Reset previous point to avoid continuous flipping
                        }

                        repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                prevPt = null;
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (selectedImage != null) {
                    int notches = e.getWheelRotation();
                    double scaleFactor = 1.1;

                    if (notches < 0) {
                        selectedImage.scale(scaleFactor);
                    } else {
                        selectedImage.scale(1.0 / scaleFactor);
                    }
                    repaint();
                }
            }
        };

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
        this.addMouseWheelListener(mouseAdapter);
    }

    public void addImage(ImageIcon imageIcon) {
        images.add(new TransformativeImage(imageIcon));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (TransformativeImage img : images) {
            g.drawImage(img.getImageIcon().getImage(), img.getX(), img.getY(), null);
        }
    }

    public void deleteSelectedImage() {
        if (selectedImage != null) {
            images.remove(selectedImage);
            selectedImage = null;
            repaint();
        }
    }

    public void saveComposition() {
        compositionCount++;
        String storagePath = "C:\\Users\\ASUS\\Documents\\GitHub\\OOAD-Assignment\\Main\\Assets\\Compositions\\composition" + compositionCount + ".png";

        try {
            BufferedImage bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            this.paint(g2d);
            g2d.dispose();

            ImageIO.write(bufferedImage, "png", new File(storagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rotateComposition() {
        double angle = Math.toRadians(90);  // Rotate 90 degrees clockwise
        // Get the center of the panel
        int centerX = this.getWidth() / 2 - 100;
        int centerY = this.getHeight() / 2 - 100;

        for (TransformativeImage img : images) {
            // Translate image position relative to the center
            int x = img.getX() - centerX;
            int y = img.getY() - centerY;

            // Apply rotation transformation
            double cosAngle = Math.cos(angle);
            double sinAngle = Math.sin(angle);

            int newX = (int) (x * cosAngle - y * sinAngle);
            int newY = (int) (x * sinAngle + y * cosAngle);

            // Translate image back to the original position relative to the center
            newX += centerX;
            newY += centerY;

            img.setPosition(newX, newY);
            img.rotate(angle);
        }

        repaint();
    }


    private static class TransformativeImage {
        private final ImageIcon imageIcon;
        private Point screenPosition;
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

            g2d.rotate(angle, width / 2, height / 2);
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
}
