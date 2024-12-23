package com.SilviaFerrari;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongDatabase {
    private final String jsonPath;
    private final ObjectMapper objectMapper;
    private List<Song> songs;

    public SongDatabase(String jsonPath) throws IOException {
        this.jsonPath = jsonPath;
        this.objectMapper = new ObjectMapper();
        this.songs = loadSongs();
    }

    // load songs from json
    private List<Song> loadSongs() throws IOException {
        File file = new File(jsonPath);
        if (!file.exists()) {
            return new ArrayList<>(); // empty array
        }
        return objectMapper.readValue(file, new TypeReference<>() {});
    }

    // save on json
    private void saveSongs() throws IOException {
        objectMapper.writeValue(new File(jsonPath), songs);
    }

    public void addSong(Song song) throws IOException {
        songs.add(song); // add song to list
        saveSongs();
    }

    public List<Song> getSongs() {
        return songs;
    }
}