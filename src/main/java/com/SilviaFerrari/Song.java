package com.SilviaFerrari;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpatric.mp3agic.Mp3File;

public class Song {
    private String songTitle;
    private String songArtist;
    private String songDuration;
    private String songPath;
    private Mp3File mp3File;
    private double frameRatePerMilliseconds;

    @JsonCreator
    public Song(@JsonProperty("songTitle") String songTitle,
                @JsonProperty("songArtist") String songArtist,
                @JsonProperty("songLength") String songDuration,
                @JsonProperty("songPath") String songPath)
    {
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songDuration = songDuration;
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

    public String getSongDuration() {
        return songDuration;
    }

    public String getSongPath() {
        return songPath;
    }

    public double getFrameRatePerMilliseconds() {
        return frameRatePerMilliseconds;
    }

    public Mp3File getMp3File() {
        return mp3File;
    }
}