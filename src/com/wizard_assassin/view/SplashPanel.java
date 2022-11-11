package com.wizard_assassin.view;

import javax.swing.*;

class SplashPanel extends JPanel {

    private JPanel splashPanel, titlePanel;
    private JButton startButton;
    private JLabel titleBlock;

    public SplashPanel() {
        splashScreen();
    }

    private void splashScreen() {
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

        //this.add(splashPanel);
        //this.add(titlePanel);
        startButton.addActionListener(e -> {
            splashPanel.setVisible(false);
            titlePanel.setVisible(false);
            //GamePanel.namePanel();
        });
    }

}