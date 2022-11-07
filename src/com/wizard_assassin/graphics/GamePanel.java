package com.wizard_assassin.graphics;

import com.wizard_assassin.controller.Controller;
import com.wizard_assassin.inputs.KeyboardInputs;
import com.wizard_assassin.model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {


    // ATTRIBUTES
   // private JPanel mainPanel;
    private GamePanel gamePanel;
    private BufferedImage backgroundImage;
    private KeyboardInputs keyboardInputs;
    private Controller controller = new Controller();


    //  GRAPHIC OBJECTS
    JPanel  splashPanel, titlePanel, namePanel, wireFrame, textBox, directionBox, showHUDBox, showGameVisual;
    JButton backButton, nameButton, startButton, northButton, eastButton, southButton, westButton, selectButton, continueButton, getButton;
    JLabel titleBlock, inventoryBlock, locationBlock;
    JTextField nameField, gameTextField;
    JTextArea promptField;
    JScrollPane scrollPane;

    // CONSTRUCTOR
    public GamePanel() {
       //setBackground(Color.blue);
       setPanelSize();
       setLayout(null);
       //this.add(new SplashPanel());
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
        JLabel introLabel1 = new JLabel("      Wizard Assassin is a single-player game in which the objective is to defeat the evil wizard and save the king.");
        JLabel introLabel2 = new JLabel("      The player needs to explore different rooms in the castle as well as collect all items necessary to defeat the evil wizard.");
        JLabel introLabel3 = new JLabel("      Once the Wizard Assassin reaches the Laboratory and defeat the evil wizard the player wins!!!!.");
        JComponent cont[] = {introLabel1, introLabel2, introLabel3, continueButton};
        introLabel1.setBounds(300, 150, 800, 50);
        introLabel2.setBounds(300, 250, 800, 50);
        introLabel3.setBounds(300, 350, 800, 50);
        introLabel1.setOpaque(true);
        introLabel2.setOpaque(true);
        introLabel3.setOpaque(true);
        introLabel1.setBackground(Color.lightGray);
        introLabel2.setBackground(Color.lightGray);
        introLabel3.setBackground(Color.lightGray);
        continueButton = new JButton("CONTINUE");
        continueButton.setBounds(600, 700, 100, 100);
        namePanel = new JPanel();
        //namePanel.setLayout();
        namePanel.setBounds(500, 30, 300, 100);
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
            this.add(introLabel1);
            this.add(introLabel2);
            this.add(introLabel3);
            this.add(continueButton);
            repaint();
            //add(namePanel);
        });
        continueButton.addActionListener(e -> {
            introLabel1.setVisible(false);
            introLabel2.setVisible(false);
            introLabel3.setVisible(false);
            continueButton.setVisible(false);
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

    //UPDATES WIREFRAME
    private void updateGame() {
        inventoryBlock.setText("Inventory: "+ Game.getViewInventory().toString());
        locationBlock.setText("Location: "+ Game.getViewLocation());
        promptField.setText(Game.getReturnPrompt());
        promptField.append(Game.getResponse());
    }

    // TEXT BOX (BOTTOM LEFT)
    public void textBox() {
        textBox = new JPanel();
        promptField = new JTextArea();
        scrollPane = new JScrollPane(promptField);

        scrollPane.setBounds(0,0,800, 150);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        gameTextField = new JTextField(60);
        gameTextField.setBounds(10, 755, 800, 20);
        gameTextField.setVisible(true);
        textBox.setBounds(10, 630, 800, 150);
        textBox.setBackground(Color.GRAY);
        textBox.setLayout(null);
        textBox.setVisible(true);
        promptField.setBounds(5, 5, 600, 125);
        promptField.setVisible(true);
        promptField.setLineWrap(true);
        promptField.setText(Game.getReturnPrompt());

        textBox.add(scrollPane);
        //this.add(gameTextField);
        add(textBox);
    }

    // DIRECTION BOX (BOTTOM RIGHT)
    public void directionBox() {
        directionBox = new JPanel();
        northButton = new JButton("N");
        northButton.setBounds(1015, 630, 50, 50);
        eastButton = new JButton("E");
        eastButton.setBounds(1070,680, 50, 50);
        southButton = new JButton("S");
        southButton.setBounds(1015, 730, 50, 50);
        westButton = new JButton("W");
        westButton.setBounds(960, 680,  50, 50);
        selectButton = new JButton("SELECT");
        selectButton.setBounds(1010, 675, 60, 60);
        directionBox.setBounds(840, 630, 400, 150);
        directionBox.setVisible(true);
        eastButton.setVisible(true);
        getButton = new JButton("GET");
        getButton.setBounds(1140, 680, 70, 50);
        this.add(eastButton);
        this.add(northButton);
        this.add(southButton);
        this.add(westButton);
        this.add(selectButton);
        this.add(getButton);
        northButton.addActionListener(e -> {
            System.out.println("North");
            controller.input("n");
            updateGame();
        });
        eastButton.addActionListener(e -> {
            System.out.println("East");
            controller.input("e");
            updateGame();
        });
        southButton.addActionListener(e -> {
            System.out.println("South");
            controller.input("s");
            updateGame();
        });
        westButton.addActionListener(e -> {
            System.out.println("West");
            controller.input("w");
            updateGame();
        });
        selectButton.addActionListener(e -> {
            System.out.println("Select");
            controller.input("f");
            updateGame();
        });
        getButton.addActionListener(e -> {
            System.out.println("Get");
            controller.input("g");
            updateGame();

        });
        add(directionBox);
    }

    // HUD BOX (TOP RIGHT)
    public void showHUDBox() {
        showHUDBox = new JPanel();
        inventoryBlock = new JLabel();
        locationBlock = new JLabel();
        showHUDBox.setBackground(Color.cyan);
        inventoryBlock.setBounds(840, 20, 400, 100);
        locationBlock.setBounds(940, 20, 400, 100);
        showHUDBox.setBounds(840, 20, 400, 600);
        showHUDBox.setVisible(true);
        inventoryBlock.setText("Inventory: "+ Game.getViewInventory().toString());
        locationBlock.setText("Location: "+ Game.getViewLocation());
        this.add(locationBlock);
        this.add(inventoryBlock);
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