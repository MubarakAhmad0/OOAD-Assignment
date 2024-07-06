package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class ImageListPanel extends JPanel {
    private static final int ICON_SIZE = 228; // Define the size of the icons
    private final DefaultListModel<ImageIcon> imageListModel;
    private final JList<ImageIcon> imageList;
    private final List<String> animalNames = List.of("cat", "dog", "lion", "tiger", "elephant"); // Example animal names
    private final List<String> flowerNames = List.of("rose", "tulip", "daisy", "sunflower", "orchid"); // Example flower names


    public ImageListPanel(String folderPath) {
        setLayout(new BorderLayout());
        imageListModel = new DefaultListModel<>();
        loadImagesFromFolder(folderPath);

        imageList = new JList<>(imageListModel);
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

    void loadImagesFromFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] validFiles = validateFolder(folder);
        if (validFiles != null) {
            for (File file : validFiles) {
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

    private File[] validateFolder(File folder) {
        File[] files = filterFilesByType(folder, "webp", "png", "jpg", "jpeg");
        if (files == null) return new File[0];
        switch (folder.getName()) {
            case "Animals":
                files = filterFilesByName(files, animalNames);
                break;
            case "Flowers":
                files = filterFilesByName(files, flowerNames);
                break;
            default:
                break;
        }
        return files;
    }

    private File[] filterFilesByType(File folder, String... types) {
        return folder.listFiles((dir, name) -> {
            for (String type : types) {
                if (name.toLowerCase().endsWith(type)) {
                    return true;
                }
            }
            return false;
        });
    }

    private File[] filterFilesByName(File[] files, List<String> validNames) {
        List<File> filteredFiles = new ArrayList<>();
        for (File file : files) {
            String fileName = file.getName().toLowerCase();
            for (String name : validNames) {
                if (fileName.contains(name.toLowerCase())) {
                    filteredFiles.add(file);
                    break;
                }
            }
        }
        return filteredFiles.toArray(new File[0]);
    }



    private BufferedImage resizeImage(BufferedImage originalImage) {
        BufferedImage resizedImage = new BufferedImage(ImageListPanel.ICON_SIZE, ImageListPanel.ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, ImageListPanel.ICON_SIZE, ImageListPanel.ICON_SIZE, null);
        g.dispose();
        return resizedImage;
    }


    public ImageIcon getSelectedImage() {
        return imageList.getSelectedValue();
    }
}

