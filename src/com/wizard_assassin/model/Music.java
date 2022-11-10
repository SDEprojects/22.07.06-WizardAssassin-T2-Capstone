package com.wizard_assassin.model;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Music {

    private JFrame musicFrame = new JFrame();
    private JPanel musicPanel = new JPanel();
    private JButton playButton = new JButton("PLAY");
    private JButton stopButton = new JButton("STOP");
    private JButton volumeUpButton = new JButton("VOL +");
    private JButton volumeDownButton = new JButton("VOL -");

    private JLabel musicLabel = new JLabel();
    private BufferedImage myPicture;
    private BufferedImage pic = showPicture("TitleScreenResources/musicBkgd.png");
    private ImageIcon musicBkgdIcon = new ImageIcon(pic);
    private JLabel musicBkgdLabel = new JLabel(musicBkgdIcon);

    private boolean musicOn = true;
    private Clip clip = AudioSystem.getClip();
    float volume = -9.0f;
    private FloatControl fc;

    public Music() throws LineUnavailableException {
    }

    public void initialize() {
        musicFrame.setTitle("Music");
        musicFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        musicFrame.setSize(500, 400);
        musicFrame.setResizable(false);

        musicFrame.setLocationRelativeTo(null);
        musicFrame.setBackground(Color.green);

        musicFrame.setVisible(true);

        musicFrame.add(musicPanel);

        musicPanel.add(playButton);
        musicPanel.add(stopButton);
        musicPanel.add(volumeUpButton);
        musicPanel.add(volumeDownButton);
        musicLabel.setText("Welcome to music control panel! ");
        musicPanel.add(musicLabel);

        musicBkgdLabel.setVisible(true);
        musicBkgdLabel.setBounds(0, 80, 80, 30);
        musicPanel.add(musicBkgdLabel);

        musicPanel.setOpaque(false);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clip.close();
                    setMusicOn(true);
                    playMusic();
                    musicLabel.setText("You are playing music........");
                    //System.out.println(getVolume());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMusicOn(false);
                musicLabel.setText("You stopped the music........");
            }
        });

        volumeUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnUpMusic();
                //System.out.println(getVolume());
                musicLabel.setText("You turned up the music volume: 3.0f.");
            }
        });

        volumeDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnDownMusic();
                //System.out.println(getVolume());
                musicLabel.setText("You turned down the music volume: 3.0f.");
            }
        });
    }

    public BufferedImage showPicture(String file) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        try {
            myPicture = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return myPicture;
    }


    public void playMusic() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream music = classLoader.getResourceAsStream("music.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(music));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        fc.setValue(-9.0f);
        clip.setFramePosition(0);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isMusicOn()) {
                    clip.start();
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    if (!isMusicOn()) {
                        clip.stop();
                    }
                }
            }
        });
        thread.start();
    }

    public void turnUpMusic() {
        volume += 3.0f;
        if (volume > 6.0f) {
            setVolume(6.0f);
        }
        fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        fc.setValue(getVolume());
    }

    public void turnDownMusic() {
        volume -= 3.0f;
        if (volume < -80.0f) {
            setVolume(-80.0f);
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