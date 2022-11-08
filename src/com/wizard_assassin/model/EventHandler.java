package com.wizard_assassin.model;


import com.apps.util.Console;

import java.util.ArrayList;
import java.util.Scanner;

class EventHandler {


    public void eventHandler(String input) {
        TextParser parser= new TextParser();
        ArrayList<String> result =  parser.textParser(input);

        if ("help".equals(result.get(0))){
            Console.clear();
            System.out.println("All commands must be in this format 'VERB<space>NOUN'\nOr 'quit' to exit game");
            HelpMenu.printMenuHeader();
            HelpMenu.buildMenu().forEach(HelpMenu::printMenu);
            System.out.println("\nEnter back to exit");
            boolean condition = false;
            while (!condition) {
                Scanner scanner = new Scanner(System.in);
                String playerChoice = scanner.nextLine().trim().toLowerCase();
                if (playerChoice.equals("back")) {
                    condition = true;
                }
            }
        }
        if ("quit".equals(result.get(0))){
            new Quit();
        }


        if ("view".equals(result.get(0))){
            Console.clear();
            KingdomMap.printMapHeader();
            KingdomMap.showKingdomMap().forEach(KingdomMap::printMap);
            System.out.println("\nEnter back to exit");
            boolean condition = false;
            while (!condition) {
                Scanner scanner = new Scanner(System.in);
                String playerChoice = scanner.nextLine().trim().toLowerCase();
                if (playerChoice.equals("back")) {
                    condition = true;
                }
            }
        }
    }
}