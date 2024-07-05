package Components;

import java.awt.*; // Importing necessary AWT classes
import java.awt.event.*; // Importing event handling classes
import javax.swing.*; // Importing Swing classes
import javax.swing.event.*; // Importing change event classes

public class Frame extends JFrame implements ActionListener, ChangeListener {
    private final DrawingPanel drawingPanel; // Components.DrawingPanel instance for the right panel
    private ImageListPanel imageListPanel; // Components.ImageListPanel instance for the left panel
    private final ComposerPanel composerPanel;
    private String imageFolderPath = "C:\\Users\\ASUS\\Documents\\GitHub\\OOAD-Assignment\\Main\\Assets\\Animals"; // Path to the image folder

    // Constructor to initialize the main application frame
    public Frame() {
        super("Painter"); // Set the title of the frame

        // Setup toolbar
        ToolbarPanel toolbar = new ToolbarPanel(this, this); // Create an instance of Components.ToolbarPanel
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
        //mainPanel.add(imageListPanel, gbc);

        // Setup the middle panel (placeholder)
        composerPanel = new ComposerPanel(); // Create an instance of Components.ComposerPanel
        composerPanel.setBackground(Color.LIGHT_GRAY);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.4; // Weight for the middle panel (leftPanel)
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

    // Action event handler
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Choose Color")) { // Handle color selection
            Color newColor = JColorChooser.showDialog(this, "Change pen colour", drawingPanel.getBackground()); // Show color chooser dialog
            if (newColor != null) {
                drawingPanel.setDrawColor(newColor); // Set the new drawing color
            }
        }

        else if (e.getActionCommand().equals("Erase All")) { // Handle erase all action
            drawingPanel.clearImage(); // Clear the image in the drawing panel
        }

        else if (e.getActionCommand().equals("Eraser")) { // Handle eraser mode toggle
            JToggleButton button = (JToggleButton) e.getSource();
            drawingPanel.setEraserMode(button.isSelected()); // Set the eraser mode
        }

        else if (e.getActionCommand().equals("Save Drawing")) { // Handle save drawing action
            JFileChooser fileChooser = new JFileChooser(); // Create a file chooser
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath(); // Get the selected file path
                drawingPanel.saveImage(filePath); // Save the image to the selected file
            }
        }

       //receive the action command from the combo box
        else if (e.getActionCommand().equals("Animals")) { // Handle image list selection
            imageFolderPath = "C:\\Users\\ASUS\\Documents\\GitHub\\OOAD-Assignment\\Main\\Assets\\Animals"; // Set the image folder path
            updateImageListPanel();
        }

        else if (e.getActionCommand().equals("Flowers")) { // Handle image list selection
            imageFolderPath = "C:\\Users\\ASUS\\Documents\\GitHub\\OOAD-Assignment\\Main\\Assets\\Flowers"; // Set the image folder path
            updateImageListPanel();
        }

        else if (e.getActionCommand().equals("Compositions")) { // Handle image list selection
            imageFolderPath = "C:\\Users\\ASUS\\Documents\\GitHub\\OOAD-Assignment\\Main\\Assets\\Compositions"; // Set the image folder path
            updateImageListPanel(); // Update the image list panel
        }
        else if (e.getActionCommand().equals("Add Image")) { // deploy selected image to the left panel
            ImageIcon selectedImage = imageListPanel.getSelectedImage(); // Get the selected image from the image list panel
            if (selectedImage != null) {
                composerPanel.addImage(selectedImage); // Add the selected image to the drawing panel
            }
        }
        else if (e.getActionCommand().equals("Delete Image")) { // Handle delete image action
            composerPanel.deleteSelectedImage(); // Delete the selected image from the drawing panel

        }
        else if (e.getActionCommand().equals("Save Composition")) {
            composerPanel.saveComposition(); // Save the composition to a file
        }
    }

    private void updateImageListPanel() {
        this.remove(imageListPanel);
        imageListPanel = new ImageListPanel(imageFolderPath);
        this.add(imageListPanel, BorderLayout.WEST);
        this.revalidate();
        this.repaint();
    }



    // Change event handler for the slider
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            drawingPanel.setLineSize(source.getValue()); // Set the line size in the drawing panel
        }
    }


}
