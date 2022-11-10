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
    //JPanel musicPanel = new JPanel();
    JButton playButton = new JButton("Play");
    JButton stopButton = new JButton("Stop");
    JButton volumeUpButton = new JButton("VOLUME+");
    JButton volumeDownButton = new JButton("VOLUME-");

    JLabel musicLabel = new JLabel();
    Clip clip= AudioSystem.getClip();
    float volume = 0.0f;
    FloatControl fc;

    public Music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    }

    public void initialize() {
        jFrame.setTitle("Music");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setSize(500, 300);
        jFrame.setLocationRelativeTo(null);
        jFrame.setBackground(Color.green);
        //jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setLayout(new GridLayout(1, 5));
        //musicPanel.setBackground(Color.pink);
        //jFrame.add(musicPanel);
        //musicPanel.add(playButton);
        //musicPanel.add(stopButton);

        jFrame.add(playButton);
        jFrame.add(stopButton);
        jFrame.add(volumeUpButton);
        jFrame.add(volumeDownButton);
        musicLabel.setText("Welcome to music control panel! ");
        jFrame.add(musicLabel);
        //musicPanel.add(musicLabel);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setMusicOn(true);
                    setVolume(0.0f);
                    playMusic();
                    System.out.println(volume);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMusicOn(false);
            }
        });

        volumeUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnUpMusic();
                System.out.println(volume);
            }
        });

        volumeDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnDownMusic();
                System.out.println(volume);
            }
        });
    }

//    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        Music music = new Music();
//        music.initialize();
//    }

    public void playMusic() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        InputStream music = classLoader.getResourceAsStream("music.wav");
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(music));
//        clip = AudioSystem.getClip();
//        clip.open(audioInputStream);
//        fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//        fc.setValue(3.0f);
//        clip.setFramePosition(0);
//
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



        clip.close();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream music = classLoader.getResourceAsStream("music.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(music));
        clip.open(audioInputStream);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (musicOn) {
                    clip.start();
//                    FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//                    fc.setValue(volume); // set volume to 50% to start
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    if (!musicOn) {
                        clip.stop();
                    }
                }
            }
        });
        thread.start();
    }

    public void turnUpMusic() {
        volume += 2.0f;
        if (volume > 6.0f) {
            volume = 6.0f;
        }
        FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        fc.setValue(getVolume());

    }

    public void turnDownMusic() {
        volume -= 2.0f;
        if (volume < -80.0f) {
            volume = -80.0f;
        }
        fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        fc.setValue(getVolume());
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }
}