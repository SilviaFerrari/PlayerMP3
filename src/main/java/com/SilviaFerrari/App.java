package com.SilviaFerrari;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // to ensure GUI is executed on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //new MusicPlayerGUI().setVisible(true);
                Song song = new Song("src/assets/Panettone.pm3");
                System.out.println(song.getSongTitle());
                System.out.println(song.getSongArtist());
            }
        });
    }
}
