package Controllers;

import Components.DrawingPanel;
import Components.MainFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ChangeHandler implements ChangeListener {
    private final MainFrame frame;

    public ChangeHandler(MainFrame frame) {
        this.frame = frame;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (!source.getValueIsAdjusting()) {
            DrawingPanel drawingPanel = frame.getDrawingPanel();
            drawingPanel.setLineSize(source.getValue());
        }
    }
}

