package Components;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
                    selectedImage.translate(
                            (int) (currentPt.getX() - prevPt.getX()),
                            (int) (currentPt.getY() - prevPt.getY())
                    );
                    prevPt = currentPt;
                    repaint();
                }
            }
        };

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
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
        // Save composition to a file
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String storagePath = fileChooser.getSelectedFile().getAbsolutePath();
    
            if (!storagePath.endsWith(".png")) {
                storagePath += ".png";
            }
    
            try {
                // Get BufferedImage from ImageIcon
                BufferedImage bufferedImage = new BufferedImage(
                        selectedImage.getImageIcon().getIconWidth(),
                        selectedImage.getImageIcon().getIconHeight(),
                        BufferedImage.TYPE_INT_ARGB);
                
                Graphics2D g2d = bufferedImage.createGraphics();
                g2d.drawImage(selectedImage.getImageIcon().getImage(), 0, 0, null);
                g2d.dispose();
    
                // Write BufferedImage to file
                ImageIO.write(bufferedImage, "png", new File(storagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class TransformativeImage {
        private final ImageIcon imageIcon;
        private Point screenPosition;
        private double angle;
        private double size;

        public TransformativeImage(ImageIcon imageIcon) {
            this.imageIcon = imageIcon;
            this.screenPosition = new Point(0, 0);  // Initialize screen position at (0, 0)
            this.angle = 0.0;  // Initial angle
            this.size = 1.0;   // Initial size
        }

        public ImageIcon getImageIcon() {
            // Depending on transformations applied, return appropriate ImageIcon
            Image img = imageIcon.getImage();
            int width = img.getWidth(null);
            int height = img.getHeight(null);

            // Scale image
            int scaledWidth = (int) (width * size);
            int scaledHeight = (int) (height * size);
            Image scaledImg = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

            // Rotate image
            BufferedImage rotatedImg = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = rotatedImg.createGraphics();
            g2d.rotate(angle, scaledWidth / 2, scaledHeight / 2);
            g2d.drawImage(scaledImg, 0, 0, null);
            g2d.dispose();

            return new ImageIcon(rotatedImg);
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

        public boolean contains(Point p) {
            int imgWidth = imageIcon.getIconWidth();
            int imgHeight = imageIcon.getIconHeight();
            return p.x >= getX() && p.x <= getX() + imgWidth && p.y >= getY() && p.y <= getY() + imgHeight;
        }
    }
}
