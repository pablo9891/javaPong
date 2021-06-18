package org.game.gamestates.states;

import org.game.fonts.Text;
import org.game.gameobject.GameObject;
import org.game.gamestates.GameState;
import org.game.input.callback.MouseCallback;
import org.game.input.callback.mouseimp.MouseListenerCallback;
import org.game.utils.Constants;
import org.game.window.Window;

import java.awt.Graphics2D;

public class MenuState extends GameState {

    private MouseListenerCallback mouseListener;

    private Text pongText;
    private Text playOpponentText;
    private Text playIAText;
    private Text exitText;

    MouseCallback<GameObject, MouseListenerCallback, Double> menuMouseCallback = (gameObject, listener, delta) -> {
        if(listener.isMousePressed()) {
            if(isMouseOverlapText(playOpponentText))
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
        pongText = new Text("Pong",
                (Constants.WINDOW_WIDTH / 2) - 100,
                (Constants.WINDOW_HEIGHT / 3),
                100,
                Constants.MENU_COLOR);
        playOpponentText = new Text("Play against opponent",
                (Constants.WINDOW_WIDTH / 2) - 290,
                pongText.getY() + pongText.getHeight() + wordSeparation,
                48,
                Constants.MENU_COLOR);
        playIAText = new Text("Play against IA",
                (Constants.WINDOW_WIDTH / 2) - 200,
                playOpponentText.getY() + playOpponentText.getHeight() + wordSeparation,
                48,
                Constants.MENU_COLOR);
        exitText = new Text("Exit",
                (Constants.WINDOW_WIDTH / 2) - 40,
                playIAText.getY() + playIAText.getHeight() + wordSeparation,
                48,
                Constants.MENU_COLOR);
    }

    @Override
    public void update(double delta) {
        if(isMouseOverlapText(playOpponentText))
            playOpponentText.setColor(Constants.MENU_HOVER_COLOR);
        else
            playOpponentText.setColor(Constants.MENU_COLOR);

        if(isMouseOverlapText(playIAText))
            playIAText.setColor(Constants.MENU_HOVER_COLOR);
        else
            playIAText.setColor(Constants.MENU_COLOR);

        if(isMouseOverlapText(exitText))
            exitText.setColor(Constants.MENU_HOVER_COLOR);
        else
            exitText.setColor(Constants.MENU_COLOR);

        menuMouseCallback.apply(null, mouseListener, delta);
    }

    @Override
    public void render(Graphics2D buffer) {
        window.getText().draw(pongText, buffer);
        window.getText().draw(playOpponentText, buffer);
        window.getText().draw(playIAText, buffer);
        window.getText().draw(exitText, buffer);
    }

    @Override
    public void loadGame() {
        // Empty method
    }

    private boolean isMouseOverlapText(Text txt) {
        return mouseListener.getMouseX() >= txt.getX() &&
                mouseListener.getMouseX() <= (txt.getX() + (txt.getWidth() / 2)) &&
                mouseListener.getMouseY() >= txt.getY() - (txt.getHeight() / 2) &&
                mouseListener.getMouseY() <= (txt.getY() + (txt.getHeight() / 2));
    }
}
