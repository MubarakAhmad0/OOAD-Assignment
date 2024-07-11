package Controllers;

import Components.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionHandler implements ActionListener {
    private final MainFrame frame;

    public ActionHandler(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        DrawingPanel drawingPanel = frame.getDrawingPanel();
        ComposerPanel composerPanel = frame.getComposerPanel();
        ImageListPanel imageListPanel = frame.getImageListPanel();

        switch (actionCommand) {
            case "Choose Color" -> {
                Color newColor = JColorChooser.showDialog(frame, "Change pen colour", drawingPanel.getBackground());
                if (newColor != null) {
                    drawingPanel.setDrawColor(newColor);
                }
            }
            case "Erase All" -> drawingPanel.clearImage();

            case "Eraser" -> {
                JToggleButton button = (JToggleButton) e.getSource();
                drawingPanel.setEraserMode(button.isSelected());
            }
            case "Save Drawing" -> {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    drawingPanel.saveImage(filePath);
                }
            }
            case "Animals" -> {
                frame.setImageFolderPath("C:\\Users\\ASUS\\Documents\\GitHub\\OOAD-Assignment\\Main\\Assets\\Animals");
                frame.updateImageListPanel();
            }
            case "Flowers" -> {
                frame.setImageFolderPath("C:\\Users\\ASUS\\Documents\\GitHub\\OOAD-Assignment\\Main\\Assets\\Flowers");
                frame.updateImageListPanel();
            }
            case "Compositions" -> {
                frame.setImageFolderPath("C:\\Users\\ASUS\\Documents\\GitHub\\OOAD-Assignment\\Main\\Assets\\Compositions");
                frame.updateImageListPanel();
            }
            case "Drawn Images" -> {
                frame.setImageFolderPath("C:\\Users\\ASUS\\Documents\\GitHub\\OOAD-Assignment\\Main\\Assets\\Drawn Images");
                frame.updateImageListPanel();
            }
            case "Add Image" -> {
                ImageIcon selectedImage = imageListPanel.getSelectedImage();
                if (selectedImage != null) {
                    composerPanel.addImage(selectedImage);
                }
            }
            case "Delete Image" -> composerPanel.deleteSelectedImage();

            case "Rotate Composition" -> composerPanel.rotateComposition();

            case "Save Composition" -> {
                composerPanel.saveComposition();
                frame.updateImageListPanel();
            }
        }
    }
}

