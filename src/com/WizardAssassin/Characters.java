package com.WizardAssassin;

import java.util.List;

class Characters {
    List<ExtraCharacters> characters;

    public Characters(List<ExtraCharacters> characters) {
        this.characters = characters;
    }

    public List<ExtraCharacters> getCharacters() {
        return characters;
    }
    
}
class ExtraCharacters{
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
        // Queen's quote to add later
        // this will be used when implementing talking to the spy to progress game
        // "One more thing, if you talk to a man that says, 'The Mocking Birds around here sing quite beautifully,'
//        then you'll know this man to be our spy.
//        The code he is waiting for is 'Yes, too bad they're dying out'.
//        This phrase will prompt him to give you what you need to complete your mission.
//                He'll recognize you with this *Queen gives you a PENDANT, it has a Mocking Bird carved in it*."
    }
}