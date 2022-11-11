package com.wizard_assassin.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleBiFunction;

import static org.junit.Assert.*;

public class TextParserTest {
    TextParser textParser = new TextParser();

    @Test
    public void testTextParserValidInputReturnTrue() {
        String phrase = "You can go north now";
        ArrayList<String> expected = new ArrayList<>(List.of("go","north"));
        ArrayList<String> actual = textParser.textParser(phrase);
        assertEquals(expected,actual);
    }

    @Test
    public void testTextParserTwoInvalidInputReturnFalse() {
        String phrase = "You can swim earth now";
        ArrayList<String> expected = new ArrayList<>(List.of("swim","earth"));
        ArrayList<String> actual = textParser.textParser(phrase);
        assertNotEquals(expected,actual);
    }

    @Test
    public void testTextParserInvalidInputReturnFalse() {
        String phrase = "You can swim earth now";
        ArrayList<String> expected = new ArrayList<>(List.of("swim","earth"));
        ArrayList<String> actual = textParser.textParser(phrase);
        assertNotEquals(expected,actual);
    }



    @Test
    public void testTextParserVerbInvalidInputReturnFalse() {
        String phrase = "You can swim earth now";
        ArrayList<String> expected = new ArrayList<>(List.of("swim","north"));
        ArrayList<String> actual = textParser.textParser(phrase);
        assertNotEquals(expected,actual);
    }

    @Test
    public void testTextParserNounInvalidInputReturnFalse() {
        String phrase = "You can swim earth now";
        //textParser.textParser(phrase);
        ArrayList<String> expected = new ArrayList<>(List.of("go","earth"));
        ArrayList<String> actual = textParser.textParser(phrase);
        assertNotEquals(expected,actual);
    }


}