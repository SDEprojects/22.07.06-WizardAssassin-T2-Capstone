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
import java.awt.image.BufferedImage;

public class Music {
    private boolean musicOn = true;
    private JFrame musicFrame = new JFrame();
    //JPanel musicPanel = new JPanel();
    JButton playButton = new JButton("Play");
    JButton stopButton = new JButton("Stop");
    JButton volumeUpButton = new JButton("VOL +");
    JButton volumeDownButton = new JButton("VOL -");

    JLabel musicLabel = new JLabel();
    Clip clip= AudioSystem.getClip();
    float volume = -9.0f;
    FloatControl fc;

    private BufferedImage backgroundImage;

    public Music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    }

    public void initialize() {
        musicFrame.setTitle("Music");
        musicFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        musicFrame.setSize(500, 300);
        musicFrame.setLocationRelativeTo(null);
        //musicFrame.setBackground(Color.green);
        //jFrame.pack();
        musicFrame.setVisible(true);
        //musicFrame.setLayout(new GridLayout(1, 5));

        //musicPanel.setBackground(Color.pink);
        //jFrame.add(musicPanel);
        //musicPanel.add(playButton);
        //musicPanel.add(stopButton);

        musicFrame.add(playButton);
        musicFrame.add(stopButton);
        musicFrame.add(volumeUpButton);
        musicFrame.add(volumeDownButton);
        musicLabel.setText("Welcome to music control panel! ");
        musicFrame.add(musicLabel);
        //musicPanel.add(musicLabel);
        importImg("TitleScreenResources/musicBkgd.png");



        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clip.close();
                    setMusicOn(true);
                    playMusic();
                    System.out.println(getVolume());
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
                System.out.println(getVolume());
            }
        });

        volumeDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turnDownMusic();
                System.out.println(getVolume());
            }
        });
    }

    private BufferedImage importImg(String file) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);

        try {
            backgroundImage = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        musicFrame.repaint();
        return null;
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Music music = new Music();
        music.initialize();
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