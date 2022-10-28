import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class Inventory {
    public void add(Data masterObj) throws IOException {

        String getItem = "Stick";

        Location currentLocation = masterObj.getLocations().get(0);
        System.out.println(currentLocation.getDescription() + "\n");
        System.out.println("You see these items: " + Arrays.toString(currentLocation.getItem()));
        //Location Inventory = masterObj.getLocations().get(13);


        System.out.println("From the " + currentLocation.getName() + " you can go to the: ");
        for (Map.Entry<String, String> direction : currentLocation.getDirections().entrySet())
            System.out.println("     " + direction.getKey() + ": " + direction.getValue());

        System.out.println("You  see these items: " + Arrays.toString(currentLocation.getItem()));
        List<String> roomItems = new ArrayList<String>(Arrays.asList(currentLocation.getItem()));

        // Add items to room "drop"
//        roomItems.add("stones");
//        roomItems.add("pebbles");
        // Pick up item step 1, remove from room items
        roomItems.remove(getItem);
        Location inventory = masterObj.getLocations().get(13);
        List<String> inventoryItems = new ArrayList<String>(Arrays.asList(inventory.getItem()));
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
        // END of INVENTORY


        // NOTE convert roomItems List to array. Update masterObj with changes
        String[] updatedRoomItems = roomItems.toArray(new String[0]);
        currentLocation.setItem(updatedRoomItems);
        System.out.println("In the room these items remain: " + Arrays.toString(currentLocation.getItem()));
        //System.out.println("The room now has: " + Arrays.toString(currentLocation.getItem()));
    }

}
