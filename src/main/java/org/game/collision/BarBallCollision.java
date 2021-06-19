package org.game.collision;

import org.game.gameobject.Ball;
import org.game.gameobject.Bar;
import org.game.math.Vector2D;
import org.game.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;

public abstract class BarBallCollision {

    Logger logger = LoggerFactory.getLogger(BarBallCollision.class);

    private double getNewAngleInRadians(Bar bar, Ball ball, double maxAngle) {
        double barMid = bar.getPosition().getY() + (Constants.BAR_HEIGHT / 2);
        double ballMid = ball.getPosition().getY() + (Constants.BALL_HEIGHT / 2);

        double pointOfCollision = barMid - ballMid;
        double interval = pointOfCollision / (Constants.BAR_HEIGHT / 2);

        if(Constants.IS_DEBUG_SET) {
            logger.debug("Point of collision {}", pointOfCollision);
            logger.debug("Interval {}", interval);
        }

        return (interval * maxAngle) + Constants.MIN_ANGLE;
    }

    private boolean isBarMoving(Bar bar) {
        return bar.getDirection().getY() > 0 || bar.getDirection().getY() < 0;
    }

    private Vector2D getNewVelocity(Bar bar, Ball ball, double maxAngle) {
        double angleInRadians = getNewAngleInRadians(bar, ball, maxAngle);
        double newYVel = Math.abs((Math.sin(angleInRadians) * Constants.MAX_BALL_VELOCITY) +
                Constants.MIN_BALL_VELOCITY);
        double newXVel = Math.abs((Math.cos(angleInRadians) * Constants.MAX_BALL_VELOCITY) +
                Constants.MIN_BALL_VELOCITY);

        if(Constants.IS_DEBUG_SET)
            logger.debug("angleInRadians {}", angleInRadians);

        return new Vector2D(newXVel, newYVel);
    }

    private Vector2D getNewDirection(Bar bar, Ball ball) {
        double oldSign = Math.signum(ball.getDirection().getX());
        double newYDir = Math.signum(ball.getDirection().getY());

        if(Math.signum(bar.getDirection().getY()) > 0 && Math.signum(ball.getDirection().getY()) < 0 ||
                Math.signum(bar.getDirection().getY()) < 0 && Math.signum(ball.getDirection().getY()) > 0)
            newYDir *= -1;

        return new Vector2D(oldSign * (-1.0), newYDir);
    }

    public void processBarBallCollisionOnEdges(Bar bar, Ball ball) {
        ball.setColor(Constants.BALL_COLOR);
        if(isBarMoving(bar)) {
            if(Constants.IS_DEBUG_SET) {
                logger.debug("New collision between ball and bar on the EDGE of the bar in MOVEMENT");
                logger.debug("{}", bar);
                logger.debug("{}", ball);
            }
            ball.setVelocity(getNewVelocity(bar, ball, Constants.MAX_ANGLE_WITH_EDGES));
            ball.setDirection(getNewDirection(bar, ball));
        } else {
            if(Constants.IS_DEBUG_SET) {
                logger.debug("New collision between ball and bar on the SURFACE of the bar");
                logger.debug("{}", bar);
                logger.debug("{}", ball);
            }
            double oldSign = Math.signum(ball.getDirection().getX());
            ball.setDirection(new Vector2D( oldSign * (-1.0), ball.getDirection().getY()));
        }
        if(Constants.IS_DEBUG_SET) {
            logger.debug("New Ball {}", ball);

            if (ball.getDirection().getY() > 0)
                ball.setColor(Color.BLUE);
            else
                ball.setColor(Color.YELLOW);
        }
    }

    protected void processBarBallCollisionOnSurface(Bar bar, Ball ball) {
        if(isBarMoving(bar)) {
            if(Constants.IS_DEBUG_SET) {
                logger.debug("New collision between ball and bar on the SURFACE of the bar in MOVEMENT");
                logger.debug("Bar {}", (bar.getForwardVector().getX() > 0) ? "LEFT" : "RIGHT");
                logger.debug("{}", bar);
                logger.debug("{}", ball);
            }
            ball.setVelocity(getNewVelocity(bar, ball, Constants.MAX_ANGLE_WITH_SURFACE));
            ball.setDirection(getNewDirection(bar, ball));
        } else {
            if(Constants.IS_DEBUG_SET) {
                logger.debug("New collision between ball and bar on the SURFACE of the bar");
                logger.debug("{}", bar);
                logger.debug("{}", ball);
            }
            double oldSign = Math.signum(ball.getDirection().getX());
            ball.setDirection(new Vector2D( oldSign * (-1.0), ball.getDirection().getY()));
        }
        if(Constants.IS_DEBUG_SET) {
            logger.debug("New Ball {}", ball);

            if (ball.getDirection().getY() > 0)
                ball.setColor(Color.BLUE);
            else
                ball.setColor(Color.YELLOW);
        }
    }

    public abstract boolean isBarBallCollisionOnSurface(Bar bar, Ball ball, double delta);

    public abstract boolean isBarBallCollisionOnEdges(Bar bar, Ball ball, double delta);
}
