package org.game.gamestates.states;

import org.game.fonts.Text;
import org.game.gamestates.GameState;
import org.game.utils.Constants;
import org.game.window.Window;

import java.awt.*;

public class PlayIAState extends GameState {

    private Text IAText = new Text("IA State", Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2, 48, Color.BLACK);

    public PlayIAState(Window w) {
        super(w);
    }

    @Override
    public void loadResources() {

    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Graphics2D buffer) {
        buffer.setColor(Color.GREEN);
        buffer.fillRect(0, 0, window.getWindowWidth(), window.getWindowHeight());

        window.getText().draw(IAText, buffer);
    }

    @Override
    public void loadGame() {

    }
}
