package com.wizard_assassin;

class Introduction {

    public String introduction;
    public String objective;
    public String win;

    public Introduction(String introduction, String objective, String win) {
        this.introduction = introduction;
        this.objective = objective;
        this.win = win;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getObjective() {
        return objective;
    }

    public String getWin() {
        return win;
    }
}