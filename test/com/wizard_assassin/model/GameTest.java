package com.wizard_assassin.model;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {

    Game game = new Game();

    @Test
    public void testGetAnItem1() {
        try {
            game.getAnItem("stick", game.getLocationState(), "get");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> stick = new ArrayList<>(List.of("stick"));
        assertEquals(stick, game.getInventoryItems());
    }

    @Test
    public void testGetAnItem2() {
        try {
            game.getAnItem("stick", game.getLocationState(), "get");
            game.getAnItem("stick", game.getLocationState(), "get");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("\nCan not " +"GET"+ " " + "STICK" +
                ". It's already in your inventory. Choose again...", game.getResponse());
    }

    @Test
    public void testGetAnItem3() {
        try {
            game.getAnItem("stick", game.getLocationState(), "get");
            game.getAnItem("stick", game.getLocationState(), "drop");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> empty = new ArrayList<>(0);
        assertEquals(empty, game.getInventoryItems());
    }

    @Test
    public void testGetAnItem4() {
        try {
            game.getAnItem("stick", game.getLocationState(), "drop");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals("\nCan not " +"DROP"+ " " + "STICK" +
                ". It is not in your inventory. Choose again...", game.getResponse());
    }
}