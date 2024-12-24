package com.SilviaFerrari;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpatric.mp3agic.Mp3File;

public class Song {
    private String songTitle;
    private String songArtist;
    private String songPath;

    @JsonIgnore
    private Mp3File mp3File;
    @JsonIgnore
    private double frameRatePerMilliseconds;

    // empty constructor for Jackson
    public Song() {}

    @JsonCreator
    public Song(
            @JsonProperty("songTitle") String songTitle,
            @JsonProperty("songArtist") String songArtist,
            @JsonProperty("songPath") String songPath
    ) {
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songPath = songPath;

        try{
            mp3File = new Mp3File(songPath);
            frameRatePerMilliseconds = (double) mp3File.getFrameCount() / mp3File.getLengthInMilliseconds();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongPath() {
        return songPath;
    }

    @JsonIgnore
    public String getSongDuration() {
        return convertToSongDuration();
    }

    @JsonIgnore
    private String convertToSongDuration(){
        long minutes = mp3File.getLengthInSeconds() / 60;
        long seconds = mp3File.getLengthInSeconds() % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @JsonIgnore
    public double getFrameRatePerMilliseconds() {
        return frameRatePerMilliseconds;
    }

    @JsonIgnore
    public Mp3File getMp3File() {
        return mp3File;
    }
}