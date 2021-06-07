package org.game.gamestates;

import org.game.window.Window;

import java.awt.*;

public abstract class GameState {

   protected Window window;

    public GameState(Window w) { this.window = w; }

    public abstract void loadResources();

    public abstract void update(double delta);

    public abstract void render(Graphics2D buffer);

    public abstract void loadGame();
}
