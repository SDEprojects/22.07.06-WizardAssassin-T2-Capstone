package com.wizard_assassin;

public class ExtraCharacters {

    String type;
    String name;
    String room;
    String quote;

    public ExtraCharacters(String type, String name, String room, String quote) {
        this.type = type;
        this.name = name;
        this.room = room;
        this.quote = quote;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }

    public String getQuote() {
        return quote;

    }
}
