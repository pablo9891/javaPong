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

    private double getNewAngleInRadians(Bar bar, Ball ball, double maxVelocity) {
        double barMid = bar.getPosition().getY() + (Constants.BAR_HEIGHT / 2);
        double ballMid = ball.getPosition().getY() + (Constants.BALL_HEIGHT / 2);

        double pointOfCollision = barMid - ballMid;
        double interval = pointOfCollision / (Constants.BAR_HEIGHT / 2);

        return interval * maxVelocity;
    }

    public void processBarBallCollisionOnEdges(Bar bar, Ball ball) {
        ball.setColor(Color.WHITE);
        double angleInRadians = getNewAngleInRadians(bar, ball, Constants.MAX_ANGLE_WITH_EDGES);
        double newYVel = Math.abs(Math.sin(angleInRadians) * Constants.MAX_BALL_VELOCITY);
        double newXVel = Math.cos(angleInRadians) * Constants.MAX_BALL_VELOCITY;

        double oldSign = Math.signum(ball.getDirection().getX());
        ball.setVelocity(new Vector2D(newXVel, newYVel));
        ball.setDirection(new Vector2D( oldSign * (-1.0), ball.getDirection().getY()));

        if(Constants.IS_DEBUG_SET) {
            logger.debug("==========================");
            logger.debug("Collision with edge");
            logger.debug("angleInRadians {}", angleInRadians);
            logger.debug("newXVel {}", newXVel);
            logger.debug("newYVel {}", newYVel);
            logger.debug("==========================");
        }
    }

    protected void processBarBallCollisionOnSurface(Bar bar, Ball ball) {
        double angleInRadians = getNewAngleInRadians(bar, ball, Constants.MAX_ANGLE_WITH_SURFACE);
        double newYVel = Math.abs(Math.sin(angleInRadians) * Constants.MAX_BALL_VELOCITY);
        double newXVel = Math.cos(angleInRadians) * Constants.MAX_BALL_VELOCITY;

        double oldSign = Math.signum(ball.getDirection().getX());
        ball.setVelocity(new Vector2D(newXVel, newYVel));
        ball.setDirection(new Vector2D( oldSign * (-1.0), ball.getDirection().getY()));

        if(Constants.IS_DEBUG_SET) {
            logger.debug("==========================");
            logger.debug("Collision with surfaces");
            logger.debug("angleInRadians {}", angleInRadians);
            logger.debug("newXVel {}", newXVel);
            logger.debug("newYVel {}", newYVel);
            logger.debug("==========================");
        }
    }


    public abstract boolean isBarBallCollisionOnSurface(Bar bar, Ball ball, double delta);

    public abstract boolean isBarBallCollisionOnEdges(Bar bar, Ball ball, double delta);
}
