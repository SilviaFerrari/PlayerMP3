package com.SilviaFerrari;

import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class MusicPlayer {
    private Song currentSong;

    private AdvancedPlayer advancedPlayer;

    public MusicPlayer() {} // constructor

    public void loadSong(Song song) {
        currentSong = song;
        if(currentSong != null) {
            playCurrentSong();
        }
    }

    public void playCurrentSong() {
        try{
            // mp3 data reading
            FileInputStream fileInputStream = new FileInputStream(currentSong.getSongPath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            advancedPlayer = new AdvancedPlayer(bufferedInputStream); // create a new player
            startMusicThread(); // start music
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startMusicThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    advancedPlayer.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}