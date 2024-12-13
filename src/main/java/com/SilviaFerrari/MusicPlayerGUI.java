package com.SilviaFerrari;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MusicPlayerGUI extends JFrame {
    // color configuration
    public static final Color FRAME_COLOR = Color.BLUE;
    public static final Color TEXT_COLOR = Color.WHITE;

    public MusicPlayerGUI() {
        super("MP3 PLayer");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //launch the app at the center of the screen
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        // color setting
        getContentPane().setBackground(FRAME_COLOR);
        addGuiComponents();
    }

    private void addGuiComponents() {
        addToolBar();

        // recod image
        JLabel songImage = new JLabel(loadImageIcon("src/assets/music.png"));
        songImage.setBounds(10, 50, getWidth() - 20, (int) ((double) 225));
        add(songImage);

        // title
        JLabel songTitle = new JLabel("Song title");
        songTitle.setBounds(0, 285, getWidth()-10, 30);
        songTitle.setFont(new Font("Dialog", Font.BOLD, 24));
        songTitle.setForeground(TEXT_COLOR);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);

        // artist
        JLabel artist = new JLabel("Artist");
        artist.setBounds(0, 320, getWidth()-10, 30);
        artist.setFont(new Font("Dialog", Font.PLAIN, 20));
        artist.setForeground(TEXT_COLOR);
        artist.setHorizontalAlignment(SwingConstants.CENTER);
        add(artist);

        // slider
        addSlider();
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

    private ImageIcon loadImageIcon(String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            int newWidth = 200;
            int newHeight = (int) ((double) image.getHeight() / image.getWidth() * newWidth);

            Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // only if image not found
    }

    private void addSlider() {
        JSlider playerBackSlider = new JSlider(JSlider.HORIZONTAL, 0, 3000, 0);
        playerBackSlider.setBounds(getWidth() / 2 - 300 / 2, 365, 300, 30);

        // style
        playerBackSlider.setBackground(null);
        playerBackSlider.setOpaque(false);
        playerBackSlider.setForeground(new Color(0xFF4081));
        playerBackSlider.setBackground(new Color(0x2C2C2C));

        // cursor
        playerBackSlider.setUI(new javax.swing.plaf.basic.BasicSliderUI(playerBackSlider) {
            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2 = (Graphics2D) g; // draw a circle
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0xFF4081)); // color
                int thumbSize = 14; // dimension
                g2.fillOval(thumbRect.x + thumbRect.width / 2 - thumbSize / 2, thumbRect.y + thumbRect.height / 2 - thumbSize / 2, thumbSize, thumbSize);
            }

            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // bar colors
                Color trackColor = new Color(0x444444); // Barra di sfondo
                Color progressColor = new Color(0xFF4081); // Barra di progresso

                // Disegna la barra di sfondo
                int trackHeight = 6;
                int trackY = trackRect.y + (trackRect.height - trackHeight) / 2;
                g2.setColor(trackColor);
                g2.fillRoundRect(trackRect.x, trackY, trackRect.width, trackHeight, trackHeight, trackHeight);

                // Disegna la barra di progresso
                int progressWidth = thumbRect.x + thumbRect.width / 2 - trackRect.x;
                g2.setColor(progressColor);
                g2.fillRoundRect(trackRect.x, trackY, progressWidth, trackHeight, trackHeight, trackHeight);
            }
        });

        // Aggiungi lo slider al frame
        add(playerBackSlider);
    }
}
