package com.wizard_assassin;

public enum CharacterVerbs {
    SPEAK("speak"),
    FIGHT("fight");

    private String characterVerb;

    CharacterVerbs(String characterVerb) {
        this.characterVerb = characterVerb;
    }

    public String characterVerb() {
        return characterVerb;
    }

    public String toString() {
        return characterVerb();
    }
}