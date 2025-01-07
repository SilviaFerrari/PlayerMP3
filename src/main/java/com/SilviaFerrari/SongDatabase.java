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

    public List<Song> getSongs() {
        return songs;
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

    public void removeSong(String songTitle) {
        try{
            boolean removed = songs.removeIf(song -> song.getSongTitle().equalsIgnoreCase(songTitle));
            if (removed) {
                saveSongs();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private int getCurrentIndex(Song currentSong) {
        int currentIndex = -1;
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getSongPath().equals(currentSong.getSongPath())) {
                currentIndex = i;
                break;
            }
        }
        return currentIndex;
    }

    public Song getNextSong(Song currentSong) {
        if (songs.isEmpty()) {
            return null; // json is empty
        }

        // if the songs doesn't exist, take the first
        int currentIndex = getCurrentIndex(currentSong);
        if (currentIndex == -1) {
            return songs.getFirst();
        }

        // next index calculation
        int nextIndex = (currentIndex + 1) % songs.size();
        return songs.get(nextIndex);
    }

    public Song getPreviousSong(Song currentSong) {
        if (songs.isEmpty()) {
            return null;
        }

        // if the songs doesn't exist, take the last
        int currentIndex = getCurrentIndex(currentSong);
        if (currentIndex == -1) {
            return songs.getLast();
        }

        int previousIndex = (currentIndex - 1 + songs.size()) % songs.size();
        return songs.get(previousIndex);
    }
}