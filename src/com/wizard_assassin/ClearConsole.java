package com.wizard_assassin;

public class ClearConsole {
    // https://stackoverflow.com/questions/46242330/clearing-console-in-intellij-idea
    // the intellij terminal is not a console and cannot be cleared.
    // this should/may clear if run from a jar file
    public static void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}