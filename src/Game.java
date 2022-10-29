import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import com.google.gson.Gson;

class Game implements Verbs  {

    private Data obj = makeObj();
    private Location inventory = obj.getLocations().get(13);
    private List<String> inventoryItems = new ArrayList<String>(Arrays.asList(inventory.getItem()));
    private Scanner inputScanner = new Scanner(System.in);

    Game() throws IOException {
    }

    public Data makeObj() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("./resources/Location.json"));
        Data obj = gson.fromJson(reader, Data.class);
        return obj;
    }

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
    }

    private void beginGame() throws IOException {
        String start;

        System.out.println("\033[35m" + "Do you want to start the game? yes | no" + "\033[0m");
        start = inputScanner.nextLine().trim().toLowerCase();
        if (start.equals("yes") || start.equals("y")) {

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

    public void getItem(String itemInput, Location currentLocation) throws IOException {

        List<String> roomItems = new ArrayList<String>(Arrays.asList(currentLocation.getItem()));

        // Add items to room "drop", use later if we add DROP feature
//        roomItems.add("stones");
//        roomItems.add("pebbles");
        // Pick up item step 1, remove from room items
        roomItems.remove(itemInput);
        inventoryItems.add(itemInput);

        // Pick up item step 2, Put item in inventory
        String[] toInventory = new String[inventoryItems.size()];
        toInventory = inventoryItems.toArray(toInventory);
        inventory.setItem(toInventory);

        // INVENTORY PRINT OUT
        System.out.println("\n");
        System.out.printf("You picked up a \033[32m%s\033[0m and added it to your inventory.\n", itemInput);
        // END of INVENTORY

        // NOTE convert roomItems List to array. Update masterObj with changes
        String[] updatedRoomItems = roomItems.toArray(new String[0]);
        currentLocation.setItem(updatedRoomItems);
    }

    public void checkInventory() {
//        Location inventoryArr = obj.getLocations().get(13);
        System.out.println();
        System.out.println("*** Inventory ***");
        System.out.printf("\033[92m%s\033[0m", inventoryItems);
        System.out.println();
    }


    public void chooseLocation() throws IOException {
        Gson gson = new Gson();
        Location currentLocation = obj.getLocations().get(0);
        String oldLocation = "";

        Reader read = Files.newBufferedReader(Paths.get("./resources/characters.json"));
        Characters object = gson.fromJson(read, Characters.class);

        while (true) {

            if (currentLocation.getName().equals("Laboratory") && (inventoryItems.contains("poison")))
            {
                System.out.println("You have poisoned the wizard. You return home as a hero who saved your kingdom.");
                break;
            }
            if(!oldLocation.equals(currentLocation.getName())) {
                System.out.println("\n\u001B[35m                                              *********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");

                System.out.println(currentLocation.getDescription() + "\n");

                for (ExtraCharacters extraCharacters : object.getCharacters())
                    if ((currentLocation.getName().equals(extraCharacters.getRoom())))
                        System.out.printf("You see a \u001B[93m %s \u001B[0m. It says: %s%n", extraCharacters.getName(), extraCharacters.getQuote());
                System.out.println();

                System.out.printf("You see these items: \u001B[32m %s \u001B[0m%n", Arrays.deepToString(currentLocation.getItem()));
                System.out.println("From the " + currentLocation.getName() + " you can go to the:");
                for (Map.Entry<String, String> direction : currentLocation.getDirections().entrySet())
                    System.out.printf("       \u001B[31m %s: %s \u001B[0m%n", direction.getKey(), direction.getValue());

                oldLocation = currentLocation.getName();
            }

            System.out.println("");
            System.out.println("\033[36m What would you like to do now?\033[0m\n\033[90mEnter 'quit' to exit game.\nEnter 'view' to see the map.\nEnter 'help' for list of valid commands.\n Enter 'inventory' to list all your items.\033[0m");
            String userInput = inputScanner.nextLine().trim().toLowerCase();

            String[] parseInput = userInput.split(" ");

            if(userInput.equals("quit")) {
                quitGame();
            }
            else if(userInput.equals("inventory")) {
                checkInventory();
            }

            else if(userInput.equals("help")) {
                System.out.println("All commands must be in this format 'VERB<space>NOUN'\nOr 'quit' to exit game");
                HelpMenu.printMenuHeader();
                HelpMenu.buildMenu().forEach(HelpMenu::printMenu);
            }
            else if(userInput.equals("view")) {
                KingdomMap.printMapHeader();
                KingdomMap.showKingdomMap().forEach(KingdomMap::printMap);
                System.out.println("\n\u001B[35m                                              *********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
            }
            else if(parseInput.length == 2) {
                String inputVerb = parseInput[0];
                String inputNoun = parseInput[1];

                if (Verbs.getMoveActions().contains(inputVerb)) {

                        if (currentLocation.directions.get(inputNoun) != null) {
                            String locationInput = currentLocation.directions.get(inputNoun);
                            currentLocation = obj.getPickedLocation(locationInput);
                        }
                        else {
                            System.out.println("\n\u001B[31m" + inputNoun.toUpperCase() + "\u001B[0m is not a valid direction. Choose again...");
                        }
                }
                else if (Verbs.getItemActions().contains(inputVerb)) {
                        if (Arrays.toString(currentLocation.getItem()).contains(inputNoun)){
                            getItem(inputNoun, currentLocation);
                        }

                }
                else if (Verbs.getCharacterActions().contains(inputVerb)) {
                    System.out.println("This VERB is for a character interaction");
                }
                else if (Verbs.getAreaActions().contains(inputVerb)) {
                    System.out.println("This VERB is for area interactions");
                }
                else {
                    System.out.println("I do not understand " + userInput.toUpperCase() + ". Format command as 'VERB<space>NOUN' or 'quit' or 'view' or 'help' or 'inventory'");
                }
            }
            else {
                System.out.println("I do not understand " + userInput.toUpperCase() + ". Format command as 'VERB<space>NOUN' or 'quit' or 'view' or 'help' or 'inventory'");
            }
        }
    }
}
