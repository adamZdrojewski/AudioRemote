package com.adam_z.AudioRemote;

import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Objects;

@Service
public class MainFrame extends JFrame {
    private final String directoryPath;
    private final Color backgroundColor = new Color(7, 7, 7);
    private final Color textColor = new Color(255, 250, 250);

    public MainFrame() throws IOException {
        // Frame setup
        this.setTitle("Audio Remote");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setIconImage(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("img/icon.png"))).getImage());

        // Get directory path
        directoryPath = setDirectoryPath();

        // Make label panel
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BorderLayout());
        labelPanel.setBorder(new EmptyBorder(65, 0, 65, 0));
        labelPanel.setBackground(backgroundColor);
        labelPanel.setOpaque(true);

        // Make labels
        JLabel title = new JLabel("Audio Remote");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("sans-serif", Font.BOLD, 30));
        title.setForeground(textColor);
        JLabel subTitle = new JLabel("Hosting on port 3000");
        subTitle.setHorizontalAlignment(JLabel.CENTER);
        subTitle.setFont(new Font("sans-serif", Font.PLAIN, 25));
        subTitle.setForeground(textColor);
        String[] directoryPathArr = directoryPath.split("/");
        JLabel folderName = new JLabel("Folder: " + directoryPathArr[directoryPathArr.length - 1]);
        folderName.setHorizontalAlignment(JLabel.CENTER);
        folderName.setFont(new Font("sans-serif", Font.ITALIC, 20));
        folderName.setForeground(textColor);

        labelPanel.add(title, BorderLayout.NORTH);
        labelPanel.add(subTitle, BorderLayout.CENTER);
        labelPanel.add(folderName, BorderLayout.SOUTH);
        this.add(labelPanel, BorderLayout.CENTER);

        // Show frame
        this.setVisible(true);
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public File[] getFiles() {
        File dir = new File(getDirectoryPath());
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".wav");
            }
        });
        return files;
    }

    private String setDirectoryPath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int response = fileChooser.showOpenDialog(null);

        if(response == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            System.exit(1);
            return "";
        }
    }
}
