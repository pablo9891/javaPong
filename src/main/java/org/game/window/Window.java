package org.game.window;

import org.game.colission.ColissionHelper;
import org.game.fonts.Text;
import org.game.gamestates.GameState;
import org.game.input.KeyListenerCallback;
import org.game.input.KeyboardCallback;
import org.game.input.PlayerController;
import org.game.math.Vector2D;
import org.game.gameobject.Ball;
import org.game.gameobject.Bar;
import org.game.gameobject.GameObject;
import org.game.sound.SoundManager;
import org.game.time.Time;
import org.game.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Window extends JFrame implements Runnable {
    private Thread windowThread;
    private Graphics graphics;
    private KeyListenerCallback keyListener;
    private int width, height;
    private String windowName;
    private Bar leftBar, rightBar;
    private Ball ball;
    private boolean isExecuting;
    private PlayerController leftBarController, rightBarController;
    private ColissionHelper colissionHelper;
    private GameState gameState;
    private Text text;
    private int leftMarker, rightMarker;
    private int frames;

    private SoundManager soundManager;

    private double avgFPS = 0;

    Time time = new Time();

    KeyboardCallback<GameObject, KeyListenerCallback, Double> leftBarListenerCalback = (gameObject, listener, delta) ->
    {
        if(keyListener.isKeyPressed(KeyEvent.VK_W)) {
            Vector2D newBarPosition = new Vector2D(gameObject.getPosition().getX(),
                    (gameObject.getPosition().getY() - (gameObject.getVelocity().getY() * delta)));
            if(newBarPosition.getY() > (Constants.TOP_BAR + Constants.TOP_BAR_MARGIN))
                gameObject.setDirection(new Vector2D(0, -1));
        }

        if(keyListener.isKeyPressed(KeyEvent.VK_S)) {
            Vector2D newBarPosition = new Vector2D(gameObject.getPosition().getX(),
                    (gameObject.getPosition().getY() + (gameObject.getVelocity().getY() * delta)));
            if(newBarPosition.getY() + Constants.BAR_HEIGHT < (Constants.BOTTOM_BAR - Constants.TOP_BAR_MARGIN))
                gameObject.setDirection(new Vector2D(0, 1));
        }
    };

    KeyboardCallback<GameObject, KeyListenerCallback, Double> rightBarListenerCalback = (gameObject, listener, delta) ->
    {
        if(keyListener.isKeyPressed(KeyEvent.VK_UP)) {
            Vector2D newBarPosition = new Vector2D(gameObject.getPosition().getX(),
                    (gameObject.getPosition().getY() - (gameObject.getVelocity().getY() * delta)));
            if(newBarPosition.getY() > (Constants.TOP_BAR + Constants.TOP_BAR_MARGIN))
                gameObject.setDirection(new Vector2D(0, -1));
        }

        if(keyListener.isKeyPressed(KeyEvent.VK_DOWN)) {
            Vector2D newBarPosition = new Vector2D(gameObject.getPosition().getX(),
                    (gameObject.getPosition().getY() + (gameObject.getVelocity().getY() * delta)));
            if(newBarPosition.getY() + Constants.BAR_HEIGHT < (Constants.BOTTOM_BAR - Constants.TOP_BAR_MARGIN))
                gameObject.setDirection(new Vector2D(0, 1));
        }
    };

    public Window() {
        loadWindowConfiguration();
        loadInputConfiguration();
        loadSoundConfiguration();
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
        Constants.TOP_BAR = this.getInsets().top;
        Constants.BOTTOM_BAR = Constants.WINDOW_HEIGHT;
        graphics = (Graphics2D)this.getGraphics();
        leftMarker = 0;
        rightMarker = 0;
        soundManager = new SoundManager();
        frames = 0;
    }

    private void loadInputConfiguration() {
        keyListener = new KeyListenerCallback(this);
        this.addKeyListener(keyListener);
    }

    private void loadSoundConfiguration() {
        soundManager.addSound("bounce", "bounce_sound.wav");
    }

    public void update(double delta) {

        if(rightMarker == Constants.MAX_POINTS) {
            System.out.println("Gano el jugador de la derecha !!!");
            // change game state to finish
        }
        if(leftMarker == Constants.MAX_POINTS) {
            System.out.println("Gano el jugador de la izquierda !!!");
            // change game state to finish
        }

        leftBarController.update(delta, leftBarListenerCalback);
        rightBarController.update(delta, rightBarListenerCalback);

        colissionHelper.processColissionBallWithTopAndBottom(ball, delta);
        colissionHelper.processBarBallColission(leftBar, ball, delta);
        colissionHelper.processBarBallColission(rightBar, ball, delta);

        if(colissionHelper.isPointForBar(leftBar, ball)) {
            leftMarker++;
            ball.setPosition(new Vector2D(Constants.BALL_INITIAL_X, Constants.BALL_INITIAL_Y));
            ball.setDirection(new Vector2D(-1, 1));
        }
        if(colissionHelper.isPointForBar(rightBar, ball)) {
            rightMarker++;
            ball.setPosition(new Vector2D(Constants.BALL_INITIAL_X, Constants.BALL_INITIAL_Y));
            ball.setDirection(new Vector2D(1, -1));
        }

        leftBar.update(delta);
        rightBar.update(delta);
        ball.update(delta);
    }

    public void render() {
        Image img = createImage(getWidth(), getHeight());
        Graphics g = img.getGraphics();
        Graphics2D doubleBuffer = (Graphics2D) g;

        doubleBuffer.setColor(Color.BLACK);
        doubleBuffer.fillRect(0, 0, this.width, this.height);

        leftBar.draw(doubleBuffer);
        rightBar.draw(doubleBuffer);
        ball.draw(doubleBuffer);

        text.draw(String.valueOf(leftMarker), doubleBuffer, 48, 200, 100, Color.WHITE);
        text.draw(String.valueOf(rightMarker), doubleBuffer, 48, Constants.WINDOW_WIDTH - 200, 100,
                Color.WHITE);
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

        this.leftBar = new Bar(new Vector2D(Constants.LEFT_BAR_INITIAL_X, Constants.LEFT_BAR_INITIAL_Y),
                Constants.BAR_WIDHT,
                Constants.BAR_HEIGHT,
                new Vector2D(Constants.BAR_INITIAL_X_VEL, Constants.BAR_INITIAL_Y_VEL),
                new Vector2D(1, 0),
                Color.GREEN);
        this.rightBar = new Bar(new Vector2D(Constants.RIGHT_BAR_INITAL_X, Constants.RIGHT_BAR_INITIAL_Y),
                Constants.BAR_WIDHT,
                Constants.BAR_HEIGHT,
                new Vector2D(Constants.BAR_INITIAL_X_VEL, Constants.BAR_INITIAL_Y_VEL),
                new Vector2D(-1, 0),
                Color.GREEN);
        this.ball = new Ball(new Vector2D(Constants.BALL_INITIAL_X, Constants.BALL_INITIAL_Y),
                Constants.BALL_WIDTH,
                Constants.BALL_HEIGHT,
                new Vector2D(Constants.BALL_INITIAL_X_VEL, Constants.BALL_INITIAL_Y_VEL),
                new Vector2D(-1, 0),
                Color.RED);

        leftBarController = new PlayerController(leftBar, keyListener);
        rightBarController = new PlayerController(rightBar, keyListener);

        colissionHelper = new ColissionHelper();

        ball.setDirection(new Vector2D(-1, -1));

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
}
