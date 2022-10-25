public enum AreaVerbs {
    EXAMINE("examine"),
    EXA("exa"),
    LOOK("look"),
    TOUCH("touch");

    private String areaVerb;

    AreaVerbs(String areaVerb) {
        this.areaVerb = areaVerb;
    }

    public String areaVerb() {
        return areaVerb;
    }

    public String toString() {
        return areaVerb();
    }
}