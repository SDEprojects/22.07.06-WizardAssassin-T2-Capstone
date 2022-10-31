package com.WizardAssassin;

public enum CharacterVerbs {
    TALK("talk"),
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