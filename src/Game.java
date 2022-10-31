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
    private int count = 0;
    private Location currentLocation = obj.getLocations().get(14);
    private String oldLocation = "";
    List<String> npcNames = new ArrayList<>();

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
            System.out.println("\n\u001B[91m                         *********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
            chooseLocation();
        }
    }

    public void getItem(String itemInput, Location currentLocation, String verb) throws IOException {

        List<String> roomItems = new ArrayList<String>(Arrays.asList(currentLocation.getItem()));

        if(verb.equals("get") && !inventoryItems.contains(itemInput)) {
            roomItems.remove(itemInput);
            inventoryItems.add(itemInput);
            System.out.println("\n");
            System.out.printf("You picked up a \033[32m%s\033[0m and added it to your inventory.\n", itemInput);
        }
        else if(verb.equals("get") && inventoryItems.contains(itemInput)) {
            System.out.println("\nCan not \033[92m" + verb.toUpperCase() + "\033[0m \u001B[31m" + itemInput.toUpperCase() + "\u001B[0m. It's already in your inventory. Choose again...");
        }
        else if(verb.equals("drop") && inventoryItems.contains(itemInput)) {
            inventoryItems.remove(itemInput);
            roomItems.add(itemInput);
            System.out.println("\n");
            System.out.printf("You dropped the \033[32m%s\033[0m and removed it from your inventory.\n", itemInput);
        }
        else if(verb.equals("drop") && !inventoryItems.contains(itemInput)) {
            System.out.println("\nCan not \033[92m" + verb.toUpperCase() + "\033[0m \u001B[31m" + itemInput.toUpperCase() + "\u001B[0m. It is not in your inventory. Choose again...");
        }

        // Pick up item step 2, Put item in inventory
        String[] toInventory = new String[inventoryItems.size()];
        toInventory = inventoryItems.toArray(toInventory);
        inventory.setItem(toInventory);

        // INVENTORY PRINT OUT
        checkInventory();
        System.out.printf("You now see these items in the room: \033[32m%s\033[0m", roomItems);
        // END of INVENTORY

        // NOTE convert roomItems List to array. Update masterObj with changes
        String[] updatedRoomItems = roomItems.toArray(new String[0]);
        currentLocation.setItem(updatedRoomItems);
    }

    public void checkInventory() {
        System.out.println();
        System.out.println("*** Inventory ***");
        System.out.printf("Your inventory: \033[92m%s\033[0m", inventoryItems);
        System.out.println();
    }

    public void chooseLocation() throws IOException {
        Gson gson = new Gson();

//        inventoryItems.add("password");
//        inventoryItems.add("diamond key");
//        inventoryItems.add("sword");
//        inventoryItems.add("tunic");
//        inventoryItems.add("wizard robes");
//        inventoryItems.add("knife");

        Reader read = Files.newBufferedReader(Paths.get("./resources/characters.json"));
        Characters object = gson.fromJson(read, Characters.class);
        Map<String, String> characterQuotes = new HashMap<>();
        for (ExtraCharacters extraCharacters : object.getCharacters())
            characterQuotes.put(extraCharacters.getName().toLowerCase(), extraCharacters.getQuote());

        while (true) {

            if (currentLocation.getName().equals("Laboratory") && (inventoryItems.contains("poison")))
            {
                System.out.println("You have poisoned the wizard. You return home as a hero who saved your kingdom.");
                break;
            }

            if(!oldLocation.equals(currentLocation.getName())) {
                System.out.println("\n\u001B[35m                                              *********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");

                System.out.println(currentLocation.getDescription() + "\n");
                System.out.println("You see the following characters: ");
                npcNames.clear();
                for (ExtraCharacters extraCharacters : object.getCharacters()) {
                    if ((currentLocation.getName().equals(extraCharacters.getRoom()))) {
                        System.out.printf("             \u001B[93m%s\u001B[0m%n", extraCharacters.getName().toUpperCase());
                        npcNames.add(extraCharacters.getName().toLowerCase());
                    }
                }
                System.out.println();
                if(currentLocation.getName().equals("Wizard's Foyer") && !inventoryItems.contains("wizard robes")){
                    System.out.println("\033[91mThe monster bites your head off and you die!\033[0m");
                    System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
                    break;
                }

                if(currentLocation.getItem().length > 0) {
                    System.out.printf("You see these items: \u001B[32m %s \u001B[0m%n", Arrays.deepToString(currentLocation.getItem()));
                }
                if(!currentLocation.getDirections().isEmpty()) {
                    System.out.println("From the " + currentLocation.getName() + " you can go to the:");
                    for (Map.Entry<String, String> direction : currentLocation.getDirections().entrySet()) {
                            System.out.printf("       \u001B[31m %s: %s \u001B[0m%n", direction.getKey(), direction.getValue());
                    }
                }
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
                System.out.println("\n\u001B[91m                         *********  You are in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
            }
            else if(parseInput.length == 2 || parseInput.length == 3) {
                String inputVerb = parseInput[0];
                String inputNoun = parseInput[1];
                if(parseInput.length == 3) {
                    inputNoun = String.format("%s %s", inputNoun, parseInput[2]);
                }
                if (Verbs.getMoveActions().contains(inputVerb)) {

                        if (currentLocation.directions.get(inputNoun) != null) {
                            String locationInput = currentLocation.directions.get(inputNoun);
                            if(locationInput.equals("Courtyard") && currentLocation.getName().equals("Church")) {
                                if(npcNames.isEmpty()) {
                                    currentLocation = obj.getPickedLocation(locationInput);
                                }
                                else {
                                    System.out.printf("The \033[31m%s\033[0m blocks your path. You must fight it.\nUse the stick%n", npcNames.get(0).toUpperCase());
                                }
                            }
                            else if(locationInput.equals("Great Hall") && currentLocation.getName().equals("Courtyard") && count == 0) {
                                if(inventoryItems.contains("password")) {
                                    System.out.println("\033[31mGuard:\033[0m That's the right \033[92mPASSWORD\033[0m. Go ahead and pass.");
                                    System.out.println();
                                    while(true) {
                                        System.out.println("Hit 'enter' to continue");
                                        String progress = inputScanner.nextLine();
                                        if(progress.equals("")) {
                                            break;
                                        }
                                    }
                                    count++;
                                    currentLocation = obj.getPickedLocation(locationInput);
                                }
                                else {
                                    System.out.println("\033[31mGuard:\033[0m Wrong \033[92mPASSWORD\033[0m! Get outta here, ya scum!");
                                    System.out.println("\n\u001B[91m                         *********  You remain in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
                                }
                            }
                            else if(locationInput.equals("Royal Lounge") && currentLocation.getName().equals("Great Hall") && count == 1) {
                                if(inventoryItems.contains("tunic") && inventoryItems.contains("sword")) {
                                    System.out.println("\033[31mGuard:\033[0m I don't know you... but you have the Kingdom's \033[92mTUNIC\033[0m... and that \033[92mSWORD\033[0m... You must be new... go ahead and pass.");
                                    System.out.println();
                                    while(true) {
                                        System.out.println("Hit 'enter' to continue");
                                        String progress = inputScanner.nextLine();
                                        if(progress.equals("")) {
                                            break;
                                        }
                                    }
                                    count++;
                                    currentLocation = obj.getPickedLocation(locationInput);
                                }
                                else {
                                    System.out.println("\033[31mGuard:\033[0m Where do you think you're goin? Only knights can pass through here.\nAnd not just any bloak with a Kingdom's \033[92mTUNIC\033[0m.\nYou need a \033[92mSWORD\033[0m too.");
                                    System.out.println("\n\u001B[91m                         *********  You remain in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
                                }
                            }
                            else if(locationInput.equals("Wizard's Foyer") && currentLocation.getName().equals("Great Hall") && count <= 2) {
                                if(inventoryItems.contains("diamond key")) {
                                    System.out.println("Maybe I can \033[31mUSE\033[0m that \033[92mDIAMOND KEY\033[0m on this door.");
                                }
                                else {
                                    System.out.println("Hmm, it's locked. There's an emblem in the shape of a \033[92mDIAMOND\033[0m on the door");
                                    System.out.println("\n\u001B[91m                         *********  You remain in the " + currentLocation.getName() + ". *********\u001B[0m\n\n");
                                }
                            }
                            else {
                                currentLocation = obj.getPickedLocation(locationInput);
                            }
                        }
                        else {
                            System.out.println("\n\u001B[31m" + inputNoun.toUpperCase() + "\u001B[0m is not a valid direction. Choose again...");
                        }
                }
                else if (Verbs.getItemActions().contains(inputVerb)) {
                        if(inputVerb.equals("use") && inputNoun.equals("diamond key") && currentLocation.getName().equals("Great Hall")) {
                            System.out.println("That \033[92mDIAMOND KEY\033[0m did the trick. You're in...");
                            System.out.println();
                            while(true) {
                                System.out.println("Hit 'enter' to continue");
                                String progress = inputScanner.nextLine();
                                if(progress.equals("")) {
                                    break;
                                }
                            }
                            count++;
                            currentLocation = obj.getPickedLocation("Wizard's Foyer");
                        }
                        else if (Arrays.asList(currentLocation.getItem()).contains(inputNoun) || inventoryItems.contains(inputNoun)){
                            getItem(inputNoun, currentLocation, inputVerb);
                        }
                        else {
                            System.out.println("\nCan not \033[92m" + inputVerb.toUpperCase() + "\033[0m \u001B[31m" + inputNoun.toUpperCase() + "\u001B[0m. Choose again...");
                        }
                }
                else if (Verbs.getCharacterActions().contains(inputVerb)) {
                    if(npcNames.contains(inputNoun)) {
                        if(inputVerb.equals("talk")) {
                            if(!inputNoun.equals("queen")) {
                                System.out.printf("\u001B[93m%s\u001B[0m: '%s'%n", inputNoun.toUpperCase(), characterQuotes.get(inputNoun));
                            }
                            else {
                                System.out.printf("\u001B[93m%s\u001B[0m: '\033[95m%s\033[0m'%n", inputNoun.toUpperCase(), characterQuotes.get(inputNoun));
                                while(true) {
                                    System.out.println();
                                    System.out.println("Input 'yes' to continue, 'no' to quit");
                                    userInput = inputScanner.nextLine().trim().toLowerCase();
                                    if(userInput.equals("yes")) {
                                        currentLocation = obj.getPickedLocation("Church");
                                        break;
                                    }
                                    else if(userInput.equals("no")) {
                                        quitGame();
                                    }
                                }
                            }
                        }
                        else if(inputVerb.equals("fight")) {
                            if(inputNoun.equals("evil wizard")) {
                                if(!inventoryItems.contains("knife")) {
                                    System.out.println("\033[91mThe Wizard suddenly blasts your head off with a thunder bolt... and you die!\033[0m");
                                    System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
                                    break;
                                }
                                else if(inventoryItems.contains("knife")){
                                    System.out.println("\033[36mThe Wizard suddenly attacks you with a thunder bolt but you matrix dodge it.\n You shank him with the\033[0m \033[92mKNIFE\033[0m \033[36mand he dies!\033[0m");
                                    System.out.println("You have shanked the wizard to death. You return home as a hero who saved your kingdom!");
                                    break;
                                }
                            }
                            else if(inventoryItems.contains("sword")){
                                int characterIndex= npcNames.indexOf(inputNoun);
                                object.getCharacters().remove(characterIndex);
                                npcNames.remove(inputNoun);
                                if(!npcNames.isEmpty() || inputNoun.equals("rat")) {
                                    System.out.printf("You stab \033[91m%s\033[0m in the heart and they die.\n Miraculously, no one notices.%n", inputNoun.toUpperCase());
                                }
                                else {
                                    System.out.println("You've been found out!");
                                    System.out.println("Should've listened to the Queen and not gone on that killing spree... You lose");
                                    System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
                                    break;
                                }
                            }
                            else if(inventoryItems.contains("stick") && inputNoun.equals("rat")) {
                                System.out.printf("You beat the \033[91m%s\033[0m to death with the \033[92mSTICK\033[0m", inputNoun.toUpperCase());
                                int characterIndex= npcNames.indexOf(inputNoun);
                                object.getCharacters().remove(characterIndex);

                                npcNames.remove(inputNoun);
                            }
                            else {
                                System.out.println("I'm going to advise against that.");
                            }
                        }
                    }
                    else {
                        System.out.printf("There is no \u001B[93m%s\u001B[0m here... You must be seeing ghosts.%n", inputNoun.toUpperCase());
                    }
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
