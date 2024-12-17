package com.SilviaFerrari;
import javax.swing.*;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            // database initialization from json file
            SongDatabase database = new SongDatabase("songs.json");
            List<Song> songs = database.getAllSongs();
            System.out.println("Canzoni esistenti nel database:");
            for (Song song : songs) {
                System.out.println("Title: " + song.getSongTitle() +
                        ", Artist: " + song.getSongArtist() +
                        ", Length: " + song.getSongLength() +
                        ", Path: " + song.getSongPath());
            }
            /*
            // adding new song to database
            Song newSong = new Song("New Song Title", "New Artist", "3:45", "path/to/newSong.mp3");
            database.addSong(newSong);
            */
        } catch (Exception e) {
            e.printStackTrace();
        }

        // to ensure GUI is executed on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MusicPlayerGUI().setVisible(true);
            }
        });
    }
}
