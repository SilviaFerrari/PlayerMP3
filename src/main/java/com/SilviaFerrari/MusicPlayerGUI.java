package com.SilviaFerrari;
import javax.swing.*;

public class MusicPlayerGUI extends JFrame {
    public MusicPlayerGUI() {
        super("MP3 PLayer");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //launch the app at the center of the screen
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        addGuiComponents();
    }

    private void addGuiComponents() {
        addToolBar();
    }

    private void addToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0, getWidth(), 20);
        toolBar.setFloatable(false);    // to prevent floating

        // drop down menu
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        // song menu
        JMenu songMenu = new JMenu("Song");
        menuBar.add(songMenu);
        // load item
        JMenuItem loadSong = new JMenuItem("Load song");
        songMenu.add(loadSong);

        // playlist menu
        JMenu playListMenu = new JMenu("Playlist");
        menuBar.add(playListMenu);
        // create item
        JMenuItem createPlayList = new JMenuItem("Create playlist");
        playListMenu.add(createPlayList);
        // load item
        JMenuItem loadPlayList = new JMenuItem("Load playlist");
        playListMenu.add(loadPlayList);
        // delete item
        JMenuItem deletePlayList = new JMenuItem("Delete playlist");
        playListMenu.add(deletePlayList);
        // rename item
        JMenuItem renamePlayList = new JMenuItem("Rename playlist");
        playListMenu.add(renamePlayList);

        add(toolBar);
    }
}
