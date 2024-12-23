package com.SilviaFerrari;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class YourSongsWindow {
    private static Song songToPlay;

    public Song getSelectedSong() {
        return songToPlay;
    }

    public static void createAndShowGUI() {
        List<Song> songs = loadSongsFromJson();

        // main window
        JFrame frame = new JFrame("Your songs");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // window title
        JLabel titleLabel = new JLabel("Song List", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Lexend", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 120, 215));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Lista delle canzoni
        DefaultListModel<String> songListModel = new DefaultListModel<>();
        JList<String> songList = new JList<>(songListModel);
        songList.setFont(new Font("Lexend", Font.PLAIN, 14));
        songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        songList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Scroll per la lista
        JScrollPane scrollPane = new JScrollPane(songList);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Pulsante di selezione
        JButton selectButton = new JButton("Play");
        selectButton.setBackground(new Color(0, 120, 215));
        selectButton.setForeground(Color.WHITE);
        selectButton.setFont(new Font("Lexend", Font.BOLD, 14));
        selectButton.setFocusPainted(false);
        selectButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        selectButton.setEnabled(false); // disabled if nothing selected

        // select song
        selectButton.addActionListener(e -> {
            int selectedIndex = songList.getSelectedIndex();

            if (selectedIndex != -1 && songs != null) {
                Song selectedSong = songs.get(selectedIndex);
                songToPlay = new Song(selectedSong.getSongTitle(), selectedSong.getSongArtist(), selectedSong.getSongDuration(), selectedSong.getSongPath());
            }
        });

        // enabile the button if a song is selected
        songList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && songList.getSelectedIndex() != -1) {
                selectButton.setEnabled(true);
            }
        });

        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(selectButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // song loading
        if (songs != null) {
            for (Song song : songs) {
                songListModel.addElement(song.getSongTitle());
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No songs found. Please add songs to the database.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static List<Song> loadSongsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/main/resources/songs.json");

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
}
