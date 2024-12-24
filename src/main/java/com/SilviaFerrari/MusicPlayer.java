package com.SilviaFerrari;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class MusicPlayer extends PlaybackListener {
    private Song currentSong;
    private AdvancedPlayer advancedPlayer;
    private boolean isPaused = false;
    private int currentFrame;

    public MusicPlayer() {} // constructor

    public void loadSong(Song song) {
        currentSong = song;
        if(currentSong != null) {
            playCurrentSong();
        }
    }

    public void pauseSong() {
        if(advancedPlayer != null) {
            isPaused = true;
            stopSong();
        }
    }

    public void stopSong() {
        if(advancedPlayer != null) {
            advancedPlayer.stop();
            advancedPlayer.close();
            advancedPlayer = null;
        }
    }

    public void resetSong(){
        isPaused = false;
    }

    public void playCurrentSong() {
        try{
            // mp3 data reading
            stopSong();
            FileInputStream fileInputStream = new FileInputStream(currentSong.getSongPath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            advancedPlayer = new AdvancedPlayer(bufferedInputStream); // create a new player
            advancedPlayer.setPlayBackListener(this);

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
                    if(isPaused) {
                        advancedPlayer.play(currentFrame, Integer.MAX_VALUE);
                    } else{
                        advancedPlayer.play();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void playbackStarted(PlaybackEvent evt) {
        // called in the end/pause of the song
        System.out.println("playback started");
    }

    @Override
    public void playbackFinished(PlaybackEvent evt) {
        // called in the beginning of the song
        System.out.println("playback finished");
        if(isPaused) {
            currentFrame += (int) ((double) evt.getFrame() * currentSong.getFrameRatePerMilliseconds());
            System.out.println("Stopped at @" + currentFrame);
        }
    }
}