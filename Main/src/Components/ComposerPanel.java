package Components;

import Actors.TransformativeImage;
import Logic.ImageManipulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class ComposerPanel extends JPanel {
    private final List<TransformativeImage> images;
    private TransformativeImage selectedImage;
    private Point prevPt;
    private int compositionCount = 0;
    private final int FLIP_THRESHOLD = 100;
    private final double ROTATION_FACTOR = 0.005;
    private final ImageManipulator imageManipulator;

    public ComposerPanel() {
        images = new ArrayList<>();
        this.setBackground(Color.LIGHT_GRAY);
        imageManipulator = new ImageManipulator();

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (TransformativeImage img : images) {
                    if (img.contains(e.getPoint())) {
                        selectedImage = img;
                        prevPt = e.getPoint();
                        imageManipulator.resetFlipFlag();
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
                        imageManipulator.translate(selectedImage, currentPt, prevPt);
                        prevPt = currentPt;
                        repaint();
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        imageManipulator.rotateOrFlip(selectedImage, currentPt, prevPt, ROTATION_FACTOR, FLIP_THRESHOLD);
                        repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                prevPt = null;
                imageManipulator.resetFlipFlag();
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (selectedImage != null) {
                    imageManipulator.scale(selectedImage, e.getWheelRotation());
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
        imageManipulator.saveComposition(this, ++compositionCount);
    }

    public void rotateComposition() {
        imageManipulator.rotateComposition(this, images);
        repaint();
    }
}