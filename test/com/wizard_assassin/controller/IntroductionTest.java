package com.wizard_assassin.controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntroductionTest {

    Introduction intro = new Introduction("introduction", "objective", "win");

    @Test
    public void testGetIntro(){
        assertEquals("introduction", intro.getIntroduction());
    }

    @Test
    public void testGetObjective() {
        assertEquals("objective",intro.getObjective());
    }

    @Test
    public void testGetWin() {
        assertEquals("win", intro.getWin());
    }


}