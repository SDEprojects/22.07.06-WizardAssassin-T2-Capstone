package com.wizard_assassin;

import java.util.Arrays;
import java.util.Map;

public class Location {

        public String name;
        public String description;
        Map<String, String> directions;
        public String [] items;
        private String [] characters;

    public Location() {
    }

    public Location(String name, String description, Map<String, String> directions, String[] items) {
            this.name = name;
            this.description = description;
            this.directions = directions;
            this.items = items;
        }

    public Location(String name, String description, Map<String, String> directions, String[] items, String[] characters) {
        this.name = name;
        this.description = description;
        this.directions = directions;
        this.items = items;
        this.characters = characters;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Map<String, String> getDirections() {
            return directions;
        }

        public void setDirections(Map<String, String> directions) {
            this.directions = directions;
        }

        public String[] getItem() {
            return items;
        }

        public void setItem(String[] item) {
            this.items = item;
        }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public String[] getCharacters() {
        return characters;
    }

    public void setCharacters(String[] characters) {
        this.characters = characters;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", directions=" + getDirections() +
                ", items=" + getItems() +
                ", characters=" + getCharacters() +
                '}';
    }
}
