package com.SilviaFerrari;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import static java.lang.Math.sqrt;

public class MusicPlayerGUI extends JFrame {
    private final JFileChooser fileChooser;
    private File selectedFile;
    MusicPlayer musicPlayer = new MusicPlayer();

    // color configuration
    public static final Color FRAME_COLOR = Color.BLACK;
    public static final Color TEXT_COLOR = Color.WHITE;

    private JLabel songTitle, songArtist;

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

        addGuiComponents();
    }

    private void addGuiComponents() {
        addToolBar();

        // recod image
        JLabel songImage = new JLabel(loadImageIcon("src/assets/music.png", 250));
        songImage.setBounds(0, 50, getWidth() - 20, 255);
        add(songImage);

        // title
       songTitle = new JLabel("Song title");
        songTitle.setBounds(0, 325, getWidth()-10, 30);
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
        toolBar.setBounds(0, 0, getWidth(), 20);
        toolBar.setFloatable(false);    // to prevent floating

    // drop down menu
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

    // song menu
        JMenu songMenu = new JMenu("Song");
        menuBar.add(songMenu);

        // display songs from json file
        JMenuItem yourSongs = new JMenuItem("Your songs");
        yourSongs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                YourSongsWindow yourSongsWindow = new YourSongsWindow();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        YourSongsWindow.createAndShowGUI();
                    }
                });
                Song songToPlay = yourSongsWindow.getSelectedSong();
                musicPlayer.loadSong(songToPlay);
            }
        });
        songMenu.add(yourSongs);

        // load item
        JMenuItem loadSong = new JMenuItem("Load song");
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
        songMenu.add(deleteSongs);

    // playlist menu
        JMenu playListMenu = new JMenu("Playlist");
        menuBar.add(playListMenu);

        // create item
        JMenuItem createPlayList = new JMenuItem("Create playlist");
        playListMenu.add(createPlayList);

        // delete item
        JMenuItem deletePlayList = new JMenuItem("Delete playlist");
        playListMenu.add(deletePlayList);

        // rename item
        JMenuItem renamePlayList = new JMenuItem("Rename playlist");
        playListMenu.add(renamePlayList);

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

    private void addSlider() {
        // create slider, set dimensions & style
        int sliderLength = 350;
        JSlider playerBackSlider = new JSlider(JSlider.HORIZONTAL, 0, 3000, 0);
        playerBackSlider.setBounds(getWidth() / 2 - sliderLength / 2, 405, sliderLength, 30);
        playerBackSlider.setOpaque(false); // transparent background
        playerBackSlider.setBackground(null); // remove background

        // cursor style
        playerBackSlider.setUI(new javax.swing.plaf.basic.BasicSliderUI(playerBackSlider) {
            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // smooth
                g2.setColor(new Color(0xFF4081)); // color
                int thumbSize = 17; // dimension in px
                g2.fillOval(        // draw circle
                        thumbRect.x + thumbRect.width / 2 - thumbSize / 2,
                        thumbRect.y + thumbRect.height / 2 - thumbSize / 2, thumbSize, thumbSize
                );
            }

            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // smooth

                // background bar
                int trackHeight = 8;
                int trackY = trackRect.y + (trackRect.height - trackHeight) / 2;
                Color trackColor = new Color(0xFFFFFF);
                g2.setColor(trackColor);
                g2.fillRoundRect(trackRect.x, trackY, trackRect.width, trackHeight, trackHeight, trackHeight);

                // progress bar depends on cursor position
                int progressWidth = thumbRect.x + thumbRect.width / 2 - trackRect.x;
                Color progressColor = new Color(0xFF4081);
                g2.setColor(progressColor);
                g2.fillRoundRect(trackRect.x, trackY, progressWidth, trackHeight, trackHeight, trackHeight);
            }
        });
        add(playerBackSlider);
    }

    private void addNavigationButtons() {
        JPanel navigationButtons = new JPanel();
        navigationButtons.setBounds(getWidth() / 2 - 300 / 2, 450, 300, 100);
        navigationButtons.setBackground(null);
        int buttonWidth = 60;

        // previous button
        JButton backButton = new JButton(loadImageIcon("src/assets/backward.png", buttonWidth));
        backButton.setBorderPainted(false);
        backButton.setBackground(null);
        navigationButtons.add(backButton);

        // play button
        JButton playButton = new JButton(loadImageIcon("src/assets/play.png", buttonWidth));
        playButton.setBorderPainted(false);
        playButton.setBackground(null);
        playButton.setVisible(true);
        navigationButtons.add(playButton);

        // pause button
        JButton pauseButton = new JButton(loadImageIcon("src/assets/pause.png", buttonWidth));
        pauseButton.setBorderPainted(false);
        pauseButton.setBackground(null);
        pauseButton.setVisible(false);
        navigationButtons.add(pauseButton);

        // next button
        JButton nextButton = new JButton(loadImageIcon("src/assets/forward.png", buttonWidth));
        nextButton.setBorderPainted(false);
        nextButton.setBackground(null);
        navigationButtons.add(nextButton);

        add(navigationButtons);
    }
}
