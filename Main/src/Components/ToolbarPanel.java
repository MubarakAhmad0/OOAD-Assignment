package Components;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import MainApplication.*; // Importing classes from the MainApplication package

public class ToolbarPanel extends JPanel {

    public ToolbarPanel(ActionListener actionListener, ChangeListener changeListener) {
        super(new FlowLayout(FlowLayout.LEFT));

        add(Box.createRigidArea(new Dimension(75, 0))); // Add horizontal space

        JComboBox<String> folderList = new JComboBox<>(new String[]{"Animals", "Flowers", "Compositions"});
        folderList.addActionListener(e -> {
            String selectedFolder = (String) folderList.getSelectedItem();
            if (selectedFolder != null) {
                actionListener.actionPerformed(new ActionEvent(folderList, ActionEvent.ACTION_PERFORMED, selectedFolder));
            }
        });
        add(folderList);


        JButton addImage = new JButton("Add Image");
        addImage.addActionListener(actionListener);
        add(addImage);

        add(Box.createRigidArea(new Dimension(300, 0))); // Add horizontal space

        JButton deleteImage = new JButton("Delete Image");
        deleteImage.addActionListener(actionListener);
        add(deleteImage);

        JButton saveComposition = new JButton("Save Composition");
        saveComposition.addActionListener(actionListener);
        add(saveComposition);

        add(Box.createRigidArea(new Dimension(330, 0))); // Add horizontal space

        add(new Label("Drag mouse to draw"));

        JButton colorButton = new JButton("Choose Color");
        colorButton.addActionListener(actionListener);
        add(colorButton);

        JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, 4);
        sizeSlider.setMajorTickSpacing(5);
        sizeSlider.setMinorTickSpacing(1);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(true);
        sizeSlider.addChangeListener(changeListener);
        add(new Label("Line Size:"));
        add(sizeSlider);

        JButton eraseAllButton = new JButton("Erase All");
        eraseAllButton.addActionListener(actionListener);
        add(eraseAllButton);

        JToggleButton eraserButton = new JToggleButton("Eraser");
        eraserButton.addActionListener(actionListener);
        add(eraserButton);

        JButton saveButton = new JButton("Save Drawing");
        saveButton.addActionListener(actionListener);
        add(saveButton);
    }
}
