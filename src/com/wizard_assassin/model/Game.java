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
    private int count = 0;
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
    private Characters object = null;
    private Map<String, List<String>> characterQuotes = new HashMap<>();


    public Game() {
        //Map of characters and quotes
        ObjectMapper mapper = new ObjectMapper();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream charactersRecFile = classLoader.getResourceAsStream("characters.json");

        try {
            object = mapper.readValue(charactersRecFile, Characters.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (ExtraCharacters extraCharacters : object.getCharacters())
            characterQuotes.put(extraCharacters.getName().toLowerCase(), extraCharacters.getQuote());

        setViewLocation(getLocation());
        setViewInventory(getInventoryItems());
        prompt(getLocation(), characterQuotes, object);
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
            // talk
            if (npcNames.size()>0) {
                if (verb.equals("speak")) {
                    //Generate random int value from 0 to 2 for random sayings
                    int num = (int) (Math.random() * (3));
                    String characterQuote = characterQuotes.get(npcNames.get(0)).get(num);
                    setResponse("\n" + characterQuote);
                } else if (verb.equals("fight")) {
                    fight(npcNames.get(0), object);
                } else {
                    setResponse("\nThere is no one here... You must be seeing ghosts.");
                }
            } else if (Verbs.getAreaActions().contains(verb)) {
                setResponse("\nThis VERB is for area interactions");
            } else {
                setResponse("\nI do not understand " + userInput.toUpperCase());
            }
            //reset location
            setLocation(locationState.getName());

        }
        //location for GUI
        setViewLocation(getLocation());

        //check win condition
        winConditionCheck();

        //set new location text
        prompt(getLocation(), characterQuotes, object);
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

        List<String> roomItems = new ArrayList<String>(Arrays.asList(currentLocation.getItem()));

        if (verb.equals("get") && !inventoryItems.contains(itemInput)) {
            roomItems.remove(itemInput);
            inventoryItems.add(itemInput);

            setResponse("\nYou picked up a " + itemInput + " and added it to your inventory.\n");
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
        if (verb.equals("use") && noun.equals("diamond key") && locationState.getName().equals("Great Hall")) {
            setResponse("\nThat DIAMOND KEY did the trick. You're in...");
            count++;
            locationState = obj.getPickedLocation("Wizard's Foyer");
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
            else if (locationInput.equals("Great Hall") && getLocation().equals("Courtyard") && count == 0) {
                if (inventoryItems.contains("password")) {
                    setResponse("\nGuard: That's the right PASSWORD Go ahead and pass.");
                    locationState = obj.getPickedLocation(locationInput);
                    setLocation(locationState.getName());
                } else {
                    setResponse("\nGuard: Wrong PASSWORD! Get outta here, ya scum!");
                }
            }
            else if (locationInput.equals("Royal Lounge") && getLocation().equals("Great Hall") && count == 1) {
                if (inventoryItems.contains("tunic") && inventoryItems.contains("sword")) {
                    setResponse("\nGuard: I don't know you... but you have the Kingdom's TUNIC... "+
                            "\nand that SWORD... You must be new... go ahead and pass.");
                    count++;
                    locationState = obj.getPickedLocation(locationInput);
                    setLocation(locationState.getName());
                } else {
                    setResponse("\nGuard: Where do you think you're going? Only knights can pass through here."+
                            "\nAnd not just any bloak with a Kingdom's TUNIC. You need a SWORD too.");
                }
            }
            else if (locationInput.equals("Wizard's Foyer") && locationState.getName().equals("Great Hall") && count <= 2) {
                if (inventoryItems.contains("diamond key")) {
                    setResponse("\nMaybe I can USE that DIAMOND KEY on this door.");
                } else {
                    setResponse("\nHmm, it's locked. There's an emblem in the shape of a DIAMOND on the door");
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
    void fight(String noun, Characters object) {
        List<String> inventoryItems = getInventoryItems();
        if(noun.equals("evil wizard")) {
            if(inventoryItems.contains("knife")) {
                setResponse("\nThe Wizard suddenly blasts your head off with a thunder bolt... and you die!");
                setLoopCondition(false);
            }
            else if(inventoryItems.contains("knife")){
                setResponse("\nThe Wizard suddenly attacks you with a thunder bolt but you matrix dodge it."+
                        "\n You shank him with the KNIFE and he dies!"+
                        "\n\nYou have shanked the wizard to death. You return home as a hero who saved your kingdom!");
                setLoopCondition(false);
            }
        }
        else if(inventoryItems.contains("sword")){
            int characterIndex= npcNames.indexOf(noun);
            object.getCharacters().remove(characterIndex);
            npcNames.remove(noun);
            if(!npcNames.isEmpty() || noun.equals("rat")) {
                setResponse("\nYou stab "+noun.toUpperCase()+" in the heart and they die."+
                        "\n Miraculously, no one notices.");
            }
            else {
                setResponse("You've been found out!"+
                        "\nShould've listened to the Queen and not gone on that killing spree... You lose");
                setLoopCondition(false);

            }
        }
        else if(inventoryItems.contains("stick") && noun.equals("rat")) {
            setResponse("\nYou beat the " + noun.toUpperCase() + " to death with the STICK");
            int characterIndex= npcNames.indexOf(noun);
            object.getCharacters().remove(characterIndex);

            npcNames.remove(noun);
        }
        else {
            setResponse("\nI'm going to advise against that.");
        }
    }

    //win condition
    void winConditionCheck() {
        if (locationState.getName().equals("Laboratory") && (inventoryItems.contains("poison"))) {
            setResponse("You have poisoned the wizard. You return home as a hero who saved your kingdom.");
            setLoopCondition(false);
        }
    }

    void prompt(String currentLocation, Map<String, List<String>> quotes, Characters object) {

        //NPCs
        npcNames.clear();
        for (ExtraCharacters extraCharacters : object.getCharacters()) {
            if ((currentLocation.equals(extraCharacters.getRoom()))) {
                npcNames.add(extraCharacters.getName().toLowerCase());
                quotes.put(extraCharacters.getName(), extraCharacters.getQuote());
            }
        }
        String localNPC = "";
        if (npcNames.size() > 0) {
            localNPC = "\nYou see the following characters: " + npcNames;
        }

        //TODO fix this death condition to work with GUI
        /*
        if (location.equals("Wizard's Foyer") && !inventoryItems.contains("wizard robes")) {
            setResponse("The monster bites your head off and you die!");
            setLoopCondition(false);
        }*/

        //Items
        String localItems = "";
        if (locationState.getItem().length > 0) {
            localItems = "\nYou see these items: " + Arrays.deepToString(locationState.getItem());
        }

        //Directions
        ArrayList<String> directionList = new ArrayList<>();
        if (!locationState.getDirections().isEmpty()) {
            //System.out.println("From the " + locationState.getName() + " you can go to the:");
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
}
