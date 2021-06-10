package org.game.colission;

import org.game.math.Vector2D;
import org.game.gameobject.Ball;
import org.game.gameobject.Bar;

import org.game.sound.SoundManager;
import org.game.utils.Constants;

public class CollisionHelper {

    private BarBallCollision barBallCollision;
    private static final String BOUNCE_SOUND_KEY = "bounce";

    public CollisionHelper() {
        // By default
        barBallCollision = new LeftBarBallCollision();
    }

    public void processCollisionBallWithTopAndBottom(Ball ball, double delta) {
        Vector2D newBallPosition;
        if(ball.getDirection().getY() < 0)
            newBallPosition = new Vector2D(ball.getPosition().getX(), (ball.getPosition().getY() - (ball.getVelocity().getY() * delta)));
        else
            newBallPosition = new Vector2D(ball.getPosition().getX(), (ball.getPosition().getY() + (ball.getVelocity().getY() * delta)));

        if(newBallPosition.getY() < (Constants.TOP_BAR + Constants.TOP_BAR_MARGIN)) {
            ball.setDirection(new Vector2D(ball.getDirection().getX(), 1));
            SoundManager.play(BOUNCE_SOUND_KEY, false);
        }
        if(newBallPosition.getY() + Constants.BALL_HEIGHT > (Constants.BOTTOM_BAR - Constants.TOP_BAR_MARGIN)) {
            ball.setDirection(new Vector2D(ball.getDirection().getX(), -1));
            SoundManager.play(BOUNCE_SOUND_KEY, false);
        }
    }

    public boolean isPointForBar(Bar bar, Ball ball) {
        if (bar.getForwardVector().getX() < 0) {
            if (ball.getPosition().getX() < 0)
                return true;
        } else {
            if (ball.getPosition().getX() > Constants.WINDOW_WIDTH)
                return true;
        }

        return false;
    }

    public void processBarBallCollision(Bar bar, Ball ball, double delta) {
        if(bar.getForwardVector().getX() > 0)
            barBallCollision = new LeftBarBallCollision();
        if(bar.getForwardVector().getX() < 0)
            barBallCollision = new RightBarBallCollision();

        if(barBallCollision.isBarBallCollisionOnEdges(bar, ball, delta)) {
            barBallCollision.processBarBallCollisionOnEdges(bar, ball, delta);
            SoundManager.play(BOUNCE_SOUND_KEY, false);
        }
        if(barBallCollision.isBarBallCollisionOnSurface(bar, ball, delta)) {
            barBallCollision.processBarBallCollisionOnSurface(bar, ball, delta);
            SoundManager.play(BOUNCE_SOUND_KEY, false);
        }
    }
}
