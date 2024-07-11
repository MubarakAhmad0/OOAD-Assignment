package Components;

import Controllers.ActionHandler;
import Controllers.ChangeHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame {
    private DrawingPanel drawingPanel;
    private ImageListPanel imageListPanel;
    private ComposerPanel composerPanel;
    private String imageFolderPath = "C:\\Users\\ASUS\\Documents\\GitHub\\OOAD-Assignment\\Main\\Assets\\Animals";

    private JPanel mainPanel;

    public MainFrame() {
        super("Painter");

        createComponents();
        setupFrame();
    }

    private void createComponents() {
        // Setup toolbar
        ToolbarPanel toolbar = new ToolbarPanel(new ActionHandler(this), new ChangeHandler(this));
        this.add(toolbar, BorderLayout.SOUTH);

        // Setup mainPanel with GridBagLayout
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Left panel (image list)
        imageListPanel = new ImageListPanel(imageFolderPath);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.1;
        gbc.weighty = 1.0;
        mainPanel.add(imageListPanel, gbc);

        // Middle panel (composer panel)
        composerPanel = new ComposerPanel();
        composerPanel.setBackground(Color.LIGHT_GRAY);
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        mainPanel.add(composerPanel, gbc);

        // Right panel (drawing panel)
        drawingPanel = new DrawingPanel();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        mainPanel.add(drawingPanel, gbc);

        // Add mainPanel to the center of the frame
        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void setupFrame() {
        setSize(1600, 900);

        setExtendedState(JFrame.MAXIMIZED_BOTH); //make full screen

        setLocationRelativeTo(null); // Center frame
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Adjust frame size listener
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                updateMainPanelSize();
            }
        });
    }

    private void updateMainPanelSize() {
        int width = getWidth();
        int height = getHeight();

        // Calculate new sizes and positions for components
        int imageListWidth = (int) (width * 0.2);
        int composerWidth = (int) (width * 0.5);
        int drawingWidth = (int) (width * 0.3);

        GridBagConstraints gbc = new GridBagConstraints();

        // Update constraints for imageListPanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = imageListWidth / (double) width;
        gbc.weighty = 1.0;
        mainPanel.getComponent(0).setPreferredSize(new Dimension(imageListWidth, height));

        // Update constraints for composerPanel
        gbc.gridx = 1;
        gbc.weightx = composerWidth / (double) width;
        mainPanel.getComponent(1).setPreferredSize(new Dimension(composerWidth, height));

        // Update constraints for drawingPanel
        gbc.gridx = 2;
        gbc.weightx = drawingWidth / (double) width;
        mainPanel.getComponent(2).setPreferredSize(new Dimension(drawingWidth, height));

        // Revalidate and repaint mainPanel
        mainPanel.revalidate();
        mainPanel.repaint();
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
