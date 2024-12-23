package com.SilviaFerrari;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class YourSongsWindow extends JDialog {
    private Song selectedSong; // Canzone selezionata

    public YourSongsWindow(JFrame parent) {
        super(parent, "Your Songs", true); // Finestra modale
        setSize(450, 400);
        setLocationRelativeTo(parent);

        // Stile della finestra
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(45, 45, 45));

        // window title
        JLabel titleLabel = new JLabel("Select a Song to Play");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // list model
        DefaultListModel<Song> songListModel = new DefaultListModel<>();
        List<Song> songList = loadSongsFromFile("src/main/resources/songs.json");
        for (Song song : songList) {
            songListModel.addElement(song);
        }

        // JList songs
        JList<Song> songJList = new JList<>(songListModel);
        songJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        songJList.setCellRenderer(new SongCellRenderer());
        songJList.setBackground(new Color(55, 55, 55));
        songJList.setForeground(Color.WHITE);
        songJList.setFont(new Font("Arial", Font.PLAIN, 14));
        songJList.setFixedCellHeight(40);

        JScrollPane scrollPane = new JScrollPane(songJList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // selection button
        JButton selectButton = new JButton("Play");
        selectButton.setFocusPainted(false);
        selectButton.setBackground(new Color(0, 153, 255));
        selectButton.setForeground(Color.WHITE);
        selectButton.setFont(new Font("Arial", Font.BOLD, 14));
        selectButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        selectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // hover effect
        selectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                selectButton.setBackground(new Color(0, 123, 215));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                selectButton.setBackground(new Color(0, 153, 255));
            }
        });

        selectButton.addActionListener(e -> {
            selectedSong = songJList.getSelectedValue();
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(45, 45, 45));
        buttonPanel.add(selectButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public Song getSelectedSong() {
        return selectedSong;
    }

    private List<Song> loadSongsFromFile(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(filePath);
        try {
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<List<Song>>() {});
            } else {
                System.err.println("No songs file found.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading songs from JSON.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null;
    }

    // list element style
    private static class SongCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Song) {
                Song song = (Song) value;
                label.setText("<html><b>" + song.getSongTitle() + "</b> <br><i>" + song.getSongArtist() + "</i></html>");
                label.setFont(new Font("Arial", Font.PLAIN, 14));
            }
            label.setOpaque(true);
            label.setBackground(isSelected ? new Color(0, 153, 255) : new Color(55, 55, 55));
            label.setForeground(isSelected ? Color.WHITE : Color.LIGHT_GRAY);
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            return label;
        }
    }
}