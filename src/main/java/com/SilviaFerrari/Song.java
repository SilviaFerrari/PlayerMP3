package com.SilviaFerrari;

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

    public Song(String songPath) {
        this.songPath = songPath;
        try {
            AudioFile audioFile = AudioFileIO.read(new File(songPath));
            Tag tag = audioFile.getTag(); // read metadata
            if (tag != null) {
                songTitle = tag.getFirst(FieldKey.TITLE);
                songArtist = tag.getFirst(FieldKey.ARTIST);
            } else {
                songTitle = "Couldn't read title";
                songArtist = "Couldn't read artist";
            }
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

    public String getSongLength() {
        return songLength;
    }

    public String getSongPath() {
        return songPath;
    }

    // Metodo per serializzare in JSON
    public String toJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    // Metodo per deserializzare da JSON
    public static Song fromJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Song.class);
    }
}