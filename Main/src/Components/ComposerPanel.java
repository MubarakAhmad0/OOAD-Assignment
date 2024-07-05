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
    private static List<DraggableImage> images;
    private DraggableImage selectedImage;
    private Point prevPt;

    public ComposerPanel() {
        images = new ArrayList<>();
        this.setBackground(Color.LIGHT_GRAY);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (DraggableImage img : images) {
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
        images.add(new DraggableImage(imageIcon));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (DraggableImage img : images) {
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
    


    private static class DraggableImage {
        private final ImageIcon imageIcon;
        private int x, y;

        public DraggableImage(ImageIcon imageIcon) {
            this.imageIcon = imageIcon;
            this.x = 0;
            this.y = 0;
        }

        public ImageIcon getImageIcon() {
            return imageIcon;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void translate(int dx, int dy) {
            this.x += dx;
            this.y += dy;
        }

        public boolean contains(Point p) {
            int imgWidth = imageIcon.getIconWidth();
            int imgHeight = imageIcon.getIconHeight();
            return p.x >= x && p.x <= x + imgWidth && p.y >= y && p.y <= y + imgHeight;
        }
    }
}
