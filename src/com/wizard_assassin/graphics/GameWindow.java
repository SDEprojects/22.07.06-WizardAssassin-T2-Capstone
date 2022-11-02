package com.wizard_assassin.graphics;

import javax.swing.*;
import java.awt.*;

public class GameWindow {

    private JFrame jFrame;
    // CONSTRUCTOR

    public GameWindow(GamePanel gamePanel) {
        initialize(gamePanel);
    }

    private void initialize(GamePanel gamePanel) {
        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setTitle("Wizard Assassin");
    }
}