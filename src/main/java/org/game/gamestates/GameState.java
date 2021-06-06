package org.game.gamestates;

public interface GameState {

    public void loadResources();

    public void update(double delta);

    public void render();

    public void loadGame();

    public void loop();
}
