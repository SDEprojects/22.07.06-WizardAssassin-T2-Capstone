package com.wizard_assassin.model;

public class ItemsObject {
    public ItemsObject() {
    }

    public ItemsObject(String name, String description, String attribute, String location) {
        this.name = name;
        this.description = description;
        this.attribute = attribute;
        this.location = location;
    }

    private String name;
    private String description;
    private String attribute;
    private String location;

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getAttribute() {
        return attribute;
    }
    public String getLocation() {
        return location;
    }

}
