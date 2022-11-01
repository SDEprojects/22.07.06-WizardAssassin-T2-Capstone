package com.wizard_assassin.graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

class SplashPanel extends JPanel {

    private BufferedImage titleImg;

    //CONSTRUCTOR
    public SplashPanel(){
        setPanelSize();
        importImg();
        repaint();
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/TitleScreenResources/game_background_1.png");

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
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(titleImg, 0, 0, 1280, 800, null);
        paintText(g);
    }

    public void paintText(Graphics g) {

        g.setFont(g.getFont().deriveFont(Font.BOLD,96F));

        String text = "Wizard Assassin";
        int x = 205;
        int y = 200;

        g.setColor(Color.black);
        g.drawString(text, x, y);
    }
}