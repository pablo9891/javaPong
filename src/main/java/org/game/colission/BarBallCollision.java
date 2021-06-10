package org.game.colission;

import org.game.gameobject.Ball;
import org.game.gameobject.Bar;
import org.game.math.Vector2D;
import org.game.utils.Constants;

import java.awt.Color;

public abstract class BarBallCollision {

    private double getRandomNumber(double min, double max) {
        return min + (Math.random() * max);
    }

    private double getNewDirection(boolean shouldReduce, double direction) {
        double newDirection;

        if(direction >= 2 || direction <= -2)
            return direction;

        if(shouldReduce) {
            do {
                newDirection = direction - getRandomNumber(0.1, 0.3);
            } while(newDirection <= -2);
        } else {
            do {
                newDirection = direction + getRandomNumber(0.1, 0.3);
            } while(newDirection >= 2);
        }

        return Math.abs(newDirection);
    }

    private double getNewVelocity(boolean shouldReduce, double velocity) {
        double newVelocity;

        if(velocity >= Constants.MAX_BALL_VELOCITY || velocity <= Constants.MIN_BALL_VELOCITY)
            return velocity;

        if(shouldReduce) {
            do {
                newVelocity = velocity - getRandomNumber(1, 15);
            } while(newVelocity <= Constants.MIN_BALL_VELOCITY);
        } else {
            do {
                newVelocity = velocity + getRandomNumber(1, 15);
            } while(newVelocity >= Constants.MAX_BALL_VELOCITY);
        }

        return newVelocity;
    }

    protected boolean isCenterBetweenCoords(double top, double bottom, double mid) {
        return  top <= mid && mid <= bottom;
    }

    protected boolean isTopEdge(double barTop, double ballTop, double ballBottom) {
        return ballTop <= barTop && barTop <= ballBottom;
    }

    protected void processBallCollisionWithTopAndBottom(Bar bar, Ball ball) {
        double newXVel = getNewVelocity(false, ball.getVelocity().getX());
        double newYVel = getNewVelocity(false, ball.getVelocity().getY());

        if (ball.getDirection().getY() > 0) { // if ball goes up
            if (bar.getDirection().getY() > 0) { // if bar goes up
                ball.setDirection(new Vector2D(ball.getDirection().getX() * (-1), ball.getDirection().getY()));
                ball.setVelocity(new Vector2D(newXVel, newYVel));
            } else if (bar.getDirection().getY() < 0) { // if bar goes down
                ball.setDirection(new Vector2D(ball.getDirection().getX() * (-1), ball.getDirection().getY() * (-1)));
                ball.setVelocity(new Vector2D(newXVel, newYVel));
            } else {
                ball.setDirection(new Vector2D(ball.getDirection().getX() * (-1), ball.getDirection().getY()));
            }
        } else { // ball goes down
            if (bar.getDirection().getY() > 0) { // if bar goes up
                ball.setDirection(new Vector2D(ball.getDirection().getX() * (-1), ball.getDirection().getY() * (-1)));
                ball.setVelocity(new Vector2D(newXVel, newYVel));
            } else if (bar.getDirection().getY() < 0) { // if bar goes down
                ball.setDirection(new Vector2D(ball.getDirection().getX() * (-1), ball.getDirection().getY()));
                ball.setVelocity(new Vector2D(newXVel, newYVel));
            } else {
                ball.setDirection(new Vector2D(ball.getDirection().getX() * (-1), ball.getDirection().getY()));
            }
        }

        if(Constants.IS_DEBUG_SET) {
            System.out.println("BALL DIRECTION: " + ball.getDirection());
            System.out.println("BALL VELOCITY: " + ball.getVelocity());
        }
    }

    protected void middleBarCollision(Ball ball) {
        double newXVel = getNewVelocity(true, ball.getVelocity().getX());
        double newYVel = getNewVelocity(true, ball.getVelocity().getY());

        ball.setDirection(new Vector2D((-1) * ball.getDirection().getX(), ball.getDirection().getY()));
        ball.setVelocity(new Vector2D(newXVel, newYVel));

        if(Constants.IS_DEBUG_SET) {
            System.out.println("BALL DIRECTION: " + ball.getDirection());
            System.out.println("BALL VELOCITY: " + ball.getVelocity());
        }
    }

    public void processBarBallCollisionOnEdges(Bar bar, Ball ball, double delta) {
        ball.setColor(Color.WHITE);
        System.out.println("Collision with edge");
        double ballTop = ball.getPosition().getY() + ((ball.getVelocity().getY() * delta) * ball.getDirection().getY());
        double ballBottom = ball.getPosition().getY() + Constants.BALL_HEIGHT + ((ball.getVelocity().getY() * delta) * ball.getDirection().getY());
        double barTop = bar.getPosition().getY();

        double barMid = bar.getPosition().getX() + (Constants.BAR_WIDTH / 2);
        double ballMid = ball.getPosition().getX() + (Constants.BALL_WIDTH / 2);

        // Si la barra y la bola suben depende la reaccion de la bola
        if(isTopEdge(barTop, ballTop, ballBottom)) {
            if(barMid <= ballMid)
                ball.setDirection(new Vector2D(ball.getDirection().getX() * (-1), -1));
            else
                ball.setDirection(new Vector2D(ball.getDirection().getX(), -1));
        } else {
            if (bar.getForwardVector().getX() > 0) {
                if (barMid <= ballMid)
                    ball.setDirection(new Vector2D(ball.getDirection().getX() * (-1), -1));
                else
                    ball.setDirection(new Vector2D(ball.getDirection().getX(), -1));
            }
        }
    }

    protected void processBarBallCollisionOnSurface(Bar bar, Ball ball, double delta) {
        double barTop = bar.getPosition().getY();
        double barBottom = bar.getPosition().getY() + Constants.BAR_HEIGHT;
        double barTopMid = barTop + (Constants.BAR_HEIGHT / 3);
        double barMidBottom = barTopMid + (Constants.BAR_HEIGHT / 3);

        double ballTop = ball.getPosition().getY() + ((ball.getVelocity().getY() * delta) * ball.getDirection().getY());
        double ballBottom = ball.getPosition().getY() + Constants.BALL_HEIGHT + ((ball.getVelocity().getY() * delta) * ball.getDirection().getY());

        double midBall = ballTop + (ballBottom - ballTop) / 2;

        if(Constants.IS_DEBUG_SET) {
            System.out.println("==========================");
            System.out.println("BARTOPMID: " +  barTopMid);
            System.out.println("BARMIDBOTTOM: " + barMidBottom);
            System.out.println("MID BALL: " + midBall);
            System.out.println("IS BETWEEN TOP COORDS: " + isCenterBetweenCoords(barTop, barTopMid, (ballBottom - ballTop) / 2));
            System.out.println("IS BETWEEN MID COORDS: " + isCenterBetweenCoords(barTopMid, barMidBottom, (ballBottom - ballTop) / 2));
            System.out.println("IS BETWEEN BOTTOM COORDS: " + isCenterBetweenCoords(barMidBottom, barBottom, (ballBottom - ballTop) / 2));
            System.out.println("==========================");
        }

        if(isCenterBetweenCoords(barTop, barTopMid, midBall)) {
            if(Constants.IS_DEBUG_SET)
                System.out.println("Collision with TOP");
            ball.setColor(Color.CYAN);
            processBallCollisionWithTopAndBottom(bar, ball);
        }
        else if(isCenterBetweenCoords(barTopMid, barMidBottom, midBall)) {
            if(Constants.IS_DEBUG_SET)
                System.out.println("Collision with MID");
            ball.setColor(Color.ORANGE);
            middleBarCollision(ball);
        }
        else if(isCenterBetweenCoords(barMidBottom, barBottom, midBall)) {
            if(Constants.IS_DEBUG_SET)
                System.out.println("Collision with BOTTOM");
            ball.setColor(Color.WHITE);
            processBallCollisionWithTopAndBottom(bar, ball);
        }
    }

    public abstract boolean isBarBallCollisionOnSurface(Bar bar, Ball ball, double delta);

    public abstract boolean isBarBallCollisionOnEdges(Bar bar, Ball ball, double delta);
}
