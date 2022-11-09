package com.wizard_assassin.model;

import java.util.Scanner;

public class Quit {

    public Quit() {
        quitGame();
    }

    void quitGame() {
        String quit;

        System.out.println("Are you sure you want to 'quit'? yes| no");
        Scanner inputScanner = new Scanner(System.in);
        quit = inputScanner.nextLine().trim().toLowerCase();
        if (quit.equals("yes")) {
            System.out.println("Thank you for playing");
            System.exit(0);
        }
    }
}
