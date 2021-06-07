package org.game.gamestates;

import org.game.fonts.Text;
import org.game.gameobject.GameObject;
import org.game.input.KeyListenerCallback;
import org.game.input.KeyboardCallback;
import org.game.utils.Constants;
import org.game.window.Window;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {

    private KeyListenerCallback keyListener;

    private Text pongText, playOponentText, playIAText, exitText;

    KeyboardCallback<GameObject, KeyListenerCallback, Double> menuCallback = (gameObject, listener, delta) -> {
        if (keyListener.isKeyPressed(KeyEvent.VK_P)) {
            window.setNewState(new PlayOpponentState(window));
        }

        if (keyListener.isKeyPressed(KeyEvent.VK_I)) {
            window.setNewState(new PlayIAState(window));
        }
    };

    public MenuState(Window w) {
        super(w);
    }

    @Override
    public void loadResources() {
        keyListener = new KeyListenerCallback(window);
        window.addKeyListener(keyListener);
        pongText = new Text("Pong", (Constants.WINDOW_WIDTH / 2) - 100, (Constants.WINDOW_HEIGHT / 3), 100, Color.WHITE);
        playOponentText = new Text("Play against opponent", (Constants.WINDOW_WIDTH / 2) - 290, (Constants.WINDOW_HEIGHT / 3) + 100, 48, Color.WHITE);
        playIAText = new Text("Play against IA", (Constants.WINDOW_WIDTH / 2) - 200, (Constants.WINDOW_HEIGHT / 3) + 170, 48, Color.WHITE);
        exitText = new Text("Exit", (Constants.WINDOW_WIDTH / 2) - 40, (Constants.WINDOW_HEIGHT / 3) + 240, 48, Color.WHITE);

    }

    @Override
    public void update(double delta) {
        menuCallback.apply(null, keyListener, delta);
    }

    @Override
    public void render(Graphics2D buffer) {
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0, 0, window.getWindowWidth(), window.getWindowHeight());

        window.getText().draw(pongText, buffer);
        window.getText().draw(playOponentText, buffer);
        window.getText().draw(playIAText, buffer);
        window.getText().draw(exitText, buffer);
    }

    @Override
    public void loadGame() {

    }
}
