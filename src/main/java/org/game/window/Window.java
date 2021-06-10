package org.game.window;

import org.game.fonts.Text;
import org.game.fonts.TextManager;
import org.game.gamestates.GameState;
import org.game.gamestates.states.MenuState;
import org.game.sound.SoundManager;
import org.game.time.Time;
import org.game.utils.Constants;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Color;

public class Window extends JFrame implements Runnable {

    private Thread windowThread;
    private int windowWidth;
    private  int windowHeight;
    private String windowName;
    private boolean isExecuting;
    private GameState gameState;
    private int frames;

    private TextManager textManager;
    private SoundManager soundManager;
    private Graphics graphics;

    private Text avgFPSText;
    private Text frameStartText;
    private Text frameEndText;
    private Text elapsedTimeText;
    private Text deltaText;
    private Text delayText;
    private double avgFPS = 0;

    private double frameElapsedTime = 0;
    private double frameStartTime = 0;
    private double delta = 0;
    private double frameEndTime = 0;

    Time time = new Time();

    public Window() {
        loadWindowConfiguration();
        windowThread = new Thread(this);
        gameState = new MenuState(this);
        gameState.loadResources();
    }

    private void loadWindowConfiguration() {
        setWindowProperties();
    }

    private void setWindowProperties() {
        this.windowName = Constants.WINDOW_NAME;
        this.windowWidth = Constants.WINDOW_WIDTH;
        this.windowHeight = Constants.WINDOW_HEIGHT;
        this.setTitle(windowName);
        this.setSize(new Dimension(this.windowWidth, this.windowHeight));
        this.setResizable(Constants.IS_RESIZABLE_WINDOW);
        this.setFocusable(Constants.IS_FOCUSABLE_WINDOW);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(Constants.IS_VISIBLE_WINDOW);
        textManager = new TextManager();
        graphics = this.getGraphics();
        soundManager = new SoundManager();
        frames = 0;

        double separationBetweenText = 10;
        avgFPSText = new Text(String.valueOf(0.0), 20, 50, 18, Color.ORANGE);
        frameStartText = new Text(String.valueOf(0.0), 20, (int)(avgFPSText.getY() + avgFPSText.getHeight() + separationBetweenText), 18, Color.ORANGE);
        frameEndText = new Text(String.valueOf(0.0), 20, (int)(frameStartText.getY() + frameStartText.getHeight() + separationBetweenText), 18, Color.ORANGE);
        elapsedTimeText = new Text(String.valueOf(0.0), 20, (int)(frameEndText.getY() + frameEndText.getHeight() + separationBetweenText), 18, Color.ORANGE);
        deltaText = new Text(String.valueOf(0.0), 20, (int)(elapsedTimeText.getY() + elapsedTimeText.getHeight() + separationBetweenText), 18, Color.ORANGE);
        delayText = new Text("Delay time: " + Math.round(Constants.FRAME_DELAY * 100.0) / 100.0, 20, (int)(deltaText.getY() + deltaText.getHeight() + separationBetweenText), 18, Color.ORANGE);
    }

    public void update(double delta) {
        gameState.update(delta);
    }

    public void render() {
        Image img = this.createImage(this.getWindowWidth(), this.getWindowHeight());
        Graphics g = img.getGraphics();
        Graphics2D doubleBuffer = (Graphics2D) g;

        doubleBuffer.setColor(getBackground());
        gameState.render(doubleBuffer);

        if(Constants.IS_DEBUG_SET) {
            avgFPSText.setText("Avg FPS: " + Math.round(avgFPS * 100.0) / 100.0);
            frameStartText.setText("Frame start: " + Math.round(frameStartTime * 100.0) / 100.0);
            frameEndText.setText("Frame end: " + Math.round(frameEndTime * 100.0) / 100.0);
            elapsedTimeText.setText("Elapsed time: " + Math.round(frameElapsedTime * 100.0) / 100.0);
            deltaText.setText("Delta: " + Math.round(delta * 10000.0) / 10000.0);

            textManager.draw(avgFPSText, doubleBuffer);
            textManager.draw(frameStartText, doubleBuffer);
            textManager.draw(frameEndText, doubleBuffer);
            textManager.draw(elapsedTimeText, doubleBuffer);
            textManager.draw(deltaText, doubleBuffer);
            textManager.draw(delayText, doubleBuffer);
        }

        graphics.drawImage(img, 0, 0, this);
        frames++;
    }

    public void startWindow() { windowThread.start(); }

    public void stopWindow() {
        this.isExecuting = false;
    }

    private void clean() {
        graphics.dispose();
        this.dispose();
        windowThread.interrupt();
    }

    @Override
    public void run() {
        this.isExecuting = true;

        gameState.loadGame();

        time.start();
        while(this.isExecuting) {
            frameStartTime = time.getTime();
            delta = (frameStartTime - frameEndTime) / 1000d;
            frameEndTime = frameStartTime;

            update(delta);
            render();

            frameElapsedTime = time.getTime() - frameStartTime;

            if(frameElapsedTime < Constants.FRAME_DELAY) {
                try {
                    Thread.sleep((long) (Constants.FRAME_DELAY - frameElapsedTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.avgFPS = frames / (time.getTime() / 1000d);
            if(frames > 2000000)
                frames = 0;
        }

        clean();
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public String getWindowName() {
        return windowName;
    }

    public TextManager getText() {
        return this.textManager;
    }

    public SoundManager getSoundManager() {
        return this.soundManager;
    }

    public void setNewState(GameState newState) {
        this.gameState = newState;
        this.gameState.loadResources();
        this.gameState.loadGame();
    }
}
