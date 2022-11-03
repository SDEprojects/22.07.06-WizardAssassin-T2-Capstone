package com.wizard_assassin.graphics;

import javax.swing.*;
import java.awt.*;

public class scratch {

    public static void playGame() {
        JPanel textBox = new JPanel();
        textBox.setBackground(Color.black);
        textBox.setBounds(5, 650, 800, 150);

        JPanel gameVisual = new JPanel();
        gameVisual.setBackground(Color.green);
        gameVisual.setBounds(5, 5, 800, 600);

        JPanel inventoryVisual = new JPanel();
        inventoryVisual.setBackground(Color.blue);
        inventoryVisual.setBounds(805, 5, 400, 600);

        JPanel directionsVisual = new JPanel();
        directionsVisual.setBackground(Color.red);
        directionsVisual.setBounds(805, 650, 400, 150);

        JPanel board = new JPanel();
        board.setSize(1280, 800);
        board.setLayout(null);
        board.add(textBox);
        board.add(gameVisual);
        board.add(inventoryVisual);
        board.add(directionsVisual);


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(1280, 800);
        frame.setVisible(true);
        frame.add(board);
    }

}