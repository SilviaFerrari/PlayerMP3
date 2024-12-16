package com.SilviaFerrari;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SongDatabase {
    private final File databaseFile;
    private final ObjectMapper mapper;

    public SongDatabase(String fileName) {
        // loading json file
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
        this.databaseFile = new File(classLoader.getResource(fileName).getFile());
        this.mapper = new ObjectMapper();
    }

    // add new song to database
    public void addSong(Song song) throws IOException {
        List<Song> songs = getAllSongs();
        songs.add(song);
        saveSongs(songs);
    }

    // read all songs from database
    public List<Song> getAllSongs() throws IOException {
        if (!databaseFile.exists()) {
            return new ArrayList<>();
        }
        return mapper.readValue(databaseFile, new TypeReference<List<Song>>() {});
    }

    // save song list on json file
    private void saveSongs(List<Song> songs) throws IOException {
        mapper.writeValue(databaseFile, songs);
    }
}