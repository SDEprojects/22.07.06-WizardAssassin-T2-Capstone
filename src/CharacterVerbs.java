public enum CharacterVerbs {
    TALK("talk"),
    SPEAK("speak"),
    ASK("ask"),
    EXPLAIN("explain"),
    TELL("tell"),
    DISCUSS("discuss");

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