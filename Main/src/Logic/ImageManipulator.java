package Logic;

import Actors.TransformativeImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageManipulator {

    private boolean hasFlipped = false;

    public ImageManipulator() {
        this.hasFlipped = false;
    }
    public void translate(TransformativeImage image, Point currentPt, Point prevPt) {
        image.translate(
                (int) (currentPt.getX() - prevPt.getX()),
                (int) (currentPt.getY() - prevPt.getY())
        );
    }

    public void rotateOrFlip(TransformativeImage image, Point currentPt, Point prevPt, double rotationFactor, int flipThreshold) {
        double dx = currentPt.getX() - prevPt.getX();
        double dy = currentPt.getY() - prevPt.getY();

        if (Math.abs(dx) > Math.abs(dy)) {
            double angle = Math.toRadians(dx) * rotationFactor;
            image.rotate(angle);
        } else if (Math.abs(dy) > flipThreshold && !hasFlipped) {
            image.flipVertically();
            hasFlipped = true;
        }
    }

    public void scale(TransformativeImage image, int wheelRotation) {
        double scaleFactor = 1.1;
        if (wheelRotation < 0) {
            image.scale(scaleFactor);
        } else {
            image.scale(1.0 / scaleFactor);
        }
    }

    public void saveComposition(JPanel panel, int compositionCount) {
        String storagePath = "Main/Assets/Compositions/composition" + compositionCount + ".png";

        try {
            BufferedImage bufferedImage = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            panel.paint(g2d);
            g2d.dispose();

            ImageIO.write(bufferedImage, "png", new File(storagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rotateComposition(JPanel panel, List<TransformativeImage> images) {
        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();

        double angle = Math.toRadians(90);
        int centerX = panelWidth / 2 -100;
        int centerY = panelHeight / 2 -100;

        //logging the width and height of the panel



        for (TransformativeImage img : images) {
            int x = img.getX() - centerX;
            int y = img.getY() - centerY;

            double cosAngle = Math.cos(angle);
            double sinAngle = Math.sin(angle);

            int newX = (int) (x * cosAngle - y * sinAngle);
            int newY = (int) (x * sinAngle + y * cosAngle);

            newX += centerX;
            newY += centerY;

            img.setPosition(newX, newY);
            img.rotate(angle);
        }
    }

    public void resetFlipFlag() {
        this.hasFlipped = false;
    }
}