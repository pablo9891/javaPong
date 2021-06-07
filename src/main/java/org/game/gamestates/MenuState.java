package org.game.gamestates;

import org.game.fonts.Text;
import org.game.gameobject.GameObject;
import org.game.input.callback.MouseCallback;
import org.game.input.callback.implementation.MouseListenerCallback;
import org.game.utils.Constants;
import org.game.window.Window;

import java.awt.*;

public class MenuState extends GameState {

    private MouseListenerCallback mouseListener;

    private Text pongText, playOponentText, playIAText, exitText;

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
        int wordSeparation = 15;
        mouseListener = new MouseListenerCallback();
        window.addMouseListener(mouseListener);
        window.addMouseMotionListener(mouseListener);
        pongText = new Text("Pong", (Constants.WINDOW_WIDTH / 2) - 100, (Constants.WINDOW_HEIGHT / 3), 100, Color.WHITE);
        playOponentText = new Text("Play against opponent", (Constants.WINDOW_WIDTH / 2) - 290, pongText.getY() + pongText.getHeight() + wordSeparation, 48, Color.WHITE);
        playIAText = new Text("Play against IA", (Constants.WINDOW_WIDTH / 2) - 200, playOponentText.getY() + playOponentText.getHeight() + wordSeparation, 48, Color.WHITE);
        exitText = new Text("Exit", (Constants.WINDOW_WIDTH / 2) - 40, playIAText.getY() + playIAText.getHeight() + wordSeparation, 48, Color.WHITE);
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
                mouseListener.getMouseY() >= txt.getY() - (txt.getHeight() / 2) && mouseListener.getMouseY() <= (txt.getY() + (txt.getHeight() / 2));
    }
}
