package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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
