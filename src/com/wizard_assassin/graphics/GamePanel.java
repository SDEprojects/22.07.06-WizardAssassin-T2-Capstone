package com.wizard_assassin.graphics;

import com.wizard_assassin.Music;
import com.wizard_assassin.controller.Controller;
import com.wizard_assassin.inputs.KeyboardInputs;
import com.wizard_assassin.model.Game;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GamePanel extends JPanel {


    // ATTRIBUTES
    // private JPanel mainPanel;
    private GamePanel gamePanel;
    private BufferedImage backgroundImage, myPicture, invBackground, gameVisualBackground;
    private KeyboardInputs keyboardInputs;
    private Controller controller = new Controller();
    private String playerName, locationImg;


    //  GRAPHIC OBJECTS
    JPanel splashPanel, titlePanel, namePanel, wireFrame, textBox, directionBox, showHUDBox, showGameVisual;
    JButton backButton, nameButton, startButton, northButton, eastButton, southButton, westButton, selectButton, continueButton, getButton, upButton, downButton;
    JLabel titleBlock, inventoryBlock, locationBlock, picLabel;
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
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }

    //----------------------------------------------------------------------------------------------------------
    //     SPLASH SCREEN
    public void splashPanel() {
        BufferedImage titlePic = showPicture("TitleScreenResources/title.png");
        ImageIcon title = new ImageIcon(titlePic);
        splashPanel = new JPanel();
        startButton = new JButton("START");
        splashPanel.setLayout(null);
        splashPanel.setBounds(600, 400, 200, 50);
        splashPanel.setVisible(true);
        startButton.setSize(200, 50);
        splashPanel.add(startButton);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setVisible(true);
        titleLabel.setBounds(100, 30, 1200, 150);
        this.add(titleLabel);
        add(splashPanel);
        startButton.addActionListener(e -> {
            splashPanel.setVisible(false);
            titleLabel.setVisible(false);
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
        JLabel directions = new JLabel("Enter name below");
        nameButton = new JButton("ENTER");
        nameField = new JTextField(20);

        namePanel.add(directions);
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
            if (nameField.getText().isEmpty()) {
                setPlayerName("Rennie");
            } else {
                setPlayerName(nameField.getText());
            }
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
            prefacePage();
            //wireFrame();
        });
        importImg("TitleScreenResources/StoneWall.jpeg");
    }

    //----------------------------------------------------------------------------------------------------------
    // Preface Page

    public void prefacePage() {
        JPanel prefacePanel = new JPanel();
        prefacePanel.setBounds(450, 200, 500, 500);
        prefacePanel.setOpaque(false);
        JTextArea prefaceText = new JTextArea();
        prefaceText.setBounds(450, 200, 500, 500);
        JButton nextButton = new JButton("NEXT");
        prefaceText.setLineWrap(true);
        prefacePanel.add(prefaceText);
        prefacePanel.add(nextButton, BorderLayout.SOUTH);
        prefaceText.setText(getPlayerName() + " is in the " + Game.getViewLocation());
        prefaceText.append("\n" + getPlayerName() + " spots the queen roaming about her garden. You decide to speak to her majesty. ");
        prefacePanel.setVisible(true);
        add(prefacePanel);
        nextButton.addActionListener(e -> {
            prefaceText.setText("The Queen says to you,\"Hello warrior, I've called you here because I have a special mission for you. This mission is sooooo special.... As you are well aware the Kingdom to the South has been mercilessly invading neighboring Kingdom's, slaughtering countless innocents, and it appears that we may be their next target. What you may not know is that we have gathered intel from a spy that this ruthless bloodshed is only occurring due to the influence of a powerful spell being placed on the entire Kingdom by an Evil Wizard. My plea for you and that of our people is for you to infiltrate the Kingdom to the South and ASSASSINATE this Evil Wizard. We know that this Wizard is guarded by a vicious monster but our inside source tells us that this beast is loyal only to the Evil Wizard. It relies mostly on scent, so you should be able to find something of the WIZARD's to trick the monster, ROBES, perhaps. Please spare as many lives as you can, since we don't believe any folk in the Kingdom to be acting of their own volition. When you're ready to go, I'll use this scroll to transport you to the Kingdom to the South. Are you ready?\"");
            nextButton.addActionListener(e1 -> {
                prefacePanel.setVisible(false);
                wireFrame();
            });
        });
    }

    //----------------------------------------------------------------------------------------------------------
    // WIRE FRAME WINDOW
    public void wireFrame() {
        importImg("TitleScreenResources/StoneWall.jpeg");
        textBox();
        directionBox();
        showHUDBox();
        showGameVisual();
        showPic();
    }

    //UPDATES WIREFRAME
    private void updateGame() {
        inventoryBlock.setText("Inventory: " + Game.getViewInventory().toString());
        locationBlock.setText("Location: " + Game.getViewLocation());
        promptField.setText(Game.getReturnPrompt());
        promptField.append(Game.getResponse());
        picLabel.setVisible(false);
        showPic();
    }

    // TEXT BOX (BOTTOM LEFT)
    public void textBox() {
        textBox = new JPanel();
        promptField = new JTextArea();
        scrollPane = new JScrollPane(promptField);

        scrollPane.setBounds(0, 0, 800, 150);
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
        directionBox.setOpaque(false);
        JButton b1 = new JButton("1");
        JButton b2 = new JButton("2");
        JButton b3 = new JButton("3");
        JButton b4 = new JButton("4");
        JButton b5 = new JButton("TALK");
        b1.setBounds(915, 580, 50, 50);
        b2.setBounds(965, 580, 50, 50);
        b3.setBounds(1015, 580, 50, 50);
        b4.setBounds(1065, 580, 50, 50);
        b5.setBounds(1140, 630, 70, 50);
        northButton = new JButton("N");
        northButton.setBounds(1015, 630, 50, 50);
        eastButton = new JButton("E");
        eastButton.setBounds(1070, 680, 50, 50);
        southButton = new JButton("S");
        southButton.setBounds(1015, 730, 50, 50);
        westButton = new JButton("W");
        westButton.setBounds(960, 680, 50, 50);
        upButton = new JButton("UP");
        upButton.setBounds(850, 630, 80, 50);
        downButton = new JButton("DOWN");
        downButton.setBounds(850, 730, 80, 50);
        selectButton = new JButton("FIGHT");
        selectButton.setBounds(1140, 730, 70, 50);
        directionBox.setBounds(840, 480, 400, 150);
        directionBox.setVisible(true);
        eastButton.setVisible(true);
        //getButton = new JButton("GET");
        //getButton.setBounds(1140, 680, 70, 50);
        this.add(b1);
        this.add(b2);
        this.add(b3);
        this.add(b4);
        this.add(b5);
        this.add(eastButton);
        this.add(northButton);
        this.add(southButton);
        this.add(westButton);
        this.add(upButton);
        this.add(downButton);
        this.add(selectButton);
        //this.add(getButton);
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
        upButton.addActionListener(e -> {
            controller.input("u");
            updateGame();
        });
        downButton.addActionListener(e -> {
            controller.input("d");
            updateGame();
        });
        selectButton.addActionListener(e -> {
            System.out.println("Select");
            controller.input("f");
            updateGame();
        });
        /*getButton.addActionListener(e -> {
            System.out.println("Get");
            controller.input("g");
            updateGame();

        });*/
        b1.addActionListener(e -> {

        });

        b2.addActionListener(e -> {

        });
        b3.addActionListener(e -> {

        });
        b4.addActionListener(e -> {
            controller.input("use diamond key");
            updateGame();
        });
        b5.addActionListener(e -> {
            System.out.println("Talk");
            controller.input("t");
            updateGame();
        });
        add(directionBox);
    }

    // HUD BOX (TOP RIGHT)
    public void showHUDBox() {
        invBackground = showPicture("PanelAssets/inv2.png");
        ImageIcon invBG = new ImageIcon(invBackground);
        JLabel invLabel = new JLabel(invBG);
        invLabel.setBounds(840, 35, 400, 545);

        //showHUDBox = new JPanel();
        inventoryBlock = new JLabel();
        inventoryBlock.setText("Inventory: " + Game.getViewInventory().toString());
        inventoryBlock.setBounds(840, 30, 400, 100);

        locationBlock = new JLabel();
        locationBlock.setText("Location: " + Game.getViewLocation());
        locationBlock.setBounds(10, 5, 160, 20);
        locationBlock.setOpaque(true);
        locationBlock.setBackground(Color.lightGray);
        //showHUDBox.setBackground(Color.cyan);
        //showHUDBox.setBounds(840, 40, 400, 450);
        //showHUDBox.setVisible(true);
        this.add(invLabel);
        this.add(locationBlock);
        invLabel.add(inventoryBlock);
        //add(showHUDBox);
    }


    public BufferedImage showPicture(String file) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        try {
            myPicture = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return myPicture;
    }

    public void showPic() {
        String imgFile = Game.getViewLocation();
        BufferedImage currentImg = null;
        switch (imgFile) {
            case ("Queen's Garden"):
                currentImg = showPicture("TitleScreenResources/magic_garden.jpg");
                break;
            case ("Church"):
                currentImg = showPicture("TitleScreenResources/church.jpg");
                break;
            case ("Courtyard"):
                currentImg = showPicture("TitleScreenResources/courtyard1.png");
                break;
            case ("Watchtower"):
                currentImg = showPicture("TitleScreenResources/watchtower.jpg");
                break;
            case ("Armory"):
                currentImg = showPicture("TitleScreenResources/armory.jpg");
                break;
            case ("Dungeon"):
                currentImg = showPicture("TitleScreenResources/dungeon.jpg");
                break;
            case ("Great Hall"):
                currentImg = showPicture("TitleScreenResources/great_hall.jpg");
                break;
            case ("kitchen"):
                currentImg = showPicture("TitleScreenResources/kitchen.jpg");
                break;
            case ("Royal Lounge"):
                currentImg = showPicture("TitleScreenResources/royal_lounge.jpg");
                break;
            case ("Royal Library"):
                currentImg = showPicture("TitleScreenResources/library.jpg");
                break;
            case ("King's Chambers"):
                currentImg = showPicture("TitleScreenResources/king_chamber.jpg");
                break;
            case ("Wizard's Foyer"):
                currentImg = showPicture("TitleScreenResources/wizard_foyer.jpg");
                break;
            case ("Wizard's Chambers"):
                currentImg = showPicture("TitleScreenResources/wizard_room.jpg");
                break;
            case ("Laboratory"):
                currentImg = showPicture("TitleScreenResources/lab.jpg");
                break;
            default:
                currentImg = showPicture("TitleScreenResources/placeholder.jpg");
                break;
        }
        ImageIcon img = new ImageIcon(currentImg);
        picLabel = new JLabel(img);
        picLabel.setVisible(true);
        picLabel.setBounds(10, 30, 800, 550);
        roomItems();
        this.add(picLabel);
    }


    private void roomItems() {
        List<String> viewRoomItems;
        viewRoomItems = Game.getViewRoomItems();

        JButton item1 = new JButton();
        JButton item2 = new JButton();
        JButton item3 = new JButton();
        item1.setBounds(340, 470, 80, 50);
        item2.setBounds(440, 470, 80, 50);
        item3.setBounds(540, 470, 80, 50);

        switch (viewRoomItems.size()) {
            case (1):
                item1.setText(viewRoomItems.get(0).toUpperCase());
                picLabel.add(item1);
                item1.addActionListener(e -> {
                    controller.input("get " + viewRoomItems.get(0));
                    updateGame();
                });
                break;
            case (2):
                item1.setText(viewRoomItems.get(0).toUpperCase());
                item2.setText(viewRoomItems.get(1).toUpperCase());
                item1.addActionListener(e -> {
                    controller.input("get " + viewRoomItems.get(0));
                    updateGame();
                });
                item2.addActionListener(e -> {
                    controller.input("get " + viewRoomItems.get(1));
                    updateGame();
                });
                picLabel.add(item1);
                picLabel.add(item2);
                break;
            case (3):
                item1.setText(viewRoomItems.get(0).toUpperCase());
                item2.setText(viewRoomItems.get(1).toUpperCase());
                item3.setText(viewRoomItems.get(2).toUpperCase());
                item1.addActionListener(e -> {
                    controller.input("get " + viewRoomItems.get(0));
                    updateGame();
                });
                item2.addActionListener(e -> {
                    controller.input("get " + viewRoomItems.get(1));
                    updateGame();
                });
                item3.addActionListener(e -> {
                    controller.input("get " + viewRoomItems.get(2));
                    updateGame();
                });
                picLabel.add(item1);
                picLabel.add(item2);
                picLabel.add(item3);
                break;
            default:
                break;
        }
    }


    // GAME VISUAL (TOP LEFT)
    public void showGameVisual() {
        showGameVisual = new JPanel();
        showGameVisual.setBounds(10, 30, 800, 600);
        JButton quitButton = new JButton("QUIT");
        quitButton.setBounds(1180, 10, 70, 20);
        JButton playMusicButton = new JButton("MUSIC-ON");
        JButton stopMusicButton = new JButton("MUSIC-OFF");
        playMusicButton.setBounds(840, 10, 80, 20);
        stopMusicButton.setBounds(930, 10, 80, 20);
        this.add(quitButton);
        this.add(playMusicButton);
        this.add(stopMusicButton);
        quitButton.addActionListener(e -> {
            System.exit(0);
        });
        playMusicButton.addActionListener(e -> {
            try {
                Music.playMusic();
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                ex.printStackTrace();
            }
        });

        stopMusicButton.addActionListener(e -> {
            Music.stopMusic();
        });
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

    // ACCESSOR METHODS


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getLocationImg() {
        return locationImg;
    }

    public void setLocationImg(String locationImg) {
        this.locationImg = locationImg;
    }
}