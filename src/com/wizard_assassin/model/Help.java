package com.wizard_assassin.model;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Help {

    private JFrame helpFrame = new JFrame();
    private JPanel helpPanel = new JPanel();
    private JButton exitButton = new JButton("EXIT");
    
    private JLabel helpLabel = new JLabel();
    private BufferedImage helpPicture;
    private BufferedImage pic = showPicture("map assets/help.png");
    private ImageIcon helpIcon = new ImageIcon(pic);
    private JLabel helpBG = new JLabel(helpIcon);
    
    public Help() {
        
    }
    
    public void initialize() {
        helpFrame.setTitle("Help Menu");
        helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        helpFrame.setSize(1100, 760);
        helpFrame.setResizable(false);
        helpFrame.setLocationRelativeTo(null);
        helpFrame.setBackground(Color.green);
        helpFrame.setVisible(true);
        helpFrame.add(helpPanel);

        exitButton.setBounds(360,20, 100, 30);

        helpPanel.add(exitButton);
        helpPanel.add(helpBG);
        helpPanel.setOpaque(false);

        exitButton.addActionListener(e -> {
            helpFrame.dispose();
        });
    }




    public BufferedImage showPicture(String file) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(file);
        try {
            helpPicture = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return helpPicture;
    }

}