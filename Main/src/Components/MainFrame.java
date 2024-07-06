package Components;

import Controllers.ActionHandler;
import Controllers.ChangeHandler;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final DrawingPanel drawingPanel; // Components.DrawingPanel instance for the right panel
    private ImageListPanel imageListPanel; // Components.ImageListPanel instance for the left panel
    private final ComposerPanel composerPanel;
    private String imageFolderPath = "C:\\Users\\ASUS\\Documents\\GitHub\\OOAD-Assignment\\Main\\Assets\\Animals"; // Path to the image folder

    // Constructor to initialize the main application frame
    public MainFrame() {
        super("Painter"); // Set the title of the frame

        // Setup toolbar
        ToolbarPanel toolbar = new ToolbarPanel(new ActionHandler(this), new ChangeHandler(this)); // Create an instance of Components.ToolbarPanel
        this.add(toolbar, BorderLayout.SOUTH); // Add the toolbar to the south of the frame

        // Set up the main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Create an instance of Components.ImageListPanel
        imageListPanel = new ImageListPanel(imageFolderPath);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.2; // Weight for the left panel (imageListPanel)
        gbc.weighty = 1.0;

        // Setup the middle panel (placeholder)
        composerPanel = new ComposerPanel(); // Create an instance of Components.ComposerPanel
        composerPanel.setBackground(Color.LIGHT_GRAY);

        gbc.gridx = 1;
        gbc.weightx = 0.4; // Weight for the middle panel (composerPanel)
        mainPanel.add(composerPanel, gbc);

        // Set up the right panel (drawing panel)
        drawingPanel = new DrawingPanel();

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.4; // Weight for the right panel (drawingPanel)
        mainPanel.add(drawingPanel, gbc);

        this.add(mainPanel, BorderLayout.CENTER); // Add the main panel to the center of the frame

        // set size of the frame to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set the frame to full screen
        setVisible(true); // Make the frame visible
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Set the default close operation
    }

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

    public ComposerPanel getComposerPanel() {
        return composerPanel;
    }

    public ImageListPanel getImageListPanel() {
        return imageListPanel;
    }

    public void setImageFolderPath(String imageFolderPath) {
        this.imageFolderPath = imageFolderPath;
    }

    public void updateImageListPanel() {
        this.remove(imageListPanel);
        imageListPanel = new ImageListPanel(imageFolderPath);
        this.add(imageListPanel, BorderLayout.WEST);
        this.revalidate();
        this.repaint();
    }
}
