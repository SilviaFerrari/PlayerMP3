package com.SilviaFerrari;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpatric.mp3agic.Mp3File;

import java.io.IOException;

public class Song {
    private String songTitle;
    private String songArtist;
    private String songDuration;
    private String songPath;
    private Mp3File mp3File;
    private double frameRatePerMilliseconds;

    //public Song() {} // void constructor for jackson

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

    /*
    @Override
    public String toString() {
        return "Song{" +
                "title='" + songTitle + '\'' +
                ", artist='" + songArtist + '\'' +
                ", duration='" + songDuration + '\'' +
                ", path='" + songPath + '\'' +
                '}';
    }
     */

/*
    // serialize in json (java object --> json)
    public String toJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    // deserialize from JSON (json --> java object)
    public static Song fromJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Song.class);
    }
 */
}