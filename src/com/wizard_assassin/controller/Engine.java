package com.wizard_assassin.controller;

import com.apps.util.Console;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizard_assassin.graphics.GamePanel;
import com.wizard_assassin.graphics.GameWindow;
import com.wizard_assassin.model.Game;

import javax.swing.*;
import java.io.InputStream;
import java.util.Scanner;


public class Engine {
    private GamePanel gamePanel;
    private GameWindow gameWindow;
    private Scanner inputScanner = new Scanner(System.in);


    public Engine() {
        runGraphics();
        title();
        gameObjective();
        beginGame();
    }

    void runGraphics() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gamePanel = new GamePanel();
                gameWindow = new GameWindow(gamePanel);
            }
        });
    }

    void title() {
        System.out.println();
        FileReading fileReading = new FileReading();
        System.out.println("\033[35m" + fileReading.dataReader("welcome.txt") + "\033[0m");
        System.out.println();
    }

    void gameObjective() {

        ObjectMapper mapper = new ObjectMapper();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream introductionRecFile = classLoader.getResourceAsStream("introduction.json");
            Introduction obj = mapper.readValue(introductionRecFile, Introduction.class);
            String gameIntro = obj.getIntroduction();
            String gameObj = obj.getObjective();
            String gameWin = obj.getWin();
            System.out.println("\033[35m" + gameIntro + "\n" + gameObj + "\n" + gameWin + "\033[0m");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    void beginGame() {
        String start;

        System.out.println("\033[35m" + "Do you want to start the game? yes | no" + "\033[0m");

        start = inputScanner.nextLine().trim().toLowerCase();
        if (start.equals("yes") || start.equals("y")) {

            Console.clear();
            new Game();

        } else if (start.equals("no") || start.equals("n")) {
            System.out.println("Thank you for playing");
            System.exit(0);
        } else {
            System.out.println("Please enter 'yes' to continue or 'no' to quit the game.");
            beginGame();
        }
    }


}
