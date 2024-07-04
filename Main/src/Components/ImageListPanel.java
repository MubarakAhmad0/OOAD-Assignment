package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageListPanel extends JPanel {
    private static final int ICON_SIZE = 228; // Define the size of the icons
    private final DefaultListModel<ImageIcon> imageListModel;

    public ImageListPanel(String folderPath) {
        setLayout(new BorderLayout());
        imageListModel = new DefaultListModel<>();
        loadImagesFromFolder(folderPath);

        JList<ImageIcon> imageList = new JList<>(imageListModel);
        imageList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(value);
            label.setOpaque(true);
            if (isSelected) {
                label.setBackground(list.getSelectionBackground());
                label.setForeground(list.getSelectionForeground());
            } else {
                label.setBackground(list.getBackground());
                label.setForeground(list.getForeground());
            }
            return label;
        });

        JScrollPane scrollPane = new JScrollPane(imageList);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void loadImagesFromFolder(String folderPath) {
        imageListModel.clear();

        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".webp") || name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg"));
            if (files != null) {
                for (File file : files) {
                    try {
                        BufferedImage img = ImageIO.read(file);
                        if (img != null) {
                            BufferedImage resizedImg = resizeImage(img);
                            imageListModel.addElement(new ImageIcon(resizedImg));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage) {
        BufferedImage resizedImage = new BufferedImage(ImageListPanel.ICON_SIZE, ImageListPanel.ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, ImageListPanel.ICON_SIZE, ImageListPanel.ICON_SIZE, null);
        g.dispose();
        return resizedImage;
    }
}
