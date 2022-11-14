package com.wizard_assassin.view;

import java.net.URL;

class GIFPlayer {

    public URL gifGetter(String file) {
        ClassLoader loader = getClass().getClassLoader();
        URL gif = loader.getResource(file);

        try {
            return gif;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}