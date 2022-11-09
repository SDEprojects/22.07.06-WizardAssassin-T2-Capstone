package com.wizard_assassin.model;

import java.util.List;

public class ExtraCharacters {

    String type;
    String name;
    String room;
    List<String> quote;

    public ExtraCharacters() {
    }

    public ExtraCharacters(String type, String name, String room, List<String> quote) {
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

    public List<String> getQuote() {
        return quote;

    }
}
