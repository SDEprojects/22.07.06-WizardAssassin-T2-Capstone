import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class HelpMenu implements Verbs{
    private String purpose;
    private HashSet<String> verbs;
    private String noun;
    private String example;

    public HelpMenu(String purpose, HashSet<String> verbs, String noun, String example) {
        this.setPurpose(purpose);
        this.setVerbs(verbs);
        this.setNoun(noun);
        this.setExample(example);
    }


    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public HashSet<String> getVerbs() {
        return verbs;
    }

    public void setVerbs(HashSet<String> verbs) {
        this.verbs = verbs;
    }

    public String getNoun() {
        return noun;
    }

    public void setNoun(String noun) {
        this.noun = noun;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public static void printMenuHeader() {
        System.out.println(String.format("\033[91m%25s %10s %20s %10s %15s %10s %10s\033[0m", "Purpose", "|", "Possible VERBs", "|", "NOUN", "|", "Example"));
        System.out.println(String.format("%s", "----------------------------------------------------------------------------------------------------------------"));
    }
    public void printMenu() {
        System.out.println(String.format("\033[34m%25s %10s %20s %10s %15s %10s %10s\033[0m", this.getPurpose(), "|", this.getVerbs(), "|", this.getNoun(), "|", this.getExample()));
    }

    public static List<HelpMenu> buildMenu() {
        List<HelpMenu> itemList = new ArrayList<>();
        itemList.add(new HelpMenu("Move to new area", Verbs.getMoveActions(), "DIRECTION name", "Go North"));
        itemList.add(new HelpMenu("Interact with Items", Verbs.getItemActions(), "ITEM name", "Use Stick"));
        itemList.add(new HelpMenu("Interact with Characters", Verbs.getCharacterActions(), "CHARACTER name", "Talk Queen"));
        itemList.add(new HelpMenu("Interact with Area", Verbs.getAreaActions(), "OBJECT name", "Exa Window"));
        return itemList;
    }
}