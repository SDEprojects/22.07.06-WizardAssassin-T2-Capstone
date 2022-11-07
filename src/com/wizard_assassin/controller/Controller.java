package com.wizard_assassin.controller;

import com.wizard_assassin.model.Game;

public class Controller {
    private Game game;

    public Controller() {
        game = new Game();
    }

    public void input(String action){
        //north
        if ("n".equals(action)) {
            game.gameLoop("go north");
        }
        //south
        if ("s".equals(action)) {
            game.gameLoop("go south");
        }
        //east
        if ("e".equals(action)) {
            game.gameLoop("go east");
        }
        //west
        if ("w".equals(action)) {
            game.gameLoop("go west");
        }
        //fight
        if ("f".equals(action)) {
            game.gameLoop("fight rat");
        }
        //get
        if ("g".equals(action)) {
            game.gameLoop("get stick");
        }
    }
}
