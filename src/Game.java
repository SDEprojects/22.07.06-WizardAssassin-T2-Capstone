import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;

class Game {

    public Scanner inputScanner = new Scanner(System.in);

    public void execute() throws IOException {
        title();
        beginGame();

    }

    private void title() throws IOException {

        System.out.println();
//        System.out.println("" + Files.readString(Path.of("22.07.06-WizardAssassin\\resources/welcome.txt")) + "\033[0m");
        System.out.println("╔╗╔╗╔╗╔═══╗╔╗   ╔═══╗╔═══╗╔═╗╔═╗╔═══╗    ╔════╗╔═══╗    ╔╗╔╗╔╗╔══╗╔════╗╔═══╗╔═══╗╔═══╗    ╔═══╗╔═══╗╔═══╗╔═══╗╔═══╗╔═══╗╔══╗╔═╗ ╔╗\n" +
                "║║║║║║║╔══╝║║   ║╔═╗║║╔═╗║║║╚╝║║║╔══╝    ║╔╗╔╗║║╔═╗║    ║║║║║║╚╣╠╝╚══╗ ║║╔═╗║║╔═╗║╚╗╔╗║    ║╔═╗║║╔═╗║║╔═╗║║╔═╗║║╔═╗║║╔═╗║╚╣╠╝║║╚╗║║\n" +
                "║║║║║║║╚══╗║║   ║║ ╚╝║║ ║║║╔╗╔╗║║╚══╗    ╚╝║║╚╝║║ ║║    ║║║║║║ ║║   ╔╝╔╝║║ ║║║╚═╝║ ║║║║    ║║ ║║║╚══╗║╚══╗║║ ║║║╚══╗║╚══╗ ║║ ║╔╗╚╝║\n" +
                "║╚╝╚╝║║╔══╝║║ ╔╗║║ ╔╗║║ ║║║║║║║║║╔══╝      ║║  ║║ ║║    ║╚╝╚╝║ ║║  ╔╝╔╝ ║╚═╝║║╔╗╔╝ ║║║║    ║╚═╝║╚══╗║╚══╗║║╚═╝║╚══╗║╚══╗║ ║║ ║║╚╗║║\n" +
                "╚╗╔╗╔╝║╚══╗║╚═╝║║╚═╝║║╚═╝║║║║║║║║╚══╗     ╔╝╚╗ ║╚═╝║    ╚╗╔╗╔╝╔╣╠╗╔╝ ╚═╗║╔═╗║║║║╚╗╔╝╚╝║    ║╔═╗║║╚═╝║║╚═╝║║╔═╗║║╚═╝║║╚═╝║╔╣╠╗║║ ║║║\n" +
                " ╚╝╚╝ ╚═══╝╚═══╝╚═══╝╚═══╝╚╝╚╝╚╝╚═══╝     ╚══╝ ╚═══╝     ╚╝╚╝ ╚══╝╚════╝╚╝ ╚╝╚╝╚═╝╚═══╝    ╚╝ ╚╝╚═══╝╚═══╝╚╝ ╚╝╚═══╝╚═══╝╚══╝╚╝ ╚═╝");

        System.out.println();
        System.out.println("Wizard Assassin is a single-player game in which the objective is to defeat the evil wizard " +
                "and save the king in order to win.\nThe player needs to explore different rooms in the castle, collect items until it reach the evil wizard.");
        System.out.println();
        System.out.println();

    }

    private void beginGame() throws IOException {
        String start;

        System.out.println("Do you want to start the game? yes/no");
        start = inputScanner.nextLine().trim().toLowerCase();
        if (start.equals("yes") || start.equals("y")) {
            //System.out.println("You have started the game");
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

        System.out.println("If you want to exit the game type 'quit'");
        quit = inputScanner.nextLine().trim().toLowerCase();
        if (quit.equals("quit")) {
            System.out.println("Are you sure you want to quit? yes/no");
            String doubleChecking = inputScanner.nextLine().trim().toLowerCase();
            if (doubleChecking.equals("yes") || (doubleChecking.equals("y"))){
                System.out.println("Thank you for playing");
                System.exit(0);
            }else {
                chooseLocation();
            }
        }else {
            chooseLocation();
        }
    }


    public void chooseLocation() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("22.07.06-WizardAssassin//src/Location.json"));
        // https://stackoverflow.com/questions/19169754/parsing-nested-json-data-using-gson
        Data obj = gson.fromJson(reader, Data.class);
        Location currentLocation = obj.getLocations().get(0);

        //while loop
        while (true) {
            System.out.println("\n                                            *********  You are in the " + currentLocation.getName() + ". *********\n\n");

            System.out.println(currentLocation.getDescription() + "\n");

            System.out.println("You see these items: " + Arrays.toString(currentLocation.getItem()));
            System.out.println("From the " + currentLocation.getName() + " you can go to the:");
            for (Map.Entry<String, String> direction : currentLocation.getDirections().entrySet())
                System.out.println("     " + direction.getKey() + ": " + direction.getValue());

            System.out.println("");
            System.out.println("What would you like to do now?");
            String userInput = inputScanner.nextLine().trim().toLowerCase();
            String[] parseInput = userInput.split(" ");
            if(parseInput.length == 2) {
                if (currentLocation.directions.get(parseInput[1]) == null){
                    System.out.println("\n\u001B[31m" + parseInput[1].toUpperCase() + "\u001B[0m is not a valid direction. Choose again...");
                }

                else if ( parseInput[0].equals("go") || parseInput[0].equals("move")){

                    String locationInput = currentLocation.directions.get(parseInput[1]);
                    //System.out.println("CURRENT LOCATION: " + currentLocation);
                    currentLocation = obj.getPickedLocation(locationInput);
                    //System.out.println("this is LOCATION INPUT: " + locationInput);
                }
                else {
                    System.out.println("I do not understand " + userInput + ". Format command as 'VERB NOUN'");
                }
            }
            else {
                System.out.println("I do not understand " + userInput + ". Format command as 'VERB NOUN'");
            }
        }
    }
}
