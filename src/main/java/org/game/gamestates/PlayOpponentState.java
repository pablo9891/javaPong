package org.game.gamestates;

import org.game.colission.ColissionHelper;
import org.game.fonts.Text;
import org.game.gameobject.Ball;
import org.game.gameobject.Bar;
import org.game.gameobject.GameObject;
import org.game.input.KeyListenerCallback;
import org.game.input.KeyboardCallback;
import org.game.input.PlayerController;
import org.game.math.Vector2D;
import org.game.sound.SoundManager;
import org.game.time.Time;
import org.game.utils.Constants;
import org.game.window.Window;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayOpponentState extends GameState {

    private Bar leftBar, rightBar;
    private Ball ball;
    private int leftMarker, rightMarker;
    private PlayerController leftBarController, rightBarController;
    private ColissionHelper colissionHelper;
    private KeyListenerCallback keyListener;

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

    public PlayOpponentState(Window w) {
        super(w);
    }

    @Override
    public void loadResources () {
        loadInputConfiguration();
        loadSoundConfiguration();

        Constants.TOP_BAR = window.getInsets().top;
        Constants.BOTTOM_BAR = Constants.WINDOW_HEIGHT;
        leftMarker = 0;
        rightMarker = 0;
    }

    @Override
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

    @Override
    public void render(Graphics2D buffer) {
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0, 0, window.getWindowWidth(), window.getWindowHeight());

        leftBar.draw(buffer);
        rightBar.draw(buffer);
        ball.draw(buffer);

        window.getText().draw(String.valueOf(leftMarker), buffer, 48, 200, 100, Color.WHITE);
        window.getText().draw(String.valueOf(rightMarker), buffer, 48, Constants.WINDOW_WIDTH - 200, 100,
                Color.WHITE);
    }

    @Override
    public void loadGame() {
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
    }

    private void loadInputConfiguration() {
        keyListener = new KeyListenerCallback(window);
        window.addKeyListener(keyListener);
    }

    private void loadSoundConfiguration() {
        window.getSoundManager().addSound("bounce", "bounce_sound.wav");
    }
}
