package com.SilviaFerrari;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MusicPlayerGUI extends JFrame {
    // color configuration
    public static final Color FRAME_COLOR = Color.BLACK;
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
        JLabel songImage = new JLabel(loadImageIcon("src/assets/music.png", 200));
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

    /*private ImageIcon loadImageIcon(String imagePath, int newWidth) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            int newHeight = (int) ((double) image.getHeight() / image.getWidth() * newWidth);

            Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // only if image not found
    }*/

    private ImageIcon loadImageIcon(String imagePath, int newWidth) {
        try {
            // Carica l'immagine originale
            BufferedImage originalImage = ImageIO.read(new File(imagePath));

            // Calcola la nuova altezza mantenendo il rapporto d'aspetto
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();
            int newHeight = (int) ((double) originalHeight / originalWidth * newWidth);

            // Crea un'immagine ridimensionata
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

            // Applica una trasformazione di scala
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose(); // Libera le risorse grafiche

            // Ritorna l'immagine come ImageIcon
            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Se l'immagine non viene caricata
        }
    }

    private void addSlider() {
        // create slider, set dimensions & style
        JSlider playerBackSlider = new JSlider(JSlider.HORIZONTAL, 0, 3000, 0);
        playerBackSlider.setBounds(getWidth() / 2 - 300 / 2, 365, 300, 30);
        playerBackSlider.setBackground(null); // remove background
        playerBackSlider.setOpaque(false); // transparent background

        // cursor style
        playerBackSlider.setUI(new javax.swing.plaf.basic.BasicSliderUI(playerBackSlider) {
            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // smooth
                g2.setColor(new Color(0xFF4081)); // color
                int thumbSize = 15; // dimension
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
                int trackHeight = 6;
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
        navigationButtons.setBounds(getWidth() / 2 - 300 / 2, 400, 300, 100);
        navigationButtons.setBackground(null);

        // previous button
        JButton backButton = new JButton(loadImageIcon("src/assets/backward.png", 50));
        backButton.setBorderPainted(false);
        backButton.setBackground(null);
        navigationButtons.add(backButton);

        // play button
        JButton playButton = new JButton(loadImageIcon("src/assets/play.png", 50));
        playButton.setBorderPainted(false);
        playButton.setBackground(null);
        navigationButtons.add(playButton);

        // pause button
        JButton pauseButton = new JButton(loadImageIcon("src/assets/pause.png", 50));
        pauseButton.setBorderPainted(false);
        pauseButton.setBackground(null);
        navigationButtons.add(pauseButton);

        // next button
        JButton nextButton = new JButton(loadImageIcon("src/assets/forward.png", 50));
        nextButton.setBorderPainted(false);
        nextButton.setBackground(null);
        navigationButtons.add(nextButton);

        add(navigationButtons);
    }
}
