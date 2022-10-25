public enum MoveVerbs {
    MOVE("move"),
    GO("go"),
    WALK("walk"),
    TRAVEL("travel");

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