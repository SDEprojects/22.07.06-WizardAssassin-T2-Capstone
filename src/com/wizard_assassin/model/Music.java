
package com.wizard_assassin.model;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


public class Music {
    private boolean musicOn = true;
    private JFrame jFrame = new JFrame();
    JPanel musicPanel = new JPanel();
    JButton playButton = new JButton("Play");
    JButton stopButton = new JButton("Stop");
    JLabel musicLabel = new JLabel();

    public Music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    }

    public void initialize() {

        jFrame.setTitle("Music");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setSize(300, 300);
        jFrame.setLocationRelativeTo(null);
        jFrame.setBackground(Color.green);
        jFrame.setVisible(true);
        jFrame.setLayout(new BorderLayout(10, 10));
        musicPanel.setBackground(Color.pink);
        jFrame.add(musicPanel);
        musicPanel.add(playButton);
        musicPanel.add(stopButton);


        musicLabel.setText("Music play and stop");
        musicPanel.add(musicLabel);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    musicOn=true;
                    playMusic();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musicOn = false;
            }
        });
    }

    public void playMusic() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        //music on
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Clip clip = AudioSystem.getClip();
        clip.stop();
        clip.close();
        InputStream music = classLoader.getResourceAsStream("music.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(music));
        clip.open(audioInputStream);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (musicOn) {
                    clip.start();
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    if (!musicOn) {
                        clip.stop();
                    }
                }
            }
        });
        thread.start();
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Music music = new Music();
//                    music.initialize();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
}

//package com.wizard_assassin;
//
//import java.io.BufferedInputStream;
//import java.io.InputStream;
//
//import javax.sound.sampled.*;
//
//import java.io.IOException;
//
//public class Music {
//    private static boolean musicOn = true;
//
//    public static void playMusic() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
//        //music on
//        musicOn = true;
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        Clip clip = AudioSystem.getClip();
//        clip.stop();
//        clip.close();
//        InputStream music = classLoader.getResourceAsStream("music.wav");
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(music));
//        clip.open(audioInputStream);
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (musicOn) {
//                    clip.start();
//                    clip.loop(Clip.LOOP_CONTINUOUSLY);
//                    if (!musicOn) {
//                        clip.stop();
//                    }
//                }
//            }
//        });
//        thread.start();
//    }
//
//    public static void stopMusic() {
//        musicOn = false;
//    }
//}