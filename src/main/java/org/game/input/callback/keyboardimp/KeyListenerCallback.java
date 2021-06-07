package org.game.input.callback.keyboardimp;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.game.window.Window;

public class KeyListenerCallback implements KeyListener {
    private boolean keyPressed[];
    private Window window;

    public KeyListenerCallback(Window w) {
        keyPressed = new boolean[128];
        window = w;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed[e.getKeyCode()] = true;
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            window.stopWindow();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed[e.getKeyCode()] = false;
    }

    public boolean isKeyPressed(int keyCode) {
        return keyPressed[keyCode];
    }

}
