package com.wizard_assassin.graphics;

import java.util.HashMap;
import java.util.Map;

public class InventoryUI {
    Map<String,String> playerInventory = new HashMap<String,String>();

    public InventoryUI() {
        playerInventory.put("stick", "objects assets/Exposure 5.png");
        playerInventory.put("brass Key", "objects assets/key.png");
        playerInventory.put("note", "objects assets/Layer 9.png");
        playerInventory.put("sword", "objects assets/Exposure 4.png");
        playerInventory.put("tunic", "objects assets/Layer 32.png");
        playerInventory.put("knife", "objects assets/Exposure 9.png");
        playerInventory.put("Poison", "objects assets/Group 1.png");
        playerInventory.put("diamond key", "objects assets/cross_key.png");
        playerInventory.put("wizard robes", "objects assets/Exposure 8.png");
        playerInventory.put("mead", "objects assets/Layer 57.png");
        playerInventory.put("jewels", "objects assets/Layer 30.png");
    }

    public String inventorySetter(String inventoryItem){
        String file;
        file = playerInventory.get(inventoryItem);
        return file;
    }

}
