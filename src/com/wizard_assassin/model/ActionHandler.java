package com.wizard_assassin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionHandler {

    public ActionHandler() {
    }

    //checks if action is allowed for given location
    boolean actionChecker(String location, String inputAction) {
        boolean result = false;
        ObjectMapper mapper = new ObjectMapper();
            ArrayList<String> actionsList = new ArrayList<>(List.of("help", "quit", "view", "inventory", "drop"));

            try {
                Actions actions = mapper.readValue(getClass().getClassLoader().getResource("actions.json"), Actions.class);

                Map<String,List<String>> actionMap = actions.getActions();

                if (actionMap.containsKey(location)){
                    actionsList.addAll(actionMap.get(location));
                }
                for (String action : actionsList) {
                    if (inputAction.equals(action)) {
                        result = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        return result;
    }

    private static class Actions{
        @JsonProperty("locations")
        private Map<String,List<String>> actions = new HashMap<>();

        public Map<String, List<String>> getActions() {
            return actions;
        }

        public void setActions(Map<String, List<String>> actions) {
            this.actions = actions;
        }
    }

}
