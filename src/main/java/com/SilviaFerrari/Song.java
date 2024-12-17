package com.SilviaFerrari;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;

import java.io.File;
import java.io.IOException;

public class Song {
    private String songTitle;
    private String songArtist;
    private String songLength;
    private String songPath;

    public Song() {} // void constructor for jackson

    @JsonCreator
    public Song(@JsonProperty("songTitle") String songTitle,
                @JsonProperty("songArtist") String songArtist,
                @JsonProperty("songLength") String songLength,
                @JsonProperty("songPath") String songPath)
    {
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songLength = songLength;
        this.songPath = songPath;
    }

    /*
    public Song(String songTitle, String songArtist, String songLength, String songPath)
    {
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songLength = songLength;
        this.songPath = songPath;
    }

     */

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongLength() {
        return songLength;
    }

    public String getSongPath() {
        return songPath;
    }

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