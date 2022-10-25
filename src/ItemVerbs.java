public enum ItemVerbs {
    PICKUP("pickup"),
    USE("use"),
    EQUIP("equip"),
    DROP("drop"),
    COMBINE("combine");

    private String itemVerb;

    ItemVerbs(String itemVerb) {
        this.itemVerb = itemVerb;
    }

    public String itemVerb() {
        return itemVerb;
    }

    public String toString() {
        return itemVerb();
    }
}