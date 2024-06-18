/**
 * this file contain the implementation for the <code>Application</code> class which is the main module in the app
 *
 * @author: 
 */
package App;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Panels.LeftPanel;
import Panels.RightPanel;
import Creation.CreationImage;

/**
 * Main application class representing a painting program.
 * Integrates GUI components including drawing panels and a toolbar for user interaction.
 */
public class Application extends JFrame {
    private Application application;
    private RightPanel rPanel;
    private LeftPanel lPanel;
    private JPanel mainPanel;
    private ToolBar toolbar;
    private CreationImage entity;

    /**
     * Constructs the application with default settings and initializes components.
     */
    public Application() {
        super("Painter");
        this.rPanel = new RightPanel();
        this.lPanel = new LeftPanel();
        this.toolbar = new ToolBar();
        this.developingCanvas();
        this.initSettings();
    }

    /**
     * Initializes frame settings such as size and default close operation.
     */
    public void initSettings() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Sets up the main panel layout and adds components.
     */
    public void developingCanvas() {
        this.mainPanel = new JPanel(new GridLayout(1, 2));
        this.mainPanel.setPreferredSize(new Dimension(2 * (rPanel.getWidth()), rPanel.getHeight()));
        this.mainPanel.setBackground(Color.BLACK);

        mainPanel.add(this.lPanel, BorderLayout.WEST);
        mainPanel.add(this.rPanel, BorderLayout.EAST);

        this.add(this.mainPanel);
        this.add(this.toolbar, BorderLayout.SOUTH);
    }

    /**
     * Entry point for the application.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Application();
    }

    /**
     * Inner class representing the toolbar for user interaction.
     */
    private class ToolBar extends JPanel {

        private JButton colorBtn;
        private JButton eraseAllBtn;
        private JButton uploadBtn;
        private JButton saveBtn;
        private JButton composteBtn;

        private JToggleButton eraserButton;

        private JSlider sizeSlider;

        /**
         * Constructs the toolbar and initializes its components.
         */
        public ToolBar() {
            this.createBtn();
            this.createTBtn();
            this.createSizeSlider();
            this.ToolBarSturcure();
        }

        /**
         * Creates buttons for color selection, erasing, image upload, saving, and composing.
         */
        public void createBtn() {
            this.colorBtn = new JButton("Choose Color");
            this.colorBtn.addActionListener(new ActionHandler());

            this.eraseAllBtn = new JButton("Erase All");
            this.eraseAllBtn.addActionListener(new ActionHandler());

            this.uploadBtn = new JButton("Upload Image");
            this.uploadBtn.addActionListener(new ActionHandler());

            this.saveBtn = new JButton("Save Drawing");
            this.saveBtn.addActionListener(new ActionHandler());

            this.composteBtn = new JButton("Compose Canvas");
            this.composteBtn.addActionListener(new ActionHandler());
        }

        /**
         * Creates a toggle button for eraser mode.
         */
        public void createTBtn() {
            this.eraserButton = new JToggleButton("Eraser");
            this.eraserButton.addActionListener(new ActionHandler());
        }

        /**
         * Creates a slider for adjusting drawing line size.
         */
        public void createSizeSlider() {
            this.sizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, 4);
            this.sizeSlider.setMajorTickSpacing(5);
            this.sizeSlider.setMinorTickSpacing(1);
            this.sizeSlider.setPaintTicks(true);
            this.sizeSlider.setPaintLabels(true);
            this.sizeSlider.addChangeListener(new ActionHandler());
        }

        /**
         * Sets up the layout and adds components to the toolbar.
         */
        public void ToolBarSturcure() {
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.add(new Label("Drag mouse to draw"));
            this.add(this.colorBtn);
            this.add(new Label("Line Size:"));
            this.add(this.sizeSlider);
            this.add(this.eraseAllBtn);
            this.add(this.eraserButton);
            this.add(this.uploadBtn);
            this.add(this.saveBtn);
            this.add(this.composteBtn);
        }

        /**
         * Inner class handling action events from toolbar components.
         */
        private class ActionHandler implements ActionListener, ChangeListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (e.getActionCommand()) {
                    case "Choose Color":
                        Color newColor = JColorChooser.showDialog(application, "Change pen colour", rPanel.getBackground());
                        if (newColor != null) {
                            rPanel.setDrawColor(newColor);
                        }
                        break;
                    case "Erase All":
                        rPanel.clearImage();
                        break;
                    case "Eraser":
                        JToggleButton button = (JToggleButton) e.getSource();
                        rPanel.setEraserMode(button.isSelected());
                        break;
                    case "Upload Image":
                        JFileChooser imageChooser = new JFileChooser();
                        String imagePath = "";
                        if (imageChooser.showSaveDialog(application) == JFileChooser.APPROVE_OPTION) {
                            imagePath = imageChooser.getSelectedFile().getAbsolutePath();
                        }
                        entity = (CreationImage) new AnimalImage(); // Not clear where AnimalImage comes from; assuming CreationImage
                        lPanel.uploadImage(entity, imagePath);
                        break;
                    case "Save Drawing":
                        JFileChooser fileChooser = new JFileChooser();
                        if (fileChooser.showSaveDialog(application) == JFileChooser.APPROVE_OPTION) {
                            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                            rPanel.saveImage(filePath, rPanel.getSaveForm());
                        }
                        break;
                    case "Compose Canvas":
                        // TODO: Implement canvas composition functionality.
                        break;
                    default:
                        JOptionPane.showMessageDialog(application, "Please Perform a Valid Action!");
                        break;
                }
            }

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    rPanel.setLineSize(source.getValue());
                }
            }
        }
    }
}
