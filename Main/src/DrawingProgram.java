import java.awt.*; // Importing necessary AWT classes
import java.awt.event.*; // Importing event handling classes
import javax.swing.*; // Importing Swing classes
import javax.swing.event.*; // Importing change event classes

public class DrawingProgram extends JFrame implements ActionListener, ChangeListener {
    private DrawingPanel drawingPanel; // DrawingPanel instance for the right panel

    // Constructor to initialize the main application frame
    public DrawingProgram() {
        super("Painter"); // Set the title of the frame

        // Setup toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Create a toolbar with left-aligned flow layout
        toolbar.add(new Label("Drag mouse to draw")); // Add a label to the toolbar

        JButton colorButton = new JButton("Choose Color"); // Create a button to choose color
        colorButton.addActionListener(this); // Add action listener to the button
        toolbar.add(colorButton); // Add the button to the toolbar

        JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, 4); // Create a slider for line size
        sizeSlider.setMajorTickSpacing(5); // Set major tick spacing
        sizeSlider.setMinorTickSpacing(1); // Set minor tick spacing
        sizeSlider.setPaintTicks(true); // Enable painting ticks
        sizeSlider.setPaintLabels(true); // Enable painting labels
        sizeSlider.addChangeListener(this); // Add change listener to the slider
        toolbar.add(new Label("Line Size:")); // Add a label for the slider
        toolbar.add(sizeSlider); // Add the slider to the toolbar

        JButton eraseAllButton = new JButton("Erase All"); // Create a button to erase all
        eraseAllButton.addActionListener(this); // Add action listener to the button
        toolbar.add(eraseAllButton); // Add the button to the toolbar

        JToggleButton eraserButton = new JToggleButton("Eraser"); // Create a toggle button for eraser mode
        eraserButton.addActionListener(this); // Add action listener to the button
        toolbar.add(eraserButton); // Add the button to the toolbar

        JButton saveButton = new JButton("Save Drawing"); // Create a button to save the drawing
        saveButton.addActionListener(this); // Add action listener to the button
        toolbar.add(saveButton); // Add the button to the toolbar

        this.add(toolbar, BorderLayout.SOUTH); // Add the toolbar to the south of the frame

        // Setup dual-panel layout
        JPanel mainPanel = new JPanel(new GridLayout(1, 2)); // Create a main panel with a grid layout (1 row, 2 columns)

        JPanel leftPanel = new JPanel(); // Create a placeholder panel for composing images
        leftPanel.setBackground(Color.LIGHT_GRAY); // Set the background color of the left panel
        mainPanel.add(leftPanel); // Add the left panel to the main panel

        drawingPanel = new DrawingPanel(); // Create an instance of DrawingPanel for the right panel
        mainPanel.add(drawingPanel); // Add the drawing panel to the main panel

        this.add(mainPanel, BorderLayout.CENTER); // Add the main panel to the center of the frame

        setSize(850, 650); // Set the size of the frame
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
        } else if (e.getActionCommand().equals("Erase All")) { // Handle erase all action
            drawingPanel.clearImage(); // Clear the image in the drawing panel
        } else if (e.getActionCommand().equals("Eraser")) { // Handle eraser mode toggle
            JToggleButton button = (JToggleButton) e.getSource();
            drawingPanel.setEraserMode(button.isSelected()); // Set the eraser mode
        } else if (e.getActionCommand().equals("Save Drawing")) { // Handle save drawing action
            JFileChooser fileChooser = new JFileChooser(); // Create a file chooser
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath(); // Get the selected file path
                drawingPanel.saveImage(filePath); // Save the image to the selected file
            }
        }
    }

    // Change event handler for the slider
    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            drawingPanel.setLineSize(source.getValue()); // Set the line size in the drawing panel
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        new DrawingProgram(); // Create an instance of DrawingProgram
    }
}