package org.game.colission;

import org.game.gameobject.Ball;
import org.game.gameobject.Bar;
import org.game.utils.Constants;

public class RightBarBallColission extends BarBallColission {

    @Override
    public boolean isBarBallColissionOnSurface(Bar bar, Ball ball, double delta) {
        double barTop = bar.getPosition().getY();
        double barBottom = bar.getPosition().getY() + Constants.BAR_HEIGHT;
        double barLeft = bar.getPosition().getX();
        double barRight = bar.getPosition().getX() + Constants.BAR_WIDHT;

        double ballTop = ball.getPosition().getY() + ((ball.getVelocity().getY() * delta) * ball.getDirection().getY());
        double ballBottom = ball.getPosition().getY() + Constants.BALL_HEIGHT + ((ball.getVelocity().getY() * delta) * ball.getDirection().getY());
        double ballRight = ball.getPosition().getX() + Constants.BALL_WIDTH + ((ball.getVelocity().getX() * delta) * ball.getDirection().getX());
/*
        System.out.println("==========================");
        System.out.println("RIGHT BALL");
        System.out.println("BAR POSITION: " + bar.getPosition().toString());
        System.out.println("BAR VELOCITY: " + bar.getVelocity().toString());
        System.out.println("BALL POSITION: " + ball.getPosition().toString());
        System.out.println("BALL VELOCITY: " + ball.getVelocity().toString());
        System.out.println("BAR TOP: " + barTop);
        System.out.println("BAR BOTTOM: " + barBottom);
        System.out.println("BAR LEFT: " + barLeft);
        System.out.println("BAR RIGHT: " + barRight);
        System.out.println("BALL TOP: " + ballTop);
        System.out.println("BALL BOTTOM: " + ballBottom);
        System.out.println("BALL RIGHT: " + ballRight);
        System.out.println("DELTA: " + delta);
        System.out.println("BALL DIRECTION: " + ball.getDirection().getX());
        System.out.println("EVALUATION: " + (barLeft <= ballRight &&
                ballRight <= barRight &&
                barTop <= ballTop &&
                ballTop <= barBottom &&
                barTop <= ballBottom &&
                ballBottom <= barBottom));
        System.out.println("==========================");
*/
        return barLeft <= ballRight &&
                ballRight <= barRight &&
                barTop <= ballTop &&
                ballTop <= barBottom &&
                barTop <= ballBottom &&
                ballBottom <= barBottom;
    }

    @Override
    public boolean isBarBallColissionOnEdges(Bar bar, Ball ball, double delta) {
        double barTop = bar.getPosition().getY();
        double barBottom = bar.getPosition().getY() + Constants.BAR_HEIGHT;
        double barLeft = bar.getPosition().getX();
        double barRight = bar.getPosition().getX() + Constants.BAR_WIDHT;

        double ballTop = ball.getPosition().getY() + ((ball.getVelocity().getY() * delta) * ball.getDirection().getY());
        double ballBottom = ball.getPosition().getY() + Constants.BALL_HEIGHT + ((ball.getVelocity().getY() * delta) * ball.getDirection().getY());
        double ballRight = ball.getPosition().getX() + Constants.BALL_WIDTH + ((ball.getVelocity().getX() * delta) * ball.getDirection().getX());
        double ballLeft = ball.getPosition().getX() + (ball.getVelocity().getX() * delta);

        boolean isColissionWithTopEdge = ballTop <= barTop && barTop <= ballBottom;
        boolean isColissionWithBottomEdge = ballTop <= barBottom && barBottom <= ballBottom;
        boolean isColissionWithingBarRange = (ballLeft <= barRight && barLeft <= ballLeft) || (ballRight <= barRight && barLeft <= ballRight);

        return isColissionWithBottomEdge && isColissionWithingBarRange || isColissionWithTopEdge && isColissionWithingBarRange;
    }
}
