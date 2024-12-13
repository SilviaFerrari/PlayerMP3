package com.SilviaFerrari;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // to ensure that GUI is executed on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MusicPlayerGUI().setVisible(true);
            }
        });
    }
}
