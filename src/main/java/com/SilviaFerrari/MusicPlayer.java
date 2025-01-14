package com.SilviaFerrari;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class MusicPlayer extends PlaybackListener {
    private Song currentSong;
    private CustomSlider customSlider;
    private AdvancedPlayer advancedPlayer;

    private boolean isPaused;
    private int currentFrame;
    private int currentTimeInMilliseconds;

    // to fix isPause synchronous
    private static final Object playSignal = new Object();

    // interface to communicate with MusicPlayerGUI
    public interface SongChangeListener {
        void onSongChanged(Song newSong);
    }

    private SongChangeListener songChangeListener;

    public void setSongChangeListener(SongChangeListener listener) {
        this.songChangeListener = listener;
    }


    public void setCustomSlider(CustomSlider customSlider) {
        this.customSlider = customSlider;
    }

    public void setCurrentFrame(int frame) {
        currentFrame = frame;
    }

    public void setCurrentTimeInMilliseconds(int timeInMilliseconds) {
        currentTimeInMilliseconds = timeInMilliseconds;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void loadSong(Song song) {
        currentSong = song;
        isPaused = false;
        if(currentSong != null) {
            playCurrentSong();
        }
    }

    public void deleteSong(Song song) throws IOException {
        SongDatabase database = new SongDatabase("src/main/resources/songs.json");
        database.removeSong(song.getSongTitle());
    }

    public void pauseSong() {
        if(advancedPlayer != null) {
            isPaused = true;
            stopSong();
        }
    }

    public void stopSong() {
        if(advancedPlayer != null) {
            try {
                advancedPlayer.stop();
            } catch (Exception e) {
                System.out.println("Error stopping the song: " + e.getMessage());
            }
            advancedPlayer.close();
            advancedPlayer = null;
        }
    }

    public void resetSong(){
        isPaused = false;
        currentTimeInMilliseconds = 0;
    }

    public void playCurrentSong() {
        try{
            stopSong();

            FileInputStream fileInputStream = new FileInputStream(currentSong.getSongPath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

            advancedPlayer = new AdvancedPlayer(bufferedInputStream); // create a new player
            advancedPlayer.setPlayBackListener(this);

            // notification to the listener
            if (songChangeListener != null && currentSong != null) {
                songChangeListener.onSongChanged(currentSong);
            }

            startMusicThread(); // start music
            startPlaybackSliderThread(); // update slider track
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Song playNextSong() throws IOException {
        if (currentSong == null) {
            System.out.println("No current song to find the next one.");
            return null;
        }
        try {
            SongDatabase database = new SongDatabase("src/main/resources/songs.json");
            Song nextSong = database.getNextSong(currentSong);
            stopSong();
            resetSong();

            if (nextSong == null) {
                System.out.println("No song available.");
                return null;
            }
            loadSong(nextSong);
            return nextSong;
        }
        catch (Exception e) {
            System.err.println("Error while switching to the next song: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Song playPreviousSong() throws IOException {
        if (currentSong == null) {
            System.out.println("No current song to find the previous one.");
            return null;
        }
        try {
            SongDatabase database = new SongDatabase("src/main/resources/songs.json");
            Song previuosSong = database.getPreviousSong(currentSong);
            stopSong();
            resetSong();

            if (previuosSong == null) {
                System.out.println("No song available.");
                return null;
            }
            loadSong(previuosSong);
            return previuosSong;
        }
        catch (Exception e) {
            System.err.println("Error while switching to the previous song: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void startMusicThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(isPaused) {
                        synchronized (playSignal) {
                            isPaused = false;
                            playSignal.notify();
                        }
                        advancedPlayer.play(currentFrame, Integer.MAX_VALUE);
                    }
                    else{
                        advancedPlayer.play();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // thread that handle the slider updating
    private void startPlaybackSliderThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(isPaused) {
                    try{
                        synchronized (playSignal) {
                            playSignal.wait();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                while(!isPaused) {
                    try {
                        // update cursor position
                        currentTimeInMilliseconds += 10;
                        int calculatedFrame = (int) ((double) currentTimeInMilliseconds * currentSong.getFrameRatePerMilliseconds());
                        customSlider.setSliderValue(calculatedFrame);

                        // update display time
                        int minutes = currentTimeInMilliseconds / 60000;
                        int seconds = (currentTimeInMilliseconds / 1000) % 60;
                        customSlider.updateTimeTrack(minutes, seconds);

                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void playbackStarted(PlaybackEvent evt) {
        // called in the end/pause of the song
        System.out.println("Music has started.");
    }

    @Override
    public void playbackFinished(PlaybackEvent evt) {
        // called in the beginning of the song
        if(isPaused) {
            currentFrame += (int) ((double) evt.getFrame() * currentSong.getFrameRatePerMilliseconds());
            System.out.println("Paused at @" + currentFrame);
        } else{
            System.out.println("Song has finished.");
            try {
                if (currentSong != null) {
                    Song nextSong = playNextSong();
                    if (nextSong == null) {
                        System.out.println("No more songs to play.");
                    }
                }
            } catch (IOException e) {
                System.err.println("Failed to play next song: " + e.getMessage());
            }
        }
    }
}