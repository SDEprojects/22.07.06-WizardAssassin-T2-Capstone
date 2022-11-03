package com.wizard_assassin;

import java.util.*;

class Data {
    List<Location> locations;

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Location getPickedLocation(String userInput){
        for (Location location : getLocations())
            if (location.getName().equals(userInput)) {
                return location;
            }
        return null; // ThisLocationNotFoundException()
    }
}


