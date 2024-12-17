package com.SilviaFerrari;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SongLoadingWindow {
    private static String selectedPath;

    public SongLoadingWindow(String selectedPath) {
        this.selectedPath = selectedPath;
    }

   private static void addComponent(JPanel panel, JLabel label, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    static void createAndShowGUI() {
        // main window (centered)
        JFrame frame = new JFrame("Choose an MP3 file");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // main panel style
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // component space
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // title field style
        JLabel titleLabel = new JLabel("Titolo:");
        JTextField titleField = new JTextField(20);
        addComponent(panel, titleLabel, titleField, gbc, 0);

        // artist field style
        JLabel artistLabel = new JLabel("Artista:");
        JTextField artistField = new JTextField(20);
        addComponent(panel, artistLabel, artistField, gbc, 1);

        // duration field style
        JLabel durationLabel = new JLabel("Durata:");
        JTextField durationField = new JTextField(20);
        addComponent(panel, durationLabel, durationField, gbc, 2);

        // enter button style
        JButton submitButton = new JButton("Add song");
        submitButton.setFocusPainted(false);
        submitButton.setBackground(new Color(0, 120, 215));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(submitButton, gbc);

        // enter button action
        submitButton.addActionListener(e -> {
            String title = titleField.getText();
            String artist = artistField.getText();
            String duration = durationField.getText();

            // verify empty filed
            if (title.isEmpty() || artist.isEmpty() || duration.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "You have to compile each filed.", "Error:", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // create a new object song and add to file json
            try {
                Song newSong = new Song(title, artist, duration, selectedPath);
                SongDatabase database = new SongDatabase("songs.json");
                database.addSong(newSong);

                JOptionPane.showMessageDialog(frame, "Song added succesfull.", "Done!", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("New song added: " + newSong);

                // clear fields
                titleField.setText("");
                artistField.setText("");
                durationField.setText("");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Database saving gone wrong.", "Error:", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
