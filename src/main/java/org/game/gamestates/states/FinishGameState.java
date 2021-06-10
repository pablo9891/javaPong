package org.game.gamestates.states;

import org.game.fonts.Text;
import org.game.gameobject.GameObject;
import org.game.gamestates.GameState;
import org.game.input.callback.MouseCallback;
import org.game.input.callback.mouseimp.MouseListenerCallback;
import org.game.utils.Constants;
import org.game.window.Window;

import java.awt.Graphics2D;
import java.awt.Color;

public class FinishGameState extends GameState {

    private MouseListenerCallback mouseListener;

    private Text winnerText;
    private Text retryText;
    private Text exitText;

    String winnerName;

    MouseCallback<GameObject, MouseListenerCallback, Double> menuMouseCallback = (gameObject, listener, delta) -> {
        if(listener.isMousePressed()) {
            if(isMouseOverlapText(retryText))
                window.setNewState(new MenuState(window));
            if(isMouseOverlapText(exitText))
                window.stopWindow();
        }
    };

    public FinishGameState(Window w, String winnerName) {
        super(w);
        this.winnerName = winnerName;
    }

    @Override
    public void loadResources() {
        int wordSeparation = 15;
        mouseListener = new MouseListenerCallback();
        window.addMouseListener(mouseListener);
        window.addMouseMotionListener(mouseListener);
        winnerText = new Text(winnerName + " Won!", (Constants.WINDOW_WIDTH / 2) - 180, (Constants.WINDOW_HEIGHT / 3), 100, Color.WHITE);
        retryText = new Text("Go back menu", (Constants.WINDOW_WIDTH / 2) - 170, winnerText.getY() + winnerText.getHeight() + wordSeparation, 48, Color.WHITE);
        exitText = new Text("Exit", (Constants.WINDOW_WIDTH / 2) - 40, retryText.getY() + retryText.getHeight() + wordSeparation, 48, Color.WHITE);
    }

    @Override
    public void update(double delta) {
        if(isMouseOverlapText(retryText))
            retryText.setColor(Color.GREEN);
        else
            retryText.setColor(Color.WHITE);

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

        window.getText().draw(winnerText, buffer);
        window.getText().draw(retryText, buffer);
        window.getText().draw(exitText, buffer);
    }

    @Override
    public void loadGame() {
        // Empty method
    }

    private boolean isMouseOverlapText(Text txt) {
        return mouseListener.getMouseX() >= txt.getX() && mouseListener.getMouseX() <= (txt.getX() + (txt.getWidth() / 2)) &&
                mouseListener.getMouseY() >= txt.getY() - (txt.getHeight() / 2) && mouseListener.getMouseY() <= (txt.getY() + (txt.getHeight() / 2));
    }
}

