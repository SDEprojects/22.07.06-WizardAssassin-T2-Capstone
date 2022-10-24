import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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

    }

    private void beginGame() throws IOException {
        String start;

        System.out.println("Do you want to start the game? yes/no");
        start = inputScanner.nextLine().trim();
        if (start.equals("yes")) {
            System.out.println("You have started the game");
            chooseLocation();
        } else if (start.equals("no")) {
            System.out.println("Thank you for playing");
            System.exit(0);
        } else {
            System.out.println("please enter 'yes' to continue or 'no' to quit the game");
            beginGame();
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
            System.out.println("You are in the " + currentLocation.getName());

            System.out.println(currentLocation.getDescription());
            System.out.println("The following items are available in this room: " + Arrays.toString(currentLocation.getItem()));
            System.out.println("As you leave the " + currentLocation.getName() + " you can pick to go to the:");
            for (Map.Entry<String, String> direction : currentLocation.getDirections().entrySet())
                System.out.println("    direction " + direction.getKey() + ", Location: " + direction.getValue());

            System.out.println("");
            System.out.println("Which direction do you walk?");
            String userInput = inputScanner.nextLine();
            String locationInput = currentLocation.directions.get(userInput);

            currentLocation = obj.getPickedLocation(locationInput);
        }
    }
}
