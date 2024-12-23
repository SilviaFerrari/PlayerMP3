package com.SilviaFerrari;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class YourSongsWindow extends JDialog {
    private Song selectedSong; // Canzone selezionata

    public YourSongsWindow(JFrame parent) {
        super(parent, "Your Songs", true); // modal window
        setSize(400, 300);
        setLocationRelativeTo(parent);

        DefaultListModel<Song> songListModel = new DefaultListModel<>();
        List<Song> songList = loadSongsFromFile("src/main/resources/songs.json");

        for (Song song : songList) {
            songListModel.addElement(song);
        }

        // jlist to display songs
        JList<Song> songJList = new JList<>(songListModel);
        songJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        songJList.setCellRenderer(new SongCellRenderer());
        add(new JScrollPane(songJList), BorderLayout.CENTER);

        // selection button
        JButton selectButton = new JButton("Play");
        selectButton.addActionListener(e -> {
            selectedSong = songJList.getSelectedValue();
            dispose();
        });
        add(selectButton, BorderLayout.SOUTH);
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

    // Renderer per migliorare lo stile degli elementi nella lista
    private static class SongCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Song) {
                Song song = (Song) value;
                label.setText(song.getSongTitle() + " - " + song.getSongArtist());
            }
            return label;
        }
    }
}


