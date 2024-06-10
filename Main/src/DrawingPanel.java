import java.awt.*; // Importing necessary AWT classes
import java.awt.event.*; // Importing event handling classes
import javax.swing.*; // Importing Swing classes
import java.awt.image.*; // Importing classes for image handling
import java.io.*; // Importing IO classes
import javax.imageio.*; // Importing classes for image IO

public class DrawingPanel extends JPanel implements MouseMotionListener, MouseListener {
    private BufferedImage image; // BufferedImage to store the drawing
    private Graphics2D g2d; // Graphics2D object to draw on the BufferedImage
    private Point mousePnt = new Point(); // Point to track the mouse position
    private Color drawColor = Color.BLACK; // Initial drawing color
    private int lineSize = 4; // Initial line size
    private boolean eraserMode = false; // Eraser mode flag

    // Constructor to initialize the drawing panel
    public DrawingPanel() {
        this.setPreferredSize(new Dimension(400, 600)); // Set preferred size of the panel
        this.setBackground(Color.WHITE); // Set background color of the panel
        this.addMouseMotionListener(this); // Add mouse motion listener
        this.addMouseListener(this); // Add mouse listener
        this.image = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB); // Create a buffered image
        this.g2d = (Graphics2D) image.getGraphics(); // Get the Graphics2D object from the buffered image
        clearImage(); // Clear the image to start with a blank canvas
        this.g2d.setColor(drawColor); // Set initial drawing color
    }

    // Method to set the drawing color
    public void setDrawColor(Color color) {
        this.drawColor = color;
    }

    // Method to set the line size
    public void setLineSize(int size) {
        this.lineSize = size;
    }

    // Method to set the eraser mode
    public void setEraserMode(boolean mode) {
        this.eraserMode = mode;
    }

    // Method to save the image to a file
    public void saveImage(String filePath) {
        if (!filePath.endsWith(".png")) {
            filePath += ".png"; // Ensure the file has a .png extension
        }
        try {
            ImageIO.write(image, "png", new File(filePath)); // Write the image to the file
        } catch (IOException e) {
            e.printStackTrace(); // Print stack trace if an error occurs
        }
    }

    // Method to clear the image
    public void clearImage() {
        g2d.setColor(Color.WHITE); // Set color to white
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight()); // Fill the entire image with white
        g2d.setColor(drawColor); // Reset the drawing color
        repaint(); // Repaint the panel
    }

    // Override the paintComponent method to draw the buffered image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call superclass method
        g.drawImage(image, 0, 0, null); // Draw the buffered image
    }

    // Mouse motion event handler for dragging
    @Override
    public void mouseDragged(MouseEvent e) {
        Point currentPoint = e.getPoint(); // Get the current mouse position
        g2d.setStroke(new BasicStroke(lineSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); // Set stroke properties
        if (eraserMode) {
            g2d.setColor(getBackground()); // Set color to background color in eraser mode
        } else {
            g2d.setColor(drawColor); // Set color to drawing color
        }
        g2d.drawLine(mousePnt.x, mousePnt.y, currentPoint.x, currentPoint.y); // Draw a line from the last point to the current point
        mousePnt = currentPoint; // Update the last point to the current point
        repaint(); // Repaint the panel
    }

    // Mouse motion event handler for moving
    @Override
    public void mouseMoved(MouseEvent e) {
        mousePnt = e.getPoint(); // Update the last point to the current point
    }

    // Mouse event handler for pressing
    @Override
    public void mousePressed(MouseEvent e) {
        mousePnt = e.getPoint(); // Update the last point to the current point
    }

    // Other mouse event handlers (not used but required by the interfaces)
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}