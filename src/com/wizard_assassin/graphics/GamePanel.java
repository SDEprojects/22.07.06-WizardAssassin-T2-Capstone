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
    private GamePanel gamePanel;
    private JPanel mainPanel;
    private BufferedImage titleImg;
    private KeyboardInputs keyboardInputs;

    private int gameCondition = 0;

    //  GRAPHIC OBJECTS
    JPanel  splashPanel, namePanel, wireFrame;
    JButton backButton, nameButton, startButton;
    JLabel titleBlock;

    // CONSTRUCTOR
    public GamePanel() {
        initialize();
    }

    private void initialize(){
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        setPanelSize();
        splashPanel();

    }
    private void setLayout() {
        setLayout(null);
    }
    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setPreferredSize(size);
    }

    //----------------------------------------------------------------------------------------------------------
    //     SPLASH SCREEN
    public void splashPanel(){
        splashPanel = new JPanel();
        splashPanel.setSize(1280, 800);
        startButton = new JButton("START");
        startButton.setBounds(0, 0, 100, 100);
        //setPanelSize();
        add(startButton);
        titleBlock = new JLabel("Wizard Assassin");
        splashPanel.add(startButton);
        splashPanel.add(titleBlock);
        startButton.setVisible(true);
        splashPanel.setVisible(true);
        add(splashPanel);
        JPanel redPanel = new JPanel();
        startButton.addActionListener(e -> {
            startButton.setVisible(false);
            splashPanel.setVisible(false);
            namePanel();
        });

        importImg(new File("/TitleScreenResources/game_background_1.png"));
    }
    //----------------------------------------------------------------------------------------------------------
    // Enter name screen
    private void namePanel() {
        importImg(new File("/TitleScreenResources/StoneWall.jpeg"));
        namePanel = new JPanel();
        add(namePanel);
        setPanelSize();
        backButton = new JButton("BACK");
        nameButton = new JButton("ENTER NAME");
        namePanel.add(backButton);
        JTextField textField = new JTextField(30);
        namePanel.add(textField);
        namePanel.add(nameButton);
        textField.setText("Enter name to continue");
        textField.setVisible(true);
        namePanel.setVisible(true);
        backButton.setVisible(true);
        nameButton.setVisible(true);
        backButton.addActionListener(e -> {
            namePanel.setVisible(false);
            nameButton.setVisible(false);
            backButton.setVisible(false);
            splashPanel();
        });
        nameButton.addActionListener(e -> {
            System.out.println(textField.getText());
            namePanel.setVisible(false);
            scratch.playGame();
        });
    }

    //----------------------------------------------------------------------------------------------------------
    // WIRE FRAME WINDOW
    public void wireFrame() {

    }

    // TEXT BOX (BOTTOM LEFT)
    public void textBox() {

    }

    // DIRECTION BOX (BOTTOM RIGHT)
    public void directionBox() {

    }

    // HUD BOX (TOP RIGHT)
    public void showHUDBox() {

    }

    // GAME VISUAL (TOP LEFT)
    public void showGameVisual() {

    }


    //----------------------------------------------------------------------------------------------------------
    private void importImg(File file) {
        InputStream is = getClass().getResourceAsStream(String.valueOf(file));

        try {
            titleImg = ImageIO.read(is);
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
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(titleImg, 0, 0, 1280, 800, null);
    }

}