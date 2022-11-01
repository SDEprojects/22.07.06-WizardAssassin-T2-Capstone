package com.wizard_assassin;

import java.util.HashSet;

public interface Verbs {


    static HashSet<String> getMoveActions() {
        HashSet<String> moveValues = new HashSet<>();

        for(MoveVerbs verb : MoveVerbs.values()) {
            String lowerName = verb.name().toLowerCase();
            moveValues.add(lowerName);
        }

        return moveValues;
    }

    static HashSet<String> getItemActions() {
        HashSet<String> itemVerbs = new HashSet<>();

        for(ItemVerbs verb : ItemVerbs.values()) {
            String lowerName = verb.name().toLowerCase();
            itemVerbs.add(lowerName);
        }

        return itemVerbs;
    }

    static HashSet<String> getCharacterActions() {
        HashSet<String> characterVerbs = new HashSet<>();

        for(CharacterVerbs verb : CharacterVerbs.values()) {
            String lowerName = verb.name().toLowerCase();
            characterVerbs.add(lowerName);
        }

        return characterVerbs;
    }

    static HashSet<String> getAreaActions() {
        HashSet<String> areaVerbs = new HashSet<>();

        for(AreaVerbs verb : AreaVerbs.values()) {
            String lowerName = verb.name().toLowerCase();
            areaVerbs.add(lowerName);
        }

        return areaVerbs;
    }
}