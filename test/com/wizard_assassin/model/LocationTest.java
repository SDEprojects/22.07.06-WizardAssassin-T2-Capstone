package com.wizard_assassin.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {
    Location location = new Location("Church", "You are in a church filled with wooden pews and an altar and nothing else.\n\nThe window you entered by is to the north.\nThere is a wooden door to the south.");


    @Test
    public void testGetName() {
        String expected = "Church";
        String actual = location.getName();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetDescription() {
        String expected = "You are in a church filled with wooden pews and an altar and nothing else.\n\nThe window you entered by is to the north.\nThere is a wooden door to the south.";
        String actual = location.getDescription();
        assertEquals(expected, actual);
    }

}