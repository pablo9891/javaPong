package org.game.input;

import org.game.gameobject.GameObject;

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
