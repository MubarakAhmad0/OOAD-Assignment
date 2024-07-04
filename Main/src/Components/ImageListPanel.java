package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import MainApplication.*; // Importing classes from the MainApplication package

public class ImageListPanel extends JPanel {
    private static final int ICON_SIZE = 228; // Define the size of the icons
    private DefaultListModel<ImageIcon> imageListModel;
    private JList<ImageIcon> imageList;

    public ImageListPanel(String folderPath) {
        setLayout(new BorderLayout());
        imageListModel = new DefaultListModel<>();
        loadImagesFromFolder(folderPath);

        imageList = new JList<>(imageListModel);
        imageList.setCellRenderer(new ListCellRenderer<ImageIcon>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends ImageIcon> list, ImageIcon value, int index, boolean isSelected, boolean cellHasFocus) {
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
            }
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
                            BufferedImage resizedImg = resizeImage(img, ICON_SIZE, ICON_SIZE);
                            imageListModel.addElement(new ImageIcon(resizedImg));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
}
