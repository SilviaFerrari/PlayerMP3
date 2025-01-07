package com.SilviaFerrari;
import javax.swing.*;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            // database initialization from json file
            SongDatabase database = new SongDatabase("src/main/resources/songs.json");
            List<Song> songs = database.getSongs();
            System.out.println("Library:");
            for (Song song : songs) {
                System.out.println("Title: " + song.getSongTitle() +
                        ", Artist: " + song.getSongArtist() +
                        ", Duration: " + song.getSongDuration() +
                        ", Path: " + song.getSongPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // to ensure GUI is executed on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new MusicPlayerGUI().setVisible(true));
    }
}
