package com.wizard_assassin;

import com.google.gson.Gson;
import com.wizard_assassin.graphics.GamePanel;
import com.wizard_assassin.graphics.GameWindow;

import javax.swing.*;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        try {
            System.out.println("\033[35m" + Files.readString(Path.of("./resources/welcome.txt")) + "\033[0m");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    void gameObjective() {
        Gson gson = new Gson();
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get("./resources/introduction.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Introduction obj = gson.fromJson(reader, (Type) Introduction.class);
        String gameIntro = obj.getIntroduction();
        String gameObj = obj.getObjective();
        String gameWin = obj.getWin();
        System.out.println("\033[35m" + gameIntro + "\n" + gameObj + "\n" + gameWin + "\033[0m");
        System.out.println();
    }

    void beginGame() {
        String start;

        System.out.println("\033[35m" + "Do you want to start the game? yes | no" + "\033[0m");

        start = inputScanner.nextLine().trim().toLowerCase();
        if (start.equals("yes") || start.equals("y")) {

            ClearConsole.clearConsole();
            try {
                Game.gameLoop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (start.equals("no") || start.equals("n")) {
            System.out.println("Thank you for playing");
            System.exit(0);
        } else {
            System.out.println("Please enter 'yes' to continue or 'no' to quit the game.");
            beginGame();
        }
    }


}
