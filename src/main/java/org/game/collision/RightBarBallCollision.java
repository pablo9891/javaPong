package org.game.collision;

import org.game.gameobject.Ball;
import org.game.gameobject.Bar;
import org.game.utils.Constants;

public class RightBarBallCollision extends BarBallCollision {

    @Override
    public boolean isBarBallCollisionOnSurface(Bar bar, Ball ball, double delta) {
        double barTop = bar.getPosition().getY();
        double barBottom = bar.getPosition().getY() + Constants.BAR_HEIGHT;
        double barLeft = bar.getPosition().getX();
        double barRight = bar.getPosition().getX() + Constants.BAR_WIDTH;

        double ballTop = ball.getPosition().getY() + ((ball.getVelocity().getY() * delta) * ball.getDirection().getY());
        double ballBottom = ball.getPosition().getY() + Constants.BALL_HEIGHT + ((ball.getVelocity().getY() * delta) * ball.getDirection().getY());
        double ballRight = ball.getPosition().getX() + Constants.BALL_WIDTH + ((ball.getVelocity().getX() * delta) * ball.getDirection().getX());

        return barLeft <= ballRight &&
                ballRight <= barRight &&
                barTop <= ballTop &&
                ballTop <= barBottom &&
                barTop <= ballBottom &&
                ballBottom <= barBottom;
    }

    @Override
    public boolean isBarBallCollisionOnEdges(Bar bar, Ball ball, double delta) {
        double barTop = bar.getPosition().getY();
        double barBottom = bar.getPosition().getY() + Constants.BAR_HEIGHT;
        double barLeft = bar.getPosition().getX();
        double barRight = bar.getPosition().getX() + Constants.BAR_WIDTH;

        double ballTop = ball.getPosition().getY() + ((ball.getVelocity().getY() * delta) * ball.getDirection().getY());
        double ballBottom = ball.getPosition().getY() + Constants.BALL_HEIGHT + ((ball.getVelocity().getY() * delta) * ball.getDirection().getY());
        double ballRight = ball.getPosition().getX() + Constants.BALL_WIDTH + ((ball.getVelocity().getX() * delta) * ball.getDirection().getX());
        double ballLeft = ball.getPosition().getX() + (ball.getVelocity().getX() * delta);

        boolean isCollisionWithTopEdge = ballTop <= barTop && barTop <= ballBottom;
        boolean isCollisionWithBottomEdge = ballTop <= barBottom && barBottom <= ballBottom;
        boolean isCollisionWithingBarRange = (ballLeft <= barRight && barLeft <= ballLeft) || (ballRight <= barRight && barLeft <= ballRight);

        return isCollisionWithBottomEdge && isCollisionWithingBarRange || isCollisionWithTopEdge && isCollisionWithingBarRange;
    }
}
