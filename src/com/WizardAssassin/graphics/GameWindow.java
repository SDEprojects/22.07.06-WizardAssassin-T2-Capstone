package com.WizardAssassin.graphics;

import javax.swing.*;

public class GameWindow extends JFrame {

    // CONSTRUCTOR
    public GameWindow(GamePanel gamePanel) {
        JFrame jFrame = new JFrame();

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}