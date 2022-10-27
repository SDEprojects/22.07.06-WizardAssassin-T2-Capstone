import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;

class Game implements Verbs {

    public Scanner inputScanner = new Scanner(System.in);

    public void execute() throws IOException {
        title();
        gameObjective();
        beginGame();

    }

    private void title() throws IOException {

        System.out.println();
        System.out.println("\033[35m" + Files.readString(Path.of("./resources/welcome.txt")) + "\033[0m");

        System.out.println();

    }
    private void gameObjective() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("./resources/introduction.json"));

        Introduction obj = gson.fromJson(reader, (Type) Introduction.class);
        String gameIntro = obj.getIntroduction();
        String gameObj = obj.getObjective();
        String gameWin = obj.getWin();
        System.out.println("\033[35m" + gameIntro + "\n" + gameObj + "\n" + gameWin + "\033[0m");
        System.out.println();
        System.out.println("\033[35m" + "In order to move between rooms you need to type the word 'go'. For example 'go south'" + "\033[0m");
        System.out.println();
    }

    private void beginGame() throws IOException {
        String start;

        System.out.println("\033[35m" + "Do you want to start the game? yes | no" + "\033[0m");
        start = inputScanner.nextLine().trim().toLowerCase();
        if (start.equals("yes") || start.equals("y")) {
            String os = System.getProperty("os.name");
            System.out.println(os);
            ClearConsole.clearConsole();
            chooseLocation();
        } else if (start.equals("no") || start.equals("n")) {
            System.out.println("Thank you for playing");
            System.exit(0);
        } else {
            System.out.println("Please enter 'yes' to continue or 'no' to quit the game.");
            beginGame();
        }
    }

    private void quitGame() throws IOException {
        String quit;

        System.out.println("Are you sure you want to 'quit'? yes| no");
        quit = inputScanner.nextLine().trim().toLowerCase();
        if (quit.equals("yes")) {
            System.out.println("Thank you for playing");
            System.exit(0);
        }
        else {
            chooseLocation();
        }
    }


    public void chooseLocation() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("./resources/Location.json"));

        Data obj = gson.fromJson(reader, Data.class);
        Location currentLocation = obj.getLocations().get(0);

        boolean condition = true;
        while (condition) {
            System.out.println("\n\u001B[35m                                              *********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");

            System.out.println(currentLocation.getDescription() + "\n");

            System.out.println("You see these items: " + Arrays.toString(currentLocation.getItem()));
            System.out.println("From the " + currentLocation.getName() + " you can go to the:");
            for (Map.Entry<String, String> direction : currentLocation.getDirections().entrySet())
                System.out.println("     " + direction.getKey() + ": " + direction.getValue());

            System.out.println("");
            System.out.println("What would you like to do now?\nEnter 'quit' to exit game.\nEnter 'help' for list of valid commands.");
            String userInput = inputScanner.nextLine().trim().toLowerCase();

            String[] parseInput = userInput.split(" ");

            if(userInput.equals("quit")) {
                quitGame();
            }
            else if(userInput.equals("help")) {
                System.out.println("All commands must be in this format 'VERB<space>NOUN'\nOr 'quit' to exit game");
                HelpMenu.printMenuHeader();
                HelpMenu.buildMenu().forEach(HelpMenu::printMenu);
            }
            else if(parseInput.length == 2) {
                String inputVerb = parseInput[0];
                String inputNoun = parseInput[1];

                if (currentLocation.directions.get(inputNoun) == null) {
                    System.out.println("\n\u001B[31m" + inputNoun.toUpperCase() + "\u001B[0m is not a valid direction. Choose again...");
                }
                else if (Verbs.getMoveActions().contains(inputVerb)) {

                        String locationInput = currentLocation.directions.get(inputNoun);
                        currentLocation = obj.getPickedLocation(locationInput);
                }
                else if (Verbs.getItemActions().contains(inputVerb)) {
                        System.out.println("This VERB is for an item action");
                }
                else if (Verbs.getCharacterActions().contains(inputVerb)) {
                    System.out.println("This VERB is for a character interaction");
                }
                else if (Verbs.getAreaActions().contains(inputVerb)) {
                    System.out.println("This VERB is for area interactions");
                }
                else {
                    System.out.println("I do not understand " + userInput.toUpperCase() + ". Format command as 'VERB<space>NOUN' or 'quit' or 'help'");
                }
            }
            else {
                System.out.println("I do not understand " + userInput.toUpperCase() + ". Format command as 'VERB<space>NOUN' or 'quit' or 'help'");
            }
        }
    }
}
