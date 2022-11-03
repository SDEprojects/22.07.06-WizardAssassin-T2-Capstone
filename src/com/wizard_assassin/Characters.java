package com.wizard_assassin;

import java.util.List;

class Characters {
    List<ExtraCharacters> characters;

    public Characters(List<ExtraCharacters> characters) {
        this.characters = characters;
    }

    public List<ExtraCharacters> getCharacters() {
        return characters;
    }



    //Character Methods
    static void fight(String noun, Characters object) {
        List<String> inventoryItems = Game.getInventoryItems();
        if(noun.equals("evil wizard")) {
            if(inventoryItems.contains("knife")) {
                System.out.println("\033[91mThe Wizard suddenly blasts your head off with a thunder bolt... and you die!\033[0m");
                System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
                Game.setLoopCondition(false);
            }
            else if(inventoryItems.contains("knife")){
                System.out.println("\033[36mThe Wizard suddenly attacks you with a thunder bolt but you matrix dodge it.\n You shank him with the\033[0m \033[92mKNIFE\033[0m \033[36mand he dies!\033[0m");
                System.out.println("You have shanked the wizard to death. You return home as a hero who saved your kingdom!");
                Game.setLoopCondition(false);
            }
        }
        else if(inventoryItems.contains("sword")){
            int characterIndex= Game.npcNames.indexOf(noun);
            object.getCharacters().remove(characterIndex);
            Game.npcNames.remove(noun);
            if(!Game.npcNames.isEmpty() || noun.equals("rat")) {
                System.out.printf("You stab \033[91m%s\033[0m in the heart and they die.\n Miraculously, no one notices.%n", noun.toUpperCase());
            }
            else {
                System.out.println("You've been found out!");
                System.out.println("Should've listened to the Queen and not gone on that killing spree... You lose");
                System.out.println("\033[91mG\033[0m\033[30mA\033[0m\033[91mM\033[0m\033[30mE\033[0m \033[91mO\033[0m\033[30mV\033[0m\033[91mE\033[0m\033[30mR\033[0m!");
                Game.setLoopCondition(false);

            }
        }
        else if(inventoryItems.contains("stick") && noun.equals("rat")) {
            System.out.printf("You beat the \033[91m%s\033[0m to death with the \033[92mSTICK\033[0m", noun.toUpperCase());
            int characterIndex= Game.npcNames.indexOf(noun);
            object.getCharacters().remove(characterIndex);

            Game.npcNames.remove(noun);
        }
        else {
            System.out.println("I'm going to advise against that.");
        }
    }

    
}