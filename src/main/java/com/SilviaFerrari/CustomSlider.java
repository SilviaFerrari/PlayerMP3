package com.SilviaFerrari;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.util.Hashtable;

public class CustomSlider extends JPanel {
    private JSlider slider;
    public static final Color TEXT_COLOR = new Color(250,250,250);

    public CustomSlider(int sliderLength, int maxFrames, Color progressColor, Color trackColor, Color thumbColor) {
        setLayout(null);
        setOpaque(false);

        // bar setup
        slider = new JSlider(JSlider.HORIZONTAL, 0, maxFrames, 0);
        slider.setBounds(getWidth() / 2, 0, sliderLength, 50);
        slider.setOpaque(false);
        slider.setBackground(null);

        // cursor style
        slider.setUI(new BasicSliderUI(slider) {
            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(thumbColor);
                int thumbSize = 20;
                g2.fillOval(
                        thumbRect.x + thumbRect.width / 2 - thumbSize / 2,
                        thumbRect.y + thumbRect.height / 2 - thumbSize / 2,
                        thumbSize,
                        thumbSize
                );
            }

            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // background bar
                int trackHeight = 8;
                int trackY = trackRect.y + (trackRect.height - trackHeight) / 2;
                g2.setColor(trackColor);
                g2.fillRoundRect(trackRect.x, trackY, trackRect.width, trackHeight, trackHeight, trackHeight);

                // progress bar
                int progressWidth = thumbRect.x + thumbRect.width / 2 - trackRect.x;
                g2.setColor(progressColor);
                g2.fillRoundRect(trackRect.x, trackY, progressWidth, trackHeight, trackHeight, trackHeight);
            }
        });

        add(slider);
    }

    public void updateSlider(Song song) {
        slider.setMaximum(song.getMp3File().getFrameCount());
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();

        JLabel labelBeginning = new JLabel("00:00");
        labelBeginning.setFont(new Font("Dialog", Font.BOLD, 18));
        labelBeginning.setForeground(TEXT_COLOR);

        JLabel labelEnd = new JLabel(song.getSongDuration());
        labelEnd.setFont(new Font("Dialog", Font.BOLD, 18));
        labelEnd.setForeground(TEXT_COLOR);

        labelTable.put(0, labelBeginning);
        labelTable.put(song.getMp3File().getFrameCount(), labelEnd);

        slider.setLabelTable(labelTable);
        slider.setPaintLabels(true);
    }

    public JSlider getSlider() {
        return slider;
    }
}