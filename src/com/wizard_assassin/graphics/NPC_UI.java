package com.wizard_assassin.graphics;

import java.util.HashMap;
import java.util.Map;

class NPC_UI {
    Map<String, String> npcUI = new HashMap<>();

    public NPC_UI() {
        npcUI.put("rat","NPC_Assets/rat.png");
        npcUI.put("guard", "NPC_Assets/knight.png");
        npcUI.put("soldier", "NPC_Assets/guard.png");
        npcUI.put("prisoner", "NPC_Assets/prisoner.png");
        npcUI.put("knight", "NPC_Assets/prisoner.png");
        npcUI.put("servant", "NPC_Assets/prisoner.png");
        npcUI.put("king's hand", "NPC_Assets/prisoner.png");
        npcUI.put("monster", "NPC_Assets/prisoner.png");
        npcUI.put("evil wizard", "NPC_Assets/prisoner.png");
        npcUI.put("queen", "NPC_Assets/prisoner.png");
    }

    public String npcSetter(String npc) {
        String file;
        file = npcUI.get(npc);
        return file;
    }
}