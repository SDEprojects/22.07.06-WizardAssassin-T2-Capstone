package com.wizard_assassin.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizard_assassin.controller.Controller;
import com.wizard_assassin.controller.Introduction;
import com.wizard_assassin.controller.PrefaceText;
import com.wizard_assassin.inputs.KeyboardInputs;
import com.wizard_assassin.model.Game;
import com.wizard_assassin.model.Music;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GamePanel extends JPanel {
    Music music = new Music();

    // ATTRIBUTES
    // private JPanel mainPanel;
    private GamePanel gamePanel;
    private BufferedImage backgroundImage;
    private BufferedImage invBackground;
    private BufferedImage gameVisualBackground;
    private BufferedImage prefaceBG;
    private KeyboardInputs keyboardInputs;
    private Controller controller;
    private String playerName, locationImg;


    //  GRAPHIC OBJECTS
    private JPanel splashPanel;
    private JPanel titlePanel;
    private JPanel namePanel;
    private JPanel wireFrame;
    private JPanel textBox;
    private JPanel showHUDBox;
    private JPanel directionBox;
    private JButton backButton;
    private JButton continueButton;
    private JButton getButton;
    private JButton eastButton;
    private JButton talk;
    private JButton fight;
    private JButton northButton;
    private JButton westButton;
    private JButton southButton;
    private JButton upButton;
    private JButton downButton;
    private JLabel inventoryBlock, locationBlock, picLabel, titleLabel,  invLabel,  mapLabel, hpLabel, winLabel;
    private JTextField nameField;
    private JTextArea promptField;
    private boolean endCondition = false;

    // CONSTRUCTOR
    public GamePanel() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        setPanelSize();
        setLayout(null);
        splashPanel();
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }

    //----------------------------------------------------------------------------------------------------------

    //     SPLASH SCREEN
    public void splashPanel() {
        GIFPlayer gifPlayer = new GIFPlayer();
        ImageIcon display = new ImageIcon(gifPlayer.gifGetter("TitleScreenResources/sbg.gif"));
        JLabel bg = new JLabel(display);
        bg.setSize(1280,800);
        this.add(bg);

        BufferedImage titlePic = showPicture("TitleScreenResources/title.png");
        ImageIcon title = new ImageIcon(titlePic);

        splashPanel = new JPanel();
        JButton startButton = new JButton("START");
        splashPanel.setLayout(null);
        splashPanel.setBounds(600, 400, 200, 50);
        splashPanel.setVisible(true);
        startButton.setBounds(600, 400, 200, 50);
        splashPanel.add(startButton);
        titleLabel = new JLabel(title);
        titleLabel.setVisible(true);
        titleLabel.setBounds(100, 30, 1200, 150);
        bg.add(titleLabel);
        bg.add(startButton);

        add(splashPanel);
        startButton.addActionListener(e -> {
            bg.setVisible(false);
            splashPanel.setVisible(false);
            titleLabel.setVisible(false);
            namePanel();
        });
        importImg("TitleScreenResources/game_background_1.png");
    }

    //----------------------------------------------------------------------------------------------------------
    // Enter name screen
    private void namePanel() {
        //load "introduction.json"
        ObjectMapper mapper = new ObjectMapper();
        String gameIntro = null;
        String gameObj = null;
        String gameWin = null;

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream introductionRecFile = classLoader.getResourceAsStream("introduction.json");
            Introduction obj = mapper.readValue(introductionRecFile, Introduction.class);
            gameIntro = obj.getIntroduction();
            gameObj = obj.getObjective();
            gameWin = obj.getWin();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JTextArea intro = new JTextArea();
        intro.setLineWrap(true);
        intro.append("\n\n\n" + gameIntro + "\n");
        intro.append("\n\n\n" + gameObj + "\n");
        intro.append("\n\n\n" + gameWin);
        intro.setLineWrap(true);
        intro.setWrapStyleWord(true);
        
        intro.setBounds(225,145, 750, 550);
        intro.setOpaque(false);

        Font font = new Font("Verdana", Font.BOLD, 18);
        intro.setFont(font);

        continueButton = new JButton("CONTINUE");
        continueButton.setBounds(600, 700, 100, 100);
        namePanel = new JPanel();

        namePanel.setBounds(500, 30, 300, 100);
        JLabel directions = new JLabel("Enter name below");
        JButton nameButton = new JButton("ENTER");
        nameField = new JTextField(20);

        prefaceBG = showPicture("TitleScreenResources/par.jpg");
        ImageIcon preBG = new ImageIcon(prefaceBG);
        JLabel preLabel = new JLabel(preBG);
        preLabel.setBounds(220, 140, 840, 550);

        namePanel.add(directions);
        namePanel.add(nameField);
        namePanel.add(nameButton);
        namePanel.setVisible(true);

        add(namePanel);
        nameButton.addActionListener(e -> {
            if (nameField.getText().isEmpty()) {
                setPlayerName("Rennie");
            } else {
                setPlayerName(nameField.getText());
            }

            this.add(intro);
            this.add(preLabel);
            this.add(continueButton);
            repaint();
        });
        continueButton.addActionListener(e -> {
            preLabel.setVisible(false);
            intro.setVisible(false);
            continueButton.setVisible(false);
            namePanel.setVisible(false);
            prefacePage();
        });
        importImg("TitleScreenResources/StoneWall.jpeg");
    }

    //----------------------------------------------------------------------------------------------------------
    // Preface Page

    public void prefacePage() {
        JPanel prefacePanel = new JPanel();

        Font font = new Font("Verdana", Font.BOLD, 18);
        prefaceBG = showPicture("TitleScreenResources/par.jpg");
        ImageIcon preBG = new ImageIcon(prefaceBG);
        JLabel preLabel = new JLabel(preBG);
        preLabel.setBounds(220, 100, 810, 600);
        preLabel.setBackground(Color.lightGray);


        JTextArea prefaceText = new JTextArea();
        prefaceText.setFont(font);
        prefaceText.setOpaque(false);
        prefaceText.setVisible(true);
        prefaceText.setBounds(250,160, 750, 450);
        JButton nextButton = new JButton("NEXT");
        nextButton.setBounds(600, 730, 100, 40);
        nextButton.setVisible(true);
        prefaceText.setLineWrap(true);
        prefaceText.setWrapStyleWord(true); //will wrap the too long word

        titleLabel.setVisible(true);
        titleLabel.setBounds(250,100, 800, 50);
        this.add(titleLabel);
        this.add(prefaceText);
        this.add(nextButton);

        //prefacePanel.add(nextButton);

        prefaceText.setText("\n"+getPlayerName() + " is in the " + Game.getViewLocation());
        prefaceText.append(" \n \n ");
        prefaceText.append("\n" + getPlayerName() + " spots the queen roaming about her garden. You decide to speak to her majesty. ");
        prefacePanel.setVisible(true);
        this.add(preLabel);
        //add(prefacePanel);

        //Load prefaceText.json
        ObjectMapper mapper = new ObjectMapper();
        String text = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream prefaceRecFile = classLoader.getResourceAsStream("prefaceText.json");
            PrefaceText obj = mapper.readValue(prefaceRecFile, PrefaceText.class);
            text = obj.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String finalText = text;
        nextButton.addActionListener(e -> {
            Font font1 = new Font("Verdana", Font.BOLD, 16); //Had to set smaller Font size to fit the textArea.
            prefaceText.setText(finalText);
            prefaceText.setFont(font1);
            nextButton.addActionListener(e1 -> {
                titleLabel.setVisible(false);
                prefaceText.setVisible(false);
                nextButton.setVisible(false);
                preLabel.setVisible(false);
                prefacePanel.setVisible(false);
                wireFrame();
            });
        });
    }

    //----------------------------------------------------------------------------------------------------------
    // WIRE FRAME WINDOW
    public void wireFrame() {
        controller = new Controller();
        importImg("TitleScreenResources/StoneWall.jpeg");
        textBox();
        directionBox();
        showHUDBox();
        showGameVisual();
        showPic();
    }

    //UPDATES WIREFRAME
    private void updateGame() {
        endCondition = Game.isEndGame();
        if (!endCondition) {
            inventoryBlock.setText("Inventory: " + Game.getViewInventory().toString());
            locationBlock.setText("Location: " + Game.getViewLocation());
            promptField.setText(Game.getReturnPrompt());
            promptField.append(Game.getResponse());
            picLabel.setVisible(false);
            mapLabel.setVisible(false);
            invLabel.setVisible(false);
            locationBlock.setVisible(false);
            showPic();
            showHUDBox();
        }
        else {
            //wireFrame.setVisible(false);
            endScreen();
        }
    }

    private void endScreen() {
        if (!Game.isLoseCondition()){
            winScreen();
        }
        else {
            loseScreen();
        }
    }

    private void winScreen() {
        GIFPlayer gifPlayer = new GIFPlayer();
        ImageIcon display = new ImageIcon(gifPlayer.gifGetter("TitleScreenResources/win_screen.gif"));
        JLabel bg = new JLabel(display);
        bg.setSize(1280, 800);
        this.add(bg);
        JButton playAgain = new JButton("PLAY AGAIN");
        playAgain.setBounds(590, 600, 160, 30);
        bg.add(playAgain);
        BufferedImage titlePic = showPicture("TitleScreenResources/title.png");
        ImageIcon title = new ImageIcon(titlePic);
        titleLabel = new JLabel(title);
        titleLabel.setVisible(true);
        titleLabel.setBounds(100, 30, 1200, 150);
        bg.add(titleLabel);

        BufferedImage winText = showPicture("TitleScreenResources/winText.png");
        ImageIcon winTextIcon = new ImageIcon(winText);
        winLabel = new JLabel(winTextIcon);
        winLabel.setVisible(true);
        winLabel.setBounds(90, 220, 1200, 150);
        bg.add(winLabel);

        invLabel.setVisible(false);
        mapLabel.setVisible(false);
        picLabel.setVisible(false);
        promptField.setVisible(false);
        locationBlock.setVisible(false);
        directionBox.setVisible(false);
        textBox.setVisible(false);
        eastButton.setVisible(false);
        southButton.setVisible(false);
        westButton.setVisible(false);
        northButton.setVisible(false);
        upButton.setVisible(false);
        downButton.setVisible(false);
        talk.setVisible(false);
        fight.setVisible(false);
        playAgain.addActionListener(e -> {
            bg.setVisible(false);
            playAgain.setVisible(false);
            splashPanel();
        });
    }

    private void loseScreen() {
        JButton playAgain = new JButton("TRY AGAIN");
        playAgain.setBounds(590, 600, 160, 30);
        this.add(playAgain);
        invLabel.setVisible(false);
        mapLabel.setVisible(false);
        picLabel.setVisible(false);
        promptField.setVisible(false);
        locationBlock.setVisible(false);
        directionBox.setVisible(false);
        textBox.setVisible(false);
        eastButton.setVisible(false);
        southButton.setVisible(false);
        westButton.setVisible(false);
        northButton.setVisible(false);
        upButton.setVisible(false);
        downButton.setVisible(false);
        talk.setVisible(false);
        fight.setVisible(false);
        playAgain.addActionListener(e -> {
            playAgain.setVisible(false);
            wireFrame();
        });
        importImg("TitleScreenResources/m_lose_screen.jpeg");
    }

    // TEXT BOX (BOTTOM LEFT)
    public void textBox() {
        textBox = new JPanel();
        promptField = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(promptField);
        scrollPane.setBounds(0, 0, 800, 150);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JTextField gameTextField = new JTextField(60);
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
        add(textBox);
    }

    // DIRECTION BOX (BOTTOM RIGHT)
    public void directionBox() {
        directionBox = new JPanel();
        directionBox.setOpaque(false);
        talk = new JButton("TALK");
        talk.setBounds(1140, 630, 70, 50);
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
        fight = new JButton("FIGHT");
        fight.setBounds(1140, 730, 70, 50);
        directionBox.setBounds(840, 480, 400, 150);
        directionBox.setVisible(true);


        this.add(talk);
        this.add(eastButton);
        this.add(northButton);
        this.add(southButton);
        this.add(westButton);
        this.add(upButton);
        this.add(downButton);
        this.add(fight);
        northButton.addActionListener(e -> {
            controller.input("n");
            updateGame();
        });
        eastButton.addActionListener(e -> {
            controller.input("e");
            updateGame();
        });
        southButton.addActionListener(e -> {
            controller.input("s");
            updateGame();
        });
        westButton.addActionListener(e -> {
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
        fight.addActionListener(e -> {
            controller.input("f");
            updateGame();
        });
        talk.addActionListener(e -> {
            controller.input("t");
            updateGame();
        });
        add(directionBox);
    }

    // HUD BOX (TOP RIGHT)
    public void showHUDBox() {
        BufferedImage invBackground = showPicture("PanelAssets/inv2.png");
        ImageIcon invBG = new ImageIcon(invBackground);

        invLabel = new JLabel(invBG);
        invLabel.setVisible(true);
        invLabel.setBounds(840, 35, 400, 545);

        inventoryBlock = new JLabel();
        inventoryBlock.setText("Inventory: " + Game.getViewInventory().toString());
        inventoryBlock.setBounds(840, 30, 400, 100);

        locationBlock = new JLabel();
        locationBlock.setText(" Location: " + Game.getViewLocation());
        locationBlock.setBounds(10, 5, 250, 20);
        locationBlock.setOpaque(true);
        locationBlock.setBackground(Color.lightGray);
        showMap();
        playerInventory();
        this.add(invLabel);
        this.add(locationBlock);
        invLabel.add(inventoryBlock);
    }

    public void showHP() {
        ImageIcon hp = new ImageIcon(showPicture("ObjectAssets/hp.png"));
        hpLabel = new JLabel(hp);
        hpLabel.setBounds(270, 5, 250, 32);
        //hpLabel.setBounds();
    }

    public void playerInventory() {
        InventoryUI inventoryUI = new InventoryUI();
        ButtonGroup group = new ButtonGroup();
        JRadioButton use = new JRadioButton("USE", true);
        JRadioButton drop = new JRadioButton("DROP");
        JRadioButton examine = new JRadioButton("EXAMINE");
        use.setBounds(40, 270, 80, 40);
        drop.setBounds(155, 270, 80, 40);
        examine.setBounds(270, 270, 110, 40);

        List<String> inventory = Game.getViewInventory();

        Icon iconDefault = new ImageIcon(showPicture("ObjectsAssets/Layer_17.png"));

        JButton item1 = new JButton(iconDefault);
        JButton item2 = new JButton(iconDefault);
        JButton item3 = new JButton(iconDefault);
        JButton item4 = new JButton(iconDefault);
        JButton item5 = new JButton(iconDefault);
        JButton item6 = new JButton(iconDefault);
        item1.setBounds(40, 320, 80, 80);
        item2.setBounds(40, 420, 80, 80);
        item3.setBounds(155, 320, 80, 80);
        item4.setBounds(155, 420, 80, 80);
        item5.setBounds(270, 320, 80, 80);
        item6.setBounds(270, 420, 80, 80);

        if (inventory.size() >= 1) {
            Icon icon1 = new ImageIcon(showPicture(inventoryUI.inventorySetter(inventory.get(0))));
            item1.setIcon(icon1);
            item1.addActionListener(e -> {
                String action = "";

                if (use.isSelected()) {
                    action = "use";

                } else if (drop.isSelected()) {
                    action = "drop";

                } else {
                    action = "examine";
                }
                controller.input(action + " " + inventory.get(0));
                updateGame();
            });
        }
        if (inventory.size() >= 2) {
            Icon icon2 = new ImageIcon(showPicture(inventoryUI.inventorySetter(inventory.get(1))));
            item2.setIcon(icon2);
            item2.addActionListener(e -> {
                String action = "";

                if (use.isSelected()) {
                    action = "use";

                } else if (drop.isSelected()) {
                    action = "drop";

                } else {
                    action = "examine";
                }
                controller.input(action + " " + inventory.get(1));
                updateGame();
            });
        }
        if (inventory.size() >= 3) {
            Icon icon3 = new ImageIcon(showPicture(inventoryUI.inventorySetter(inventory.get(2))));
            item3.setIcon(icon3);
            item3.addActionListener(e -> {
                String action = "";

                if (use.isSelected()) {
                    action = "use";

                } else if (drop.isSelected()) {
                    action = "drop";

                } else {
                    action = "examine";
                }

                controller.input(action + " " + inventory.get(2));
                updateGame();
            });
        }
        if (inventory.size() >= 4) {
            Icon icon4 = new ImageIcon(showPicture(inventoryUI.inventorySetter(inventory.get(3))));
            item4.setIcon(icon4);
            item4.addActionListener(e -> {
                String action = "";

                if (use.isSelected()) {
                    action = "use";

                } else if (drop.isSelected()) {
                    action = "drop";

                } else {
                    action = "examine";
                }

                controller.input(action + " " + inventory.get(3));
                updateGame();
            });
        }
        if (inventory.size() >= 5) {
            Icon icon5 = new ImageIcon(showPicture(inventoryUI.inventorySetter(inventory.get(4))));
            item5.setIcon(icon5);
            item5.addActionListener(e -> {
                String action = "";

                if (use.isSelected()) {
                    action = "use";

                } else if (drop.isSelected()) {
                    action = "drop";

                } else {
                    action = "examine";
                }

                controller.input(action + " " + inventory.get(4));
                updateGame();
            });
        }
        if (inventory.size() >= 6) {
            Icon icon6 = new ImageIcon(showPicture(inventoryUI.inventorySetter(inventory.get(5))));
            item6.setIcon(icon6);
            item6.addActionListener(e -> {
                String action = "";

                if (use.isSelected()) {
                    action = "use";

                } else if (drop.isSelected()) {
                    action = "drop";

                } else {
                    action = "examine";
                }

                controller.input(action + " " + inventory.get(5));
                updateGame();
            });
        }
        group.add(use);
        group.add(drop);
        group.add(examine);

        invLabel.add(use);
        invLabel.add(drop);
        invLabel.add(examine);

        invLabel.add(item1);
        invLabel.add(item2);
        invLabel.add(item3);
        invLabel.add(item4);
        invLabel.add(item5);
        invLabel.add(item6);

    }

    public void showMap() {
        String mapImgFile = Game.getViewLocation();
        BufferedImage mapPic = null;
        switch (mapImgFile) {
            case ("Queen's Garden"):
                mapPic = showPicture("map assets/atGarden.png");
                break;
            case("Church"):
                mapPic = showPicture("map assets/atChurch.png");
                break;
            case ("Courtyard"):
                mapPic = showPicture("map assets/atCourtyard.png");
                break;
            case ("Watchtower"):
            case ("Armory"):
            case ("Dungeon"):
                mapPic = showPicture("map assets/atWatchtower.png");
                break;
            case ("Laboratory"):
                mapPic = showPicture("map assets/atLab.png");
                break;
            case("Wizard's Foyer"):
                mapPic = showPicture("map assets/atWizardFoyer.png");
                break;
            case("Wizard’s Chambers"):
                mapPic = showPicture("map assets/atWizardChamber.png");
                break;
            case("Great Hall"):
                mapPic = showPicture("map assets/atGreatHall.png");
                break;
            case("Kitchen"):
                mapPic = showPicture("map assets/atKitchen.png");
                break;
            case("Royal Lounge"):
                mapPic = showPicture("map assets/atRoyalLounge.png");
                break;
            case("Royal Library"):
                mapPic = showPicture("map assets/atRoyalLibrary.png");
                break;
            case("King’s Chambers"):
                mapPic = showPicture("map assets/atKingSuite.png");
                break;
            default:
                mapPic = showPicture("TitleScreenResources/placeholder.jpg");
                break;
        }
        ImageIcon mapIc = new ImageIcon(mapPic);
        mapLabel = new JLabel(mapIc);
        mapLabel.setVisible(true);
        mapLabel.setBounds(875, 70, 320, 235);
        this.add(mapLabel);
    }

    public BufferedImage showPicture(String file) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        BufferedImage myPicture;
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
                currentImg = showPicture("TitleScreenResources/dungeon.png");
                break;
            case ("Great Hall"):
                currentImg = showPicture("TitleScreenResources/great_hall.jpg");
                break;
            case ("Kitchen"):
                currentImg = showPicture("TitleScreenResources/kitchen.jpg");
                break;
            case ("Royal Lounge"):
                currentImg = showPicture("TitleScreenResources/royal_lounge.jpg");
                break;
            case ("Royal Library"):
                currentImg = showPicture("TitleScreenResources/library.jpg");
                break;
            case ("King’s Chambers"):
                currentImg = showPicture("TitleScreenResources/king_chamber.jpg");
                break;
            case ("Wizard's Foyer"):
                currentImg = showPicture("TitleScreenResources/wizard_foyer.jpg");
                break;
            case ("Wizard’s Chambers"):
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
        // NPC
        List<String> viewRoomNPCs;
        viewRoomNPCs = Game.getViewRoomNPCs();

        JButton item1 = new JButton();
        JButton item2 = new JButton();
        JButton item3 = new JButton();
        item1.setBounds(340, 470, 80, 80);
        item2.setBounds(440, 470, 80, 80);
        item3.setBounds(540, 470, 80, 80);
        //NPC
        JButton NPC1 = new JButton();
        NPC1.setBounds(100, 100, 200, 200);
        NPC1.setOpaque(false);
        NPC1.setContentAreaFilled(false);

        Icon icon1;
        Icon icon2;
        Icon icon3;
        //
        Icon npcIcon;
        JLabel npcLabel = new JLabel();
        npcLabel.setBounds(275, 200, 200, 200);

        NPC_UI npcUi = new NPC_UI();

        if (viewRoomNPCs !=null && !viewRoomNPCs.isEmpty()) {
            npcIcon = new ImageIcon(showPicture(npcUi.npcSetter(viewRoomNPCs.get(0))));
            npcLabel.setIcon(npcIcon);
            picLabel.add(npcLabel);
        }

        InventoryUI inventoryUI = new InventoryUI();
        switch (viewRoomItems.size()) {
            case (1):
                icon1 = new ImageIcon(showPicture(inventoryUI.inventorySetter(viewRoomItems.get(0))));
                item1.setIcon(icon1);
                picLabel.add(item1);
                item1.addActionListener(e -> {
                    controller.input("get " + viewRoomItems.get(0));
                    updateGame();
                });
                break;
            case (2):
                icon1 = new ImageIcon(showPicture(inventoryUI.inventorySetter(viewRoomItems.get(0))));
                item1.setIcon(icon1);
                icon2 = new ImageIcon(showPicture(inventoryUI.inventorySetter(viewRoomItems.get(1))));
                item2.setIcon(icon2);
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
                icon1 = new ImageIcon(showPicture(inventoryUI.inventorySetter(viewRoomItems.get(0))));
                item1.setIcon(icon1);
                icon2 = new ImageIcon(showPicture(inventoryUI.inventorySetter(viewRoomItems.get(1))));
                item2.setIcon(icon2);
                icon3 = new ImageIcon(showPicture(inventoryUI.inventorySetter(viewRoomItems.get(2))));
                item3.setIcon(icon3);

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
        JPanel showGameVisual = new JPanel();
        showGameVisual.setBounds(10, 30, 800, 600);
        JButton quitButton = new JButton("QUIT");
        quitButton.setBounds(1180, 10, 70, 20);
        JButton settingsButton = new JButton("SETTINGS");
        settingsButton.setBounds(1000,10,110,20);
        this.add(quitButton);
        this.add(settingsButton);
        quitButton.addActionListener(e -> {
            System.exit(0);
        });

        settingsButton.addActionListener(e -> {
            music.initialize();
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