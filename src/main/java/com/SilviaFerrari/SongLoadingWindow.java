package com.SilviaFerrari;

import javax.swing.*;
import java.awt.*;

public class SongLoadingWindow {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI()); // Event Dispatch Thread
    }

    private static void createAndShowGUI() {
        // main window (centered)
        JFrame frame = new JFrame("Choose an MP3 file");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // main panel style
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // component space
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title field
        JLabel titleLabel = new JLabel("Titolo:");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        JTextField titleField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(titleField, gbc);

        // Artist field
        JLabel artistLabel = new JLabel("Artista:");
        artistLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(artistLabel, gbc);

        JTextField artistField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(artistField, gbc);

        // Duration field
        JLabel durationLabel = new JLabel("Durata:");
        durationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(durationLabel, gbc);

        JTextField durationField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(durationField, gbc);

        // Enter button
        JButton submitButton = new JButton("Salva canzone");
        submitButton.setFocusPainted(false);
        submitButton.setBackground(new Color(0, 120, 215)); // Colore blu moderno
        submitButton.setForeground(Color.WHITE); // Testo bianco
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(submitButton, gbc);

        // Aggiunta azione al pulsante
        submitButton.addActionListener(e -> {
            String title = titleField.getText();
            String artist = artistField.getText();
            String duration = durationField.getText();

            // Messaggio di conferma con i dati inseriti
            JOptionPane.showMessageDialog(frame,
                    "Titolo: " + title + "\nArtista: " + artist + "\nDurata: " + duration,
                    "Dati Inseriti",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // Aggiunta del pannello alla finestra e visualizzazione
        frame.add(panel);
        frame.setVisible(true);
    }
}
