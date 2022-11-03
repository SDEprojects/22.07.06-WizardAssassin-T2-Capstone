package com.wizard_assassin;

import java.io.IOException;
import java.util.Scanner;

public class Quit {

    private static Scanner inputScanner;

    static void quitGame() throws IOException {
        String quit;

        System.out.println("Are you sure you want to 'quit'? yes| no");
        quit = inputScanner.nextLine().trim().toLowerCase();
        if (quit.equals("yes")) {
            System.out.println("Thank you for playing");
            System.exit(0);
        }
        else {
            System.out.println("\n\u001B[91m                         *********  You are in the " + Game.getLocation() + ". *********\u001B[0m\n\n");
            Game.gameLoop();
        }
    }
}
