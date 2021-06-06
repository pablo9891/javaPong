package org.game.colission;

import org.game.math.Vector2D;
import org.game.gameobject.Ball;
import org.game.gameobject.Bar;

import org.game.sound.SoundManager;
import org.game.utils.Constants;

public class ColissionHelper {

    private BarBallColission barBallColission;

    public ColissionHelper() {
        // By default
        barBallColission = new LeftBarBallColission();
    }

    public void processColissionBallWithTopAndBottom(Ball ball, double delta) {
        Vector2D newBallPosition;
        if(ball.getDirection().getY() < 0)
            newBallPosition = new Vector2D(ball.getPosition().getX(), (ball.getPosition().getY() - (ball.getVelocity().getY() * delta)));
        else
            newBallPosition = new Vector2D(ball.getPosition().getX(), (ball.getPosition().getY() + (ball.getVelocity().getY() * delta)));

        if(newBallPosition.getY() < (Constants.TOP_BAR + Constants.TOP_BAR_MARGIN)) {
            ball.setDirection(new Vector2D(ball.getDirection().getX(), 1));
            SoundManager.play("bounce", false);
        }
        if(newBallPosition.getY() + Constants.BALL_HEIGHT > (Constants.BOTTOM_BAR - Constants.TOP_BAR_MARGIN)) {
            ball.setDirection(new Vector2D(ball.getDirection().getX(), -1));
            SoundManager.play("bounce", false);
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

    public void processBarBallColission(Bar bar, Ball ball, double delta) {
        if(bar.getForwardVector().getX() > 0)
            barBallColission = new LeftBarBallColission();
        if(bar.getForwardVector().getX() < 0)
            barBallColission = new RightBarBallColission();

        if(barBallColission.isBarBallColissionOnEdges(bar, ball, delta)) {
            barBallColission.processBarBallColissionOnEdges(bar, ball, delta);
            SoundManager.play("bounce", false);
        }
        if(barBallColission.isBarBallColissionOnSurface(bar, ball, delta)) {
            barBallColission.processBarBallColissionOnSurface(bar, ball, delta);
            SoundManager.play("bounce", false);
        }
    }
}
