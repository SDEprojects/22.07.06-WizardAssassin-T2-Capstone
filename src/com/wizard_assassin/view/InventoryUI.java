package com.wizard_assassin.view;

import java.util.HashMap;
import java.util.Map;

public class InventoryUI {
    Map<String,String> playerInventory = new HashMap<String,String>();

    public InventoryUI() {
        playerInventory.put("stick", "ObjectsAssets/Exposure_5.png");
        playerInventory.put("brass key", "ObjectsAssets/brass_keys.jpg");
        playerInventory.put("note", "ObjectsAssets/Layer_9.png");
        playerInventory.put("sword", "ObjectsAssets/Exposure_4.png");
        playerInventory.put("tunic", "ObjectsAssets/Layer_32.png");
        playerInventory.put("knife", "ObjectsAssets/Exposure_9.png");
        playerInventory.put("poison", "ObjectsAssets/Group_1.png");
        playerInventory.put("diamond key", "ObjectsAssets/diamond_keys.jpg");
        playerInventory.put("wizard robes", "ObjectsAssets/Layer_57.png");
        playerInventory.put("mead", "ObjectsAssets/07.png");
        playerInventory.put("jewels", "ObjectsAssets/Layer_30.png");
        playerInventory.put("password", "ObjectsAssets/pass.jpg");
        playerInventory.put("hunk of meat", "ObjectsAssets/Layer 6.png");
    }

    public String inventorySetter(String inventoryItem){
        String file;
        file = playerInventory.get(inventoryItem);
        return file;
    }

}
