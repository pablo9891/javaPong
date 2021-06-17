package org.game.gamestates;

import org.game.window.Window;

import java.awt.Graphics2D;
import java.util.Random;

public abstract class GameState {

   protected Window window;

    protected GameState(Window w) { this.window = w; }

    protected double getRandomVel(double min, double max) { return min + (new Random().nextDouble() * (max - min)); }

    protected int getRandomDir() {
     if(Math.random() > 0.5)
      return 1;
     return -1;
    }

    public abstract void loadResources();

    public abstract void update(double delta);

    public abstract void render(Graphics2D buffer);

    public abstract void loadGame();
}
