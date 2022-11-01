package com.WizardAssassin.graphics;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    // CONSTRUCTOR
    public GamePanel() {
        setPanelSize();
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setPreferredSize(size);
    }
}