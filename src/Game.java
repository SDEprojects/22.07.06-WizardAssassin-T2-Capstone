import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.Gson;

class Game implements Verbs  {

    Game() throws IOException {
    }

    public Data makeObj() throws IOException {
        Gson gson = new Gson();
        Reader reader = Files.newBufferedReader(Paths.get("./resources/Location.json"));
        // https://stackoverflow.com/questions/19169754/parsing-nested-json-data-using-gson
        Data obj = gson.fromJson(reader, Data.class);
        return obj;
    }

    Data obj = makeObj();
    Location inventory = obj.getLocations().get(13);
    List<String> inventoryItems = new ArrayList<String>(Arrays.asList(inventory.getItem()));


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
//            String os = System.getProperty("os.name");
//            System.out.println(os);
            ClearConsole.clearConsole();
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

        String getItem = itemInput;
//        Location currentLocation = obj.getLocations().get(0);


        System.out.println(currentLocation.getDescription() + "\n");
        System.out.println("You see these items: " + Arrays.toString(currentLocation.getItem()));
        //Location Inventory = masterObj.getLocations().get(13);


//        System.out.println("From the " + currentLocation.getName() + " you can go to the: ");
//        for (Map.Entry<String, String> direction : currentLocation.getDirections().entrySet())
//            System.out.println("     " + direction.getKey() + ": " + direction.getValue());

        System.out.println("You  see these items: " + Arrays.toString(currentLocation.getItem()));
        List<String> roomItems = new ArrayList<String>(Arrays.asList(currentLocation.getItem()));

        // Add items to room "drop", use later if we add DROP feature
//        roomItems.add("stones");
//        roomItems.add("pebbles");
        // Pick up item step 1, remove from room items
        roomItems.remove(getItem);
//        Location inventory = obj.getLocations().get(13);
        // TODO removed from here List<String> inventoryItems = new ArrayList<String>(Arrays.asList(inventory.getItem()));
        // List of strings
        inventoryItems.add(getItem);

        // Pick up item step 2, Put item in inventory
        String[] toInventory = new String[inventoryItems.size()];
        toInventory = inventoryItems.toArray(toInventory);
        inventory.setItem(toInventory);
        System.out.println("You picked up the " + getItem + " and added it to inventory. You see : " + roomItems);


        // INVENTORY PRINT OUT
        System.out.println("\n");
        System.out.println(inventory.getName());
        //System.out.println(inventory.getDescription() );
        System.out.println(Arrays.toString(inventory.getItem()));
        System.out.println("end of inventory");
        System.out.println("You have acquired a " + Arrays.toString(inventory.getItem()) + ".");
        // END of INVENTORY


        // NOTE convert roomItems List to array. Update masterObj with changes
        String[] updatedRoomItems = roomItems.toArray(new String[0]);
        currentLocation.setItem(updatedRoomItems);
        System.out.println("In the room these items remain: " + Arrays.toString(currentLocation.getItem()));
        //System.out.println("The room now has: " + Arrays.toString(currentLocation.getItem()));
    }

    public void checkInventory() {
        Location inventory = obj.getLocations().get(13);
        System.out.println("*** Inventory ***");
        System.out.println(inventory);
    }


    public void chooseLocation() throws IOException {
        Gson gson = new Gson();
//        Reader reader = Files.newBufferedReader(Paths.get("./resources/Location.json"));
//        // https://stackoverflow.com/questions/19169754/parsing-nested-json-data-using-gson
//        Data obj = gson.fromJson(reader, Data.class);
        Location currentLocation = obj.getLocations().get(0);

        Reader read = Files.newBufferedReader(Paths.get("./resources/characters.json"));
        Characters object = gson.fromJson(read, Characters.class);
        List<String> inventoryItems = new ArrayList<String>(Arrays.asList(inventory.getItem()));


        boolean condition = true;
        while (condition) {
            // TODO
            inventoryItems.forEach(System.out::println);



            if (currentLocation.getName().equals("Laboratory") && (inventoryItems.contains("poison")))
            {
                System.out.println("You have poisoned the wizard. You return home as a hero who saved your kingdom.");
                condition = false;
            }
            System.out.println("\n\u001B[35m                                              *********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");

            System.out.println(currentLocation.getDescription() + "\n");

            for (ExtraCharacters extraCharacters : object.getCharacters())
                if ((currentLocation.getName().equals(extraCharacters.getRoom())))
                    System.out.println(extraCharacters.getName() +  " says : " + extraCharacters.getQuote());
            System.out.println();

            System.out.println("You see these items: " + Arrays.toString(currentLocation.getItem()));
            System.out.println("From the " + currentLocation.getName() + " you can go to the:");
            for (Map.Entry<String, String> direction : currentLocation.getDirections().entrySet())
                System.out.println("     " + direction.getKey() + ": " + direction.getValue());


            System.out.println("");
            System.out.println("What would you like to do now?\nEnter 'quit' to exit game.\nEnter 'view' to see the map.\nEnter 'help' for list of valid commands.\n Enter 'inventory' to list all your items.");
            String userInput = inputScanner.nextLine().trim().toLowerCase();

            String[] parseInput = userInput.split(" ");

            if(userInput.equals("quit")) {
                condition = false;
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
                        System.out.println("This VERB is for an item action");
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
                    System.out.println("I do not understand " + userInput.toUpperCase() + ". Format command as 'VERB<space>NOUN' or 'quit' or 'help'");
                }
            }
            else {
                System.out.println("I do not understand " + userInput.toUpperCase() + ". Format command as 'VERB<space>NOUN' or 'quit' or 'help'");
            }
        }
    }
}
