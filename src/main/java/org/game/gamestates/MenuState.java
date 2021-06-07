package org.game.gamestates;

import org.game.fonts.Text;
import org.game.gameobject.GameObject;
import org.game.input.KeyListenerCallback;
import org.game.input.KeyboardCallback;
import org.game.input.MouseCallback;
import org.game.input.MouseListenerCallback;
import org.game.utils.Constants;
import org.game.window.Window;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {

    private KeyListenerCallback keyListener;
    private MouseListenerCallback mouseListener;

    private Text pongText, playOponentText, playIAText, exitText;

    KeyboardCallback<GameObject, KeyListenerCallback, Double> menuCallback = (gameObject, listener, delta) -> {
        if (keyListener.isKeyPressed(KeyEvent.VK_P)) {
            window.setNewState(new PlayOpponentState(window));
        }

        if (keyListener.isKeyPressed(KeyEvent.VK_I)) {
            window.setNewState(new PlayIAState(window));
        }
    };

    MouseCallback<GameObject, MouseListenerCallback, Double> menuMouseCallback = (gameObject, listener, delta) -> {
        if(listener.isMousePressed()) {
            if(isMouseOverlapText(playOponentText))
                window.setNewState(new PlayOpponentState(window));
            if(isMouseOverlapText(playIAText))
                window.setNewState(new PlayIAState(window));
            if(isMouseOverlapText(exitText))
                window.stopWindow();
        }
    };

    public MenuState(Window w) {
        super(w);
    }

    @Override
    public void loadResources() {
        keyListener = new KeyListenerCallback(window);
        mouseListener = new MouseListenerCallback();
        window.addKeyListener(keyListener);
        window.addMouseListener(mouseListener);
        window.addMouseMotionListener(mouseListener);
        pongText = new Text("Pong", (Constants.WINDOW_WIDTH / 2) - 100, (Constants.WINDOW_HEIGHT / 3), 100, Color.WHITE);
        playOponentText = new Text("Play against opponent", (Constants.WINDOW_WIDTH / 2) - 290, (Constants.WINDOW_HEIGHT / 3) + 100, 48, Color.WHITE);
        playIAText = new Text("Play against IA", (Constants.WINDOW_WIDTH / 2) - 200, (Constants.WINDOW_HEIGHT / 3) + 170, 48, Color.WHITE);
        exitText = new Text("Exit", (Constants.WINDOW_WIDTH / 2) - 40, (Constants.WINDOW_HEIGHT / 3) + 240, 48, Color.WHITE);
    }

    @Override
    public void update(double delta) {
        if(isMouseOverlapText(playOponentText))
            playOponentText.setColor(Color.GREEN);
        else
            playOponentText.setColor(Color.WHITE);

        if(isMouseOverlapText(playIAText))
            playIAText.setColor(Color.GREEN);
        else
            playIAText.setColor(Color.WHITE);

        if(isMouseOverlapText(exitText))
            exitText.setColor(Color.GREEN);
        else
            exitText.setColor(Color.WHITE);

        menuMouseCallback.apply(null, mouseListener, delta);
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

    private boolean isMouseOverlapText(Text txt) {
        return mouseListener.getMouseX() >= txt.getX() && mouseListener.getMouseX() <= (txt.getX() + (txt.getWidth() / 2)) &&
                mouseListener.getMouseY() >= txt.getY() && mouseListener.getMouseY() <= (txt.getY() + (txt.getHeight() / 2));
    }
}
