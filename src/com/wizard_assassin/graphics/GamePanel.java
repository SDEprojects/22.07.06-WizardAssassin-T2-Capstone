package com.wizard_assassin.graphics;

import com.wizard_assassin.inputs.KeyboardInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {


    GameWindow jFrame;

    // ATTRIBUTES
   // private JPanel mainPanel;
    private GamePanel gamePanel;
    private BufferedImage backgroundImage;
    private KeyboardInputs keyboardInputs;


    //  GRAPHIC OBJECTS
    JPanel  splashPanel, titlePanel, namePanel, wireFrame, textBox, directionBox, showHUDBox, showGameVisual;
    JButton backButton, nameButton, startButton;
    JLabel titleBlock;
    JTextField nameField;

    // CONSTRUCTOR
    public GamePanel() {
       //setBackground(Color.blue);
       setPanelSize();
       setLayout(null);
       splashPanel();

    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setPreferredSize(size);
    }

    //----------------------------------------------------------------------------------------------------------
    //     SPLASH SCREEN
    public void splashPanel(){
        splashPanel = new JPanel();
        startButton = new JButton("START");
        splashPanel.setLayout(null);
        splashPanel.setBounds(600,400,200,50);
        splashPanel.setVisible(true);
        startButton.setSize(200, 50);
        splashPanel.add(startButton);

        titleBlock = new JLabel("Wizard Assassin");
        titlePanel = new JPanel();
        titlePanel.setLayout(null);
        titlePanel.setBounds(150, 50, 1000, 100);
        titleBlock.setSize(1000,100);
        titlePanel.add(titleBlock);

        add(splashPanel);
        add(titlePanel);
        startButton.addActionListener(e -> {
            splashPanel.setVisible(false);
            titlePanel.setVisible(false);
            namePanel();
        });
        importImg("TitleScreenResources/game_background_1.png");
    }


    //----------------------------------------------------------------------------------------------------------
    // Enter name screen
    private void namePanel() {
        namePanel = new JPanel();
        //namePanel.setLayout();
        namePanel.setBounds(550,350, 300, 100);
        backButton = new JButton("BACK");

        nameButton = new JButton("ENTER");
        nameField = new JTextField(20);
        nameField.setText("Enter name to continue");

        namePanel.add(nameField);
        namePanel.add(backButton);
        namePanel.add(nameButton);
        namePanel.setVisible(true);

        add(namePanel);
        backButton.addActionListener(e -> {
            namePanel.setVisible(false);
            nameButton.setVisible(false);
            backButton.setVisible(false);
            splashPanel();
        });
        nameButton.addActionListener(e -> {
            System.out.println(nameField.getText());
            namePanel.setVisible(false);
            wireFrame();
        });
        importImg("TitleScreenResources/StoneWall.jpeg");
    }

    //----------------------------------------------------------------------------------------------------------
    // WIRE FRAME WINDOW
    public void wireFrame() {
        importImg("TitleScreenResources/StoneWall.jpeg");
        textBox();
        directionBox();
        showHUDBox();
        showGameVisual();

    }

    // TEXT BOX (BOTTOM LEFT)
    public void textBox() {
        textBox = new JPanel();
        textBox.setBounds(10, 630, 800, 150);
        textBox.setBackground(Color.GRAY);
        textBox.setVisible(true);
        add(textBox);
    }

    // DIRECTION BOX (BOTTOM RIGHT)
    public void directionBox() {
        directionBox = new JPanel();
        directionBox.setBounds(840, 630, 400, 150);
        directionBox.setBackground(Color.blue);
        directionBox.setVisible(true);
        add(directionBox);
    }

    // HUD BOX (TOP RIGHT)
    public void showHUDBox() {
        showHUDBox = new JPanel();
        showHUDBox.setBounds(840, 20, 400, 600);
        showHUDBox.setBackground(Color.red);
        showHUDBox.setVisible(true);
        add(showHUDBox);
    }

    // GAME VISUAL (TOP LEFT)
    public void showGameVisual() {
        showGameVisual = new JPanel();
        showGameVisual.setBounds(10, 20, 800, 600);
        showGameVisual.setBackground(Color.cyan);
        showGameVisual.setVisible(true);
        add(showGameVisual);
    }


    //----------------------------------------------------------------------------------------------------------
    private BufferedImage importImg(String file) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);

        try {
            backgroundImage = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        repaint();
        return null;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, 1280, 800, null);
    }

}