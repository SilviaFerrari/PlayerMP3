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
        // to prevent floating
        toolBar.setFloatable(false);

        // drop down menu
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        // song menu (loading song option)
        JMenu songMenu = new JMenu("Song");
        menuBar.add(songMenu);

        add(toolBar);
    }
}
