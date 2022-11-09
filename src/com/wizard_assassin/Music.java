package com.wizard_assassin;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.*;

import java.io.IOException;

public class Music {
    private static boolean musicOn = true;

    public static void playMusic() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        //music on
        musicOn = true;
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

    public static void stopMusic() {
        musicOn = false;
    }
}