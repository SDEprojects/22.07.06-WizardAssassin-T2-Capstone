public enum MoveVerbs {
    MOVE("move"),
    GO("go");

    private String moveVerb;

    MoveVerbs(String moveVerb) {
        this.moveVerb = moveVerb;
    }

    public String moveVerb() {
        return moveVerb;
    }

    public String toString() {
        return moveVerb();
    }
}