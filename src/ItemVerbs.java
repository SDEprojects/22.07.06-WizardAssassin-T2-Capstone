public enum ItemVerbs {
    PICKUP("pickup"),
    USE("use"),
    DROP("drop");

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