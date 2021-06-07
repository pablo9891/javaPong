package org.game.input.controller;

import org.game.gameobject.GameObject;
import org.game.input.callback.keyboardimp.KeyListenerCallback;
import org.game.input.callback.KeyboardCallback;

public class PlayerController {

    private KeyListenerCallback keyListener;
    private GameObject gameObject;

    public PlayerController(GameObject gameObject, KeyListenerCallback keyListener) {
        this.keyListener = keyListener;
        this.gameObject = gameObject;
    }

    public void update(Double delta, KeyboardCallback callback) {
        callback.apply(this.gameObject, keyListener, delta);
    }
}
