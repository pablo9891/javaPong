package org.game.gamestates.states;

import org.game.collision.CollisionHelper;
import org.game.fonts.Text;
import org.game.gameobject.Ball;
import org.game.gameobject.Bar;
import org.game.gameobject.GameObject;
import org.game.gamestates.GameState;
import org.game.input.callback.KeyboardCallback;
import org.game.input.callback.keyboardimp.KeyListenerCallback;
import org.game.input.controller.PlayerController;
import org.game.input.controller.ia.IAController;
import org.game.math.Vector2D;
import org.game.utils.Constants;
import org.game.window.Window;

import java.awt.Graphics2D;

import java.awt.event.KeyEvent;

public class PlayIAState extends GameState {
    private Bar leftBar;
    private Bar rightBar;
    private Ball ball;
    private int leftMarker;
    private int rightMarker;
    private PlayerController leftBarController;
    private IAController rightBarController;
    private CollisionHelper collisionHelper;
    private KeyListenerCallback keyListener;
    private Text leftMarkerText;
    private Text rightMarkerText;

    private static final String IA = "IA";
    private static final String P1 = "P1";

    KeyboardCallback<GameObject, KeyListenerCallback, Double> leftBarListenerCallback = (gameObject, listener, delta) -> {
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

    public PlayIAState(Window w) {
        super(w);
    }

    @Override
    public void loadResources () {
        loadInputConfiguration();
        loadSoundConfiguration();

        //Constants.TOP_BAR = window.getFrame().getInsets().top;
        Constants.BOTTOM_BAR = window.getHeight();

        leftMarker = 0;
        rightMarker = 0;
        leftMarkerText = new Text(String.valueOf(leftMarker), 200, 100, 48, Constants.MARKER_COLOR);
        rightMarkerText = new Text(String.valueOf(rightMarker),
                Constants.WINDOW_WIDTH - 200,
                100,
                48,
                Constants.MARKER_COLOR);
    }

    @Override
    public void update(double delta) {
        if(rightMarker == Constants.MAX_POINTS)
            window.setNewState(new FinishGameState(window, IA));

        if(leftMarker == Constants.MAX_POINTS)
            window.setNewState(new FinishGameState(window, P1));

        leftBarController.update(delta, leftBarListenerCallback);
        rightBarController.update(delta);

        collisionHelper.processCollisionBallWithTopAndBottom(ball, delta);
        collisionHelper.processBarBallCollision(leftBar, ball, delta);
        collisionHelper.processBarBallCollision(rightBar, ball, delta);

        if(collisionHelper.isPointForBar(leftBar, ball)) {
            double initialVelX = getRandomVel(Constants.BALL_INITIAL_X_VEL_MIN, Constants.BALL_INITIAL_X_VEL_MAX);
            double initialVelY = getRandomVel(Constants.BALL_INITIAL_Y_VEL_MIN, Constants.BALL_INITIAL_Y_VEL_MAX);

            leftMarker++;
            ball.setPosition(new Vector2D(Constants.BALL_INITIAL_X, Constants.BALL_INITIAL_Y));
            ball.setVelocity(new Vector2D(initialVelX, initialVelY));
            ball.setDirection(new Vector2D(1, getRandomDir()));
        }
        if(collisionHelper.isPointForBar(rightBar, ball)) {
            double initialVelX = getRandomVel(Constants.BALL_INITIAL_X_VEL_MIN, Constants.BALL_INITIAL_X_VEL_MAX);
            double initialVelY = getRandomVel(Constants.BALL_INITIAL_Y_VEL_MIN, Constants.BALL_INITIAL_Y_VEL_MAX);

            rightMarker++;
            ball.setPosition(new Vector2D(Constants.BALL_INITIAL_X, Constants.BALL_INITIAL_Y));
            ball.setVelocity(new Vector2D(initialVelX, initialVelY));
            ball.setDirection(new Vector2D(-1, getRandomDir()));
        }

        leftMarkerText.setText(String.valueOf(leftMarker));
        rightMarkerText.setText(String.valueOf(rightMarker));

        leftBar.update(delta);
        rightBar.update(delta);
        ball.update(delta);
    }

    @Override
    public void render(Graphics2D buffer) {
        window.getText().draw(leftMarkerText, buffer);
        window.getText().draw(rightMarkerText, buffer);

        leftBar.draw(buffer);
        rightBar.draw(buffer);
        ball.draw(buffer);
    }

    @Override
    public void loadGame() {
        double initialVelX = getRandomVel(Constants.BALL_INITIAL_X_VEL_MIN, Constants.BALL_INITIAL_X_VEL_MAX);
        double initialVelY = getRandomVel(Constants.BALL_INITIAL_Y_VEL_MIN, Constants.BALL_INITIAL_Y_VEL_MAX);

        this.leftBar = new Bar(new Vector2D(Constants.LEFT_BAR_INITIAL_X, Constants.LEFT_BAR_INITIAL_Y),
                Constants.BAR_WIDTH,
                Constants.BAR_HEIGHT,
                new Vector2D(Constants.BAR_INITIAL_X_VEL, Constants.BAR_INITIAL_Y_VEL),
                new Vector2D(1, 0),
                Constants.BAR_COLOR);
        this.rightBar = new Bar(new Vector2D(Constants.RIGHT_BAR_INITAL_X, Constants.RIGHT_BAR_INITIAL_Y),
                Constants.BAR_WIDTH,
                Constants.BAR_HEIGHT,
                new Vector2D(Constants.BAR_INITIAL_X_VEL, Constants.BAR_INITIAL_Y_VEL),
                new Vector2D(-1, 0),
                Constants.BAR_COLOR);
        this.ball = new Ball(new Vector2D(Constants.BALL_INITIAL_X, Constants.BALL_INITIAL_Y),
                Constants.BALL_WIDTH,
                Constants.BALL_HEIGHT,
                new Vector2D(initialVelX, initialVelY),
                new Vector2D(-1, 0),
                Constants.BALL_COLOR);

        leftBarController = new PlayerController(leftBar, keyListener);
        rightBarController = new IAController(rightBar, ball);

        collisionHelper = new CollisionHelper();

        ball.setDirection(new Vector2D(getRandomDir(), getRandomDir()));
    }

    private void loadInputConfiguration() {
        keyListener = new KeyListenerCallback(window);
        window.addKeyListener(keyListener);
    }

    private void loadSoundConfiguration() {
        window.getSoundManager().addSound("bounce", "bounce_sound.wav");
    }
}
