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
        //up
        if ("u".equals(action)) {
            game.gameLoop("go up");
        }
        //down
        if ("d".equals(action)) {
            game.gameLoop("go down");
        }
        //fight
        if ("f".equals(action)) {
            game.gameLoop("fight");
        }
        //get
        if ("g".equals(action)) {
            game.gameLoop("get stick");
        }
        //talk
        if ("t".equals(action)) {
            game.gameLoop("speak");
        }
    }
}
