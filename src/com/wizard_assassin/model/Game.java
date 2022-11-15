package com.wizard_assassin.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Game implements Verbs {

    ActionHandler actionHandler = new ActionHandler();
    EventHandler eventHandler = new EventHandler();

    private Data obj = makeObj();
    private Location inventory = obj.getLocations().get(13);
    private List<String> inventoryItems = new ArrayList<>(Arrays.asList(inventory.getItem()));
    private Location locationState = obj.getLocations().get(0);
    private List<String> npcNames = new ArrayList<>();
    private String location = locationState.getName();
    private String description = locationState.getDescription();
    private ArrayList<String> verbNoun = new ArrayList<>(List.of("verb", "noun"));
    private String userInput;
    //TODO change loop to end condition
    boolean loopCondition = true;
    private static String returnPrompt;
    private static String response;
    private static String viewLocation;
    private static List<String> viewInventory = new ArrayList<>();
    private static List<String> viewRoomItems = new ArrayList<>();
    private static List<String> viewRoomNPCs = new ArrayList<>();
    private Character object = null;
    private Item itemsObject = null;
    private Map<String, List<String>> characterQuotes = new HashMap<>();
    private Map<String, String> itemDescription = new HashMap<>();
    private Map<String, List<String>> npcMap = new HashMap<>();
    private List<String> empty = new ArrayList<>(0);
    private boolean poisoned = false;
    private static boolean endGame = false;
    private static boolean loseCondition = false;


    public Game() {
        //Map of characters and quotes
        ObjectMapper mapper = new ObjectMapper();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream charactersRecFile = classLoader.getResourceAsStream("characters.json");

        try {
            object = mapper.readValue(charactersRecFile, Character.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (ExtraCharacters extraCharacters : object.getCharacters()) {
            characterQuotes.put(extraCharacters.getName().toLowerCase(), extraCharacters.getQuote());
            List<String> list = new ArrayList<>(List.of(extraCharacters.getName()));
            npcMap.put(extraCharacters.getRoom(), list);
        }

        setNpcNames(npcMap.get(getLocation()));
        setViewLocation(getLocation());
        setViewInventory(getInventoryItems());
        prompt(getLocation());

    }

    public void gameLoop(String controllerInput) {

        //reset response and set user input
        setResponse("");
        setUserInput(controllerInput);

        //User input  set verb noun variables
        userInputPrompt();
        String verb = getVerbNoun().get(0);
        String noun = getVerbNoun().get(1);

        //Actions
        if ("go".equals(verb) && Verbs.getMoveActions().contains(verb)) {
            //move
            action(verb, noun);
        }
        //use items
        else if (Verbs.getItemActions().contains(verb)) {
            useItem(verb, noun);
        }
        //character actions
        else if (Verbs.getCharacterActions().contains(verb)) {
            noun = npcMap.get(getLocation()).get(0);
            // talk
            if (npcMap.get(getLocation())!=null && !npcMap.get(getLocation()).isEmpty()) {
                if (verb.equals("speak")) {
                    //Generate random int value from 0 to 2 for random sayings
                    int num = (int) (Math.random() * (3));
                    String characterQuote = characterQuotes.get(noun).get(num);
                    setResponse("\n" + characterQuote);
                } else if (verb.equals("fight")) {
                    fight(noun);
                }
            }
            else {
                    setResponse("\nThere is no one here... You must be seeing ghosts.");
                }
        }

        else if (verb.equals("examine")) {
            examine(noun);

            }
        else {
                setResponse("\nI do not understand " + userInput.toUpperCase());
            }
        //reset location
        setLocation(locationState.getName());

        //location for GUI
        setViewLocation(getLocation());

        //check end conditions
        setEndGame(!isLoopCondition());

        //set new location text
        prompt(getLocation());

        //
        setViewRoomNPCs(npcMap.get(getLocation()));
    }

    private void examine(String noun) {
        //Map of game items
        ObjectMapper mapper = new ObjectMapper();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream charactersRecFile = classLoader.getResourceAsStream("Items.json");

        try {
            itemsObject = mapper.readValue(charactersRecFile, Item.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (ItemsObject gameItems : itemsObject.getItems())
            itemDescription.put(gameItems.getName().toLowerCase(), gameItems.getDescription());
        setResponse("\n" + itemDescription.get(noun));
    }

    public Data makeObj() {

        ObjectMapper mapper = new ObjectMapper();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream locationRecFile = classLoader.getResourceAsStream("Location.json");
            obj = mapper.readValue(locationRecFile, Data.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    public void getAnItem(String itemInput, Location currentLocation, String verb) throws IOException {

        List<String> roomItems = new ArrayList<>(Arrays.asList(currentLocation.getItem()));

        if (verb.equals("get") && !inventoryItems.contains(itemInput)) {
            if(inventoryItems.size()<6) {
                roomItems.remove(itemInput);
                inventoryItems.add(itemInput);

                setResponse("\nYou picked up a " + itemInput + " and added it to your inventory.\n");
            }
            else {
                setResponse("Your inventory is full. Drop an Item to make room");
            }
        } else if (verb.equals("get") && inventoryItems.contains(itemInput)) {
            setResponse("\nCan not " + verb.toUpperCase() + " " + itemInput.toUpperCase() +
                    ". It's already in your inventory. Choose again...");
        } else if (verb.equals("drop") && inventoryItems.contains(itemInput)) {
            inventoryItems.remove(itemInput);
            roomItems.add(itemInput);

            setResponse("You dropped the "+itemInput+" and removed it from your inventory.");
        } else if (verb.equals("drop") && !inventoryItems.contains(itemInput)) {
            setResponse("\nCan not " + verb.toUpperCase() + " " + itemInput.toUpperCase() +
                    ". It is not in your inventory. Choose again...");
        }

        // Pick up item step 2, Put item in inventory
        String[] toInventory = new String[inventoryItems.size()];
        toInventory = inventoryItems.toArray(toInventory);
        inventory.setItem(toInventory);

        // END of INVENTORY

        // NOTE convert roomItems List to array. Update masterObj with changes
        String[] updatedRoomItems = roomItems.toArray(new String[0]);
        currentLocation.setItem(updatedRoomItems);
    }

    private void useItem(String verb, String noun) {

        if ("diamond".equals(noun)){
            noun = "diamond key";
        }
        if ("brass".equals(noun)){
            noun = "brass key";
        }
        if ("meat".equals(noun)){
            noun = "hunk of meat";
        }
        if (verb.equals("use")){

            if (noun.equals("diamond key") && locationState.getName().equals("Great Hall")){
                setResponse("\nThat DIAMOND KEY did the trick. You're in...");
                locationState = obj.getPickedLocation("Wizard's Foyer");
            }
            else if (noun.equals("brass key") && locationState.getName().equals("Dungeon")){
                setResponse("\nThank you! Here's the password");
                npcMap.replace(getLocation(),empty);
                setNpcNames(npcMap.get(getLocation()));
                inventoryItems.add("password");
            }
            else if (noun.equals("poison") && locationState.getName().equals("Laboratory") && inventoryItems.contains("hunk of meat")) {
                setResponse("\nYou've poisoned the meat. I bet the wizard would like this!");
                inventoryItems.remove("poison");
                setPoisoned(true);

            }
            else if (noun.equals("hunk of meat") && locationState.getName().equals("Wizard’s Chambers") && isPoisoned()) {
                setResponse("\nThe wizard couldn't resist your mutton. \nHuzzah! The poison killed the wizard!!!");
                setLoopCondition(false);
            }
            else {
                setResponse("\nCan not " + verb.toUpperCase() + " " + noun.toUpperCase() + ". Choose again...");
            }
        }
        else if (Arrays.asList(locationState.getItem()).contains(noun) || inventoryItems.contains(noun)) {

                try {
                    getAnItem(noun, locationState, verb);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        else {
            setResponse("\nCan not " + verb.toUpperCase() + " " + noun.toUpperCase() + ". Choose again...");
        }
    }

    private void action(String verb, String noun) {
        //move
        if (locationState.directions.get(noun) != null) {
            String locationInput = locationState.directions.get(noun);
            if (locationInput.equals("Courtyard") && getLocation().equals("Church")) {
                if (npcNames.isEmpty()) {
                    locationState = obj.getPickedLocation(locationInput);
                    setLocation(locationState.getName());
                } else {
                    setResponse("\nThe " + npcNames.get(0).toUpperCase() + " blocks your path. You must fight it. Use the stick");
                }
            }
            else if (locationInput.equals("Great Hall") && getLocation().equals("Courtyard")) {
                if (inventoryItems.contains("password")) {
                    setResponse("\nGuard: That's the right PASSWORD Go ahead and pass.");
                    locationState = obj.getPickedLocation(locationInput);
                    setLocation(locationState.getName());
                } else {
                    setResponse("\nGuard: Wrong PASSWORD! Get outta here, ya scum!");
                }
            }
            else if (locationInput.equals("Royal Lounge") && getLocation().equals("Great Hall")) {
                if (inventoryItems.contains("tunic") && inventoryItems.contains("sword")) {
                    setResponse("\nGuard: I don't know you... but you have the Kingdom's TUNIC... "+
                            "\nand that SWORD... You must be new... go ahead and pass.");
                    locationState = obj.getPickedLocation(locationInput);
                    setLocation(locationState.getName());
                } else {
                    if(inventoryItems.contains("tunic")) {
                        setResponse("\nGuard: Where do you think you're going? Only knights can pass through here." +
                                "\nAnd not just any bloak with a Kingdom's TUNIC. You need a SWORD too.");
                    }
                    else{
                        setResponse("\nGuard: Where do you think you're going? Only knights can pass through here." +
                                "\nAnd not just any bloak with a Kingdom's SWORD. You need a TUNIC too.");
                    }
                }
            }
            else if (locationInput.equals("Wizard's Foyer") && locationState.getName().equals("Great Hall")) {
                if (inventoryItems.contains("diamond key")) {
                    setResponse("\nMaybe I can USE that DIAMOND KEY on this door.");
                } else {
                    setResponse("\nHmm, it's locked. There's an emblem in the shape of a DIAMOND on the door");
                }
            }
            else if (locationInput.equals("Wizard’s Chambers") && locationState.getName().equals("Wizard's Foyer")){
                if (!inventoryItems.contains("wizard robes")) {
                    setResponse("The monster bites your head off and you die! \nYou should have used the wizard's cloak.");
                    setLoopCondition(false);
                    setLoseCondition(true);
                }
                else{
                    setResponse("You look just like a wizard");
                    locationState = obj.getPickedLocation(locationInput);
                    setLocation(locationState.getName());
                }
            }
            else {
                locationState = obj.getPickedLocation(locationInput);
                setLocation(locationState.getName());
            }
        }
        else {
            setResponse("\n"+noun.toUpperCase() + " is not a valid direction. Choose again...");
        }

    }

    //fight method
    void fight(String noun) {
        List<String> inventoryItems = getInventoryItems();

        setNpcNames(npcMap.get(getLocation()));

        String npc = getNpcNames().get(0);
        if(npc.equals("evil wizard")) {
            if(!inventoryItems.contains("knife")) {
                setResponse("\nThe Wizard suddenly blasts your head off with a thunder bolt... and you die!");
                setLoopCondition(false);
                setLoseCondition(true);
            }
            else if(inventoryItems.contains("knife")){
                setResponse("\nThe Wizard suddenly attacks you with a thunder bolt but you matrix dodge it."+
                        "\nYou have shanked the wizard to death. You return home as a hero who saved your kingdom!");
                setLoopCondition(false);
            }
        }
        else if(inventoryItems.contains("sword")){

            if(!npcNames.isEmpty() & !npc.equals("guard")) {
                if (!npc.equals("prisoner") && !npc.equals("monster")) {
                    setResponse("\nYou stab " + noun.toUpperCase() + " in the heart and they die." +
                            "\n Miraculously, no one notices.");

                    npcMap.replace(getLocation(), empty);
                    setNpcNames(npcMap.get(getLocation()));
                } else {
                    setResponse("\nI'm going to advise against that.");
                }
            }
            else {
                setResponse("You've been found out!"+
                        "\nShould've listened to the Queen and not gone on that killing spree... You lose");
                setLoopCondition(false);
                setLoseCondition(true);
                npcMap.replace(getLocation(),empty);
                setNpcNames(npcMap.get(getLocation()));

            }
        }
        else if(inventoryItems.contains("stick") && noun.equals("rat")) {
            setResponse("\nYou beat the " + noun.toUpperCase() + " to death with the STICK");

            npcMap.replace(getLocation(),empty);
            setNpcNames(npcMap.get(getLocation()));

        }
        else {
            setResponse("\nI'm going to advise against that.");
        }
    }

    void prompt(String currentLocation) {

        //NPCs
        String localNPC = "";

        if (npcMap.get(getLocation())!=null && !npcMap.get(getLocation()).isEmpty()) {
            localNPC = "\nYou see the following characters: " + npcMap.get(getLocation());
        }
        setViewRoomNPCs(getNpcNames());

        //Items
        String localItems = "";
        if (locationState.getItem().length > 0) {
            localItems = "\nYou see these items: " + Arrays.deepToString(locationState.getItem());
        }
        //set for viewing
        setViewRoomItems(Arrays.asList(locationState.getItem()));

        //Directions
        ArrayList<String> directionList = new ArrayList<>();
        if (!locationState.getDirections().isEmpty()) {
            Map<String,String> direction = locationState.getDirections();
            direction.forEach((k,v) -> directionList.add(k));
        }

        //location
        setReturnPrompt(locationState.getDescription() + "\n" +
                        localNPC+
                        localItems+ "\nYou can go: " + directionList +"\n");
    }


    //user input processor, sets verbNoun attribute array
    private void userInputPrompt() {
        boolean validInput = false;
        while (!validInput) {

            TextParser parser = new TextParser();
            //verb-noun pair array using text parser
            ArrayList<String> result = parser.textParser(userInput);

            //checks verbs and nouns for validity
            if (!"verb".equals(result.get(0))) {
                if (actionHandler.actionChecker(getLocation(), result.get(0))) {
                    validInput = true;
                    //sends to event handler if a global command
                    eventHandler.eventHandler(userInput);
                    setVerbNoun(result);
                } else {
                    setResponse("Invalid Input: Enter as Prompted (verb and noun)");
                }
            }
        }
    }




    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public boolean isLoopCondition() {
        return loopCondition;
    }

    public void setLoopCondition(boolean loopCondition) {
        this.loopCondition = loopCondition;
    }

    public ArrayList<String> getVerbNoun() {
        return verbNoun;
    }

    public void setVerbNoun(ArrayList<String> verbNoun) {
        this.verbNoun = verbNoun;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public List<String> getInventoryItems() {
        return inventoryItems;
    }


    public static String getReturnPrompt() {
        return returnPrompt;
    }

    public void setReturnPrompt(String returnPrompt) {
        this.returnPrompt = returnPrompt;
    }

    public static String getResponse() {
        return response;
    }

    public static void setResponse(String response) {
        Game.response = response;
    }

    public static String getViewLocation() {
        return viewLocation;
    }

    public static void setViewLocation(String viewLocation) {
        Game.viewLocation = viewLocation;
    }

    public static List<String> getViewInventory() {
        return viewInventory;
    }

    public static void setViewInventory(List<String> viewInventory) {
        Game.viewInventory = viewInventory;
    }

    public static List<String> getViewRoomItems() {
        return viewRoomItems;
    }

    public static void setViewRoomItems(List<String> viewRoomItems) {
        Game.viewRoomItems = viewRoomItems;
    }

    public Location getLocationState() {
        return locationState;
    }

    public static List<String> getViewRoomNPCs() {
        return viewRoomNPCs;
    }

    public static void setViewRoomNPCs(List<String> viewRoomNPCs) {
        Game.viewRoomNPCs = viewRoomNPCs;
    }

    public List<String> getNpcNames() {
        return npcNames;
    }

    public void setNpcNames(List<String> npcNames) {
        this.npcNames = npcNames;
    }

    public static boolean isEndGame() {
        return endGame;
    }

    public static void setEndGame(boolean endGame) {
        Game.endGame = endGame;
    }

    public static boolean isLoseCondition() {
        return loseCondition;
    }

    public static void setLoseCondition(boolean loseCondition) {
        Game.loseCondition = loseCondition;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public void setPoisoned(boolean poisoned) {
        this.poisoned = poisoned;
    }
}
