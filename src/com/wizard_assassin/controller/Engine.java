package com.wizard_assassin.controller;

import com.apps.util.Console;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizard_assassin.view.GamePanel;
import com.wizard_assassin.view.GameWindow;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class Engine {
    private GamePanel gamePanel;
    private GameWindow gameWindow;
    private Scanner inputScanner = new Scanner(System.in);


    public Engine() {
        runGraphics();
        //title();
        //gameObjective();
        //beginGame();
    }

    void runGraphics() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //noinspection TryWithIdenticalCatches
                try {
                    gamePanel = new GamePanel();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gameWindow = new GameWindow(gamePanel);
            }
        });
    }

    void beginGame() {
        String start;

        System.out.println("\033[35m" + "Do you want to start the game? yes | no" + "\033[0m");

        start = inputScanner.nextLine().trim().toLowerCase();
        if (start.equals("yes") || start.equals("y")) {

            Console.clear();

            //new Game();

        } else if (start.equals("no") || start.equals("n")) {
            System.out.println("Thank you for playing");
            System.exit(0);
        } else {
            System.out.println("Please enter 'yes' to continue or 'no' to quit the game.");
            beginGame();
        }
    }


}
