package Components;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolbarPanel extends JPanel {

    public ToolbarPanel(ActionListener actionListener, ChangeListener changeListener) {
        super(new BorderLayout());

        // Left panel
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> folderList = new JComboBox<>(new String[]{"Choose Creation","Animals", "Flowers", "Compositions", "Drawn Images"});
        folderList.addActionListener(e -> {
            String selectedFolder = (String) folderList.getSelectedItem();
            if (selectedFolder != null) {
                actionListener.actionPerformed(new ActionEvent(folderList, ActionEvent.ACTION_PERFORMED, selectedFolder));
            }
        });
        leftPanel.add(folderList);

        JButton addImage = new JButton("Add Image");
        addImage.addActionListener(actionListener);
        leftPanel.add(addImage);

        // Center panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton deleteImage = new JButton("Delete Image");
        deleteImage.addActionListener(actionListener);
        centerPanel.add(deleteImage);

        JButton saveComposition = new JButton("Save Composition");
        saveComposition.addActionListener(actionListener);
        centerPanel.add(saveComposition);

        JButton rotateComposition = new JButton("Rotate Composition");
        rotateComposition.addActionListener(actionListener);
        centerPanel.add(rotateComposition);

        // Right panel
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(new Label("Drag mouse to draw"));

        JButton colorButton = new JButton("Choose Color");
        colorButton.addActionListener(actionListener);
        rightPanel.add(colorButton);

        JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, 4);
        sizeSlider.setMajorTickSpacing(5);
        sizeSlider.setMinorTickSpacing(1);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(true);
        sizeSlider.addChangeListener(changeListener);
        rightPanel.add(new Label("Line Size:"));
        rightPanel.add(sizeSlider);

        JButton eraseAllButton = new JButton("Erase All");
        eraseAllButton.addActionListener(actionListener);
        rightPanel.add(eraseAllButton);

        JToggleButton eraserButton = new JToggleButton("Eraser");
        eraserButton.addActionListener(actionListener);
        rightPanel.add(eraserButton);

        JButton saveButton = new JButton("Save Drawing");
        saveButton.addActionListener(actionListener);
        rightPanel.add(saveButton);

        // Add sub-panels to main panel
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }
}
