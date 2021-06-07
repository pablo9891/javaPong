package org.game.window;

import org.game.fonts.Text;
import org.game.gamestates.GameState;
import org.game.gamestates.PlayOpponentState;
import org.game.sound.SoundManager;
import org.game.time.Time;
import org.game.utils.Constants;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame implements Runnable {
    private Thread windowThread;
    private int width, height;
    private String windowName;
    private boolean isExecuting;
    private GameState gameState;
    private int frames;

    private Text text;
    private SoundManager soundManager;
    private Graphics graphics;

    private double avgFPS = 0;

    Time time = new Time();

    public Window() {
        loadWindowConfiguration();
        windowThread = new Thread(this);
    }

    private void loadWindowConfiguration() {
        this.windowName = Constants.WINDOW_NAME;
        this.width = Constants.WINDOW_WIDTH;
        this.height = Constants.WINDOW_HEIGHT;
        this.setTitle(windowName);
        this.setSize(new Dimension(this.width, this.height));
        this.setVisible(Constants.IS_VISIBLE_WINDOW);
        this.setResizable(Constants.IS_RESIZABLE_WINDOW);
        this.setFocusable(Constants.IS_FOCUSABLE_WINDOW);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        text = new Text();
        graphics = (Graphics2D)this.getGraphics();
        soundManager = new SoundManager();
        frames = 0;

        gameState = new PlayOpponentState(this);
        gameState.loadResources();
    }

    public void update(double delta) {
        gameState.update(delta);
    }

    public void render() {
        Image img = this.createImage(this.getWindowWidth(), this.getWindowHeight());
        Graphics g = img.getGraphics();
        Graphics2D doubleBuffer = (Graphics2D) g;

        gameState.render(doubleBuffer);

        if(Constants.IS_DEBUG_SET)
            text.draw(String.valueOf(Math.round(avgFPS * 100.0) / 100.0), doubleBuffer, 20, 10, 50,
                    Color.YELLOW);

        graphics.drawImage(img, 0, 0, this);
        frames++;
    }

    public void startWindow() { windowThread.start(); }

    public void stopWindow() {
        graphics.dispose();
        this.dispose();
        this.isExecuting = false;
        windowThread.interrupt();
        System.exit(0);
    }

    @Override
    public void run() {
        this.isExecuting = true;

        gameState.loadGame();

        double frameElapasedTime = 0;
        double frameStartTime = 0;
        double delta = 0;
        double frameEndTime = 0;

        time.start();
        while(this.isExecuting) {
            frameStartTime = time.getTime();
            delta = (frameStartTime - frameEndTime) / 1000d;
            frameEndTime = frameStartTime;

            update(delta);
            render();

            frameElapasedTime = time.getTime() - frameStartTime;

            if(frameElapasedTime < Constants.FRAME_DELAY) {
                try {
                    Thread.sleep((long) (Constants.FRAME_DELAY - frameElapasedTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            this.avgFPS = frames / (time.getTime() / 1000d);
            if(frames > 2000000)
                frames = 0;

            if(Constants.IS_DEBUG_SET) {
                System.out.println("frameStart: " + frameStartTime);
                System.out.println("frameEnd: " + frameEndTime);
                System.out.println("frameElapsed: " + frameElapasedTime);
                System.out.println("frameDelat: " + Constants.FRAME_DELAY);
                System.out.println("delta: " + delta);
            }
        }

        stopWindow();
    }

    public int getWindowWidth() {
        return width;
    }

    public int getWindowHeight() {
        return height;
    }

    public String getWindowName() {
        return windowName;
    }

    public Text getText() {
        return this.text;
    }

    public SoundManager getSoundManager() {
        return this.soundManager;
    }
}
