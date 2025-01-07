package com.SilviaFerrari;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.sqrt;

public class MusicPlayerGUI extends JFrame {
    // color configuration
    public static final Color FRAME_COLOR = new Color(22, 21, 26);
    public static final Color TEXT_COLOR = new Color(250,250,250);

    private JLabel songTitle, songArtist;
    private JPanel navigationButtons;
    private CustomSlider customSlider;

    private final JFileChooser fileChooser;
    private File selectedFile;
    private Song songToPlay;
    private Song songToDelete;

    MusicPlayer musicPlayer = new MusicPlayer();

    public MusicPlayerGUI() {
        super("MP3 PLayer");
        int screenHeight = 700;
        setSize((int) ((double)screenHeight/sqrt(2)), screenHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //launch the app at the center of the screen
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // color setting
        getContentPane().setBackground(FRAME_COLOR);

        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("src/assets/mp3")); // default path
        fileChooser.setFileFilter(new FileNameExtensionFilter("MP3", "mp3"));

        addGuiComponents();
    }

    private void addGuiComponents() {
        addToolBar();

        // record image
        JLabel songImage = new JLabel(loadImageIcon("src/assets/music.png", 250));
        songImage.setBounds(0, 50, getWidth() - 20, 255);
        add(songImage);

        // title
        songTitle = new JLabel("Song title");
        songTitle.setBounds(0, 330, getWidth()-10, 30);
        songTitle.setFont(new Font("Dialog", Font.BOLD, 24));
        songTitle.setForeground(TEXT_COLOR);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);

        // artist
        songArtist = new JLabel("Artist");
        songArtist.setBounds(0, 365, getWidth()-10, 30);
        songArtist.setFont(new Font("Dialog", Font.PLAIN, 20));
        songArtist.setForeground(TEXT_COLOR);
        songArtist.setHorizontalAlignment(SwingConstants.CENTER);
        add(songArtist);

        // slider
        addSlider();

        // navigation buttons
        addNavigationButtons();
    }

    private void addToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0, getWidth(), 30);
        toolBar.setFloatable(false);    // to prevent floating

        // drop down menu
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        // song menu
        JMenu songMenu = new JMenu("Options");
        songMenu.setFont(new Font("Lexend", Font.BOLD, 18));
        menuBar.add(songMenu);

        // play song
        JMenuItem yourSongs = new JMenuItem("Library");
        yourSongs.setFont(new Font("Lexend", Font.BOLD, 16));
        yourSongs.addActionListener(e -> {
            YourSongsWindow yourSongsWindow = new YourSongsWindow(null);
            yourSongsWindow.setVisible(true);
            songToPlay = yourSongsWindow.getSelectedSong();
            if (songToPlay != null) {
                System.out.println("Selected song: " + songToPlay.getSongTitle());
                musicPlayer.resetSong();
                musicPlayer.loadSong(songToPlay);

                updateSongInformation(songToPlay);
                customSlider.updateSlider(songToPlay);
                pauseSongButton();
            } else {
                System.out.println("No song selected.");
            }
        });
        songMenu.add(yourSongs);

        // load item
        JMenuItem loadSong = new JMenuItem("Add song");
        loadSong.setFont(new Font("Lexend", Font.BOLD, 16));
        loadSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.showOpenDialog(MusicPlayerGUI.this);
                selectedFile = fileChooser.getSelectedFile();
                String selectedPath = selectedFile.getPath();

                SongLoadingWindow songLoadingWindow = new SongLoadingWindow(selectedPath);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        songLoadingWindow.createAndShowGUI();
                    }
                });
            }
        });
        songMenu.add(loadSong);

        // delete song
        JMenuItem deleteSongs = new JMenuItem("Delete song");
        deleteSongs.setFont(new Font("Lexend", Font.BOLD, 16));
        deleteSongs.addActionListener(e -> {
            YourSongsWindow yourSongsWindow = new YourSongsWindow(null);
            yourSongsWindow.setVisible(true);
            songToDelete = yourSongsWindow.getSelectedSong();
            if (songToDelete != null) {
                try {
                    musicPlayer.deleteSong(songToDelete);
                    System.out.println("Deleted song: " + songToDelete.getSongTitle() + " by " + songToDelete.getSongArtist());
                } catch (Exception x) {
                    x.printStackTrace();
                }
            } else {
                System.out.println("No song selected.");
            }
        });
        songMenu.add(deleteSongs);

        add(toolBar);
    }

    private ImageIcon loadImageIcon(String imagePath, int newWidth) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            int newHeight = (int) ((double) image.getHeight() / image.getWidth() * newWidth);
            Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // only if image not found
    }

    private void updateSongInformation(Song song){
        songTitle.setText(song.getSongTitle());
        songArtist.setText(song.getSongArtist());
    }

    private void addSlider(){
        customSlider = new CustomSlider(
                350,
                3000,
                new Color(0xFF4081), // progress bar
                new Color(0xFFFFFF), // background bar
                new Color(0xFF4081)  // cursor
        );
        musicPlayer.setCustomSlider(customSlider);
        customSlider.setBounds(getWidth() / 2 - 350 / 2, 420, 350, 50);
        customSlider.mouseListener(musicPlayer);
        add(customSlider);
    }

    private void pauseSongButton() {
        if(musicPlayer.getCurrentSong() != null){
            JButton playButton = (JButton) navigationButtons.getComponent(1);
            JButton pauseButton = (JButton) navigationButtons.getComponent(2);
            // turn off play button
            playButton.setEnabled(false);
            playButton.setVisible(false);
            // turn on pause button
            pauseButton.setVisible(true);
            pauseButton.setEnabled(true);
        }
    }

    private void playSongButton() {
        JButton playButton = (JButton) navigationButtons.getComponent(1);
        JButton pauseButton = (JButton) navigationButtons.getComponent(2);
        // turn on play button
        playButton.setEnabled(true);
        playButton.setVisible(true);
        // turn off pause button
        pauseButton.setVisible(false);
        pauseButton.setEnabled(false);
    }

    private void addNavigationButtons() {
        navigationButtons = new JPanel();
        navigationButtons.setBounds(getWidth() / 2 - 300 / 2, 480, 300, 100);
        navigationButtons.setBackground(null);
        int buttonWidth = 60;

        // previous button
        JButton previousButton = new JButton(loadImageIcon("src/assets/backward.png", buttonWidth));
        previousButton.setBorderPainted(false);
        previousButton.setBackground(null);
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(musicPlayer.getCurrentSong() != null) {
                    musicPlayer.pauseSong();
                    try {
                        System.out.println("Next song: " + songToPlay.getSongTitle());
                        musicPlayer.resetSong();
                        songToPlay = musicPlayer.playPreviousSong();
                        customSlider.updateSlider(songToPlay);
                        updateSongInformation(songToPlay);
                        pauseSongButton();
                    }
                    catch (Exception ex) {
                        System.out.println("nextButton didn't work properly.");
                    }
                }
            }
        });
        navigationButtons.add(previousButton);

        // play button
        JButton playButton = new JButton(loadImageIcon("src/assets/play.png", buttonWidth));
        playButton.setBorderPainted(false);
        playButton.setBackground(null);
        playButton.setVisible(true);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(musicPlayer.getCurrentSong() != null){
                    pauseSongButton();
                    musicPlayer.playCurrentSong();
                }
            }
        });
        navigationButtons.add(playButton);

        // pause button
        JButton pauseButton = new JButton(loadImageIcon("src/assets/pause.png", buttonWidth));
        pauseButton.setBorderPainted(false);
        pauseButton.setBackground(null);
        pauseButton.setVisible(false);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSongButton();
                musicPlayer.pauseSong();
            }
        });
        navigationButtons.add(pauseButton);

        // next button
        JButton nextButton = new JButton(loadImageIcon("src/assets/forward.png", buttonWidth));
        nextButton.setBorderPainted(false);
        nextButton.setBackground(null);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(musicPlayer.getCurrentSong() != null) {
                    musicPlayer.pauseSong();
                    try {
                        System.out.println("Next song: " + songToPlay.getSongTitle());
                        musicPlayer.resetSong();
                        songToPlay = musicPlayer.playNextSong();
                        customSlider.updateSlider(songToPlay);
                        updateSongInformation(songToPlay);
                        pauseSongButton();
                    }
                    catch (Exception ex) {
                        System.out.println("nextButton didn't work properly.");
                    }
                }
            }
        });
        navigationButtons.add(nextButton);

        add(navigationButtons);
    }
}