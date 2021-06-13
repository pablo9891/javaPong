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

        return (interval * maxVelocity) + Constants.MIN_ANGLE;
    }

    private boolean isBarMoving(Bar bar) {
        return bar.getDirection().getY() > 0 || bar.getDirection().getY() < 0;
    }

    private Vector2D getNewVelocity(Bar bar, Ball ball) {
        double angleInRadians = getNewAngleInRadians(bar, ball, Constants.MAX_ANGLE_WITH_EDGES);
        double newYVel = Math.abs(Math.sin(angleInRadians) * Constants.MAX_BALL_VELOCITY);
        double newXVel = Math.abs(Math.cos(angleInRadians) * Constants.MAX_BALL_VELOCITY);

        if(Constants.IS_DEBUG_SET) {
            logger.debug("angleInRadians {}", angleInRadians);
            logger.debug("newXVel {}", newXVel);
            logger.debug("newYVel {}", newYVel);
        }

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
        ball.setColor(Color.WHITE);
        if(isBarMoving(bar)) {
            if(Constants.IS_DEBUG_SET) {
                logger.debug(" ");
                logger.debug("New collision with Edge");
                logger.debug("Bar {}", (bar.getForwardVector().getX() > 0) ? "LEFT" : "RIGHT");
                logger.debug("Bar direction {}", (bar.getDirection().getY() > 0) ? "DOWN" : "UP");
                logger.debug("Ball X direction {}", (bar.getDirection().getX() < 0) ? "LEFT" : "RIGHT");
                logger.debug("Ball Y direction {}", (bar.getDirection().getY() > 0) ? "DOWN" : "UP");
                logger.debug("Old Vector velocity {}", ball.getVelocity().toString());
                logger.debug("Old Vector direction {}", ball.getDirection().toString());
                ball.setVelocity(getNewVelocity(bar, ball));
                ball.setDirection(getNewDirection(bar, ball));
                logger.debug("New Vector velocity {}", ball.getVelocity().toString());
                logger.debug("New Vector direction {}", ball.getDirection().toString());
                logger.debug("Ball new X direction {}", (ball.getDirection().getX() < 0) ? "LEFT" : "RIGHT");
                logger.debug("Ball new Y direction {}", (ball.getDirection().getY() > 0) ? "DOWN" : "UP");

                if (ball.getDirection().getY() > 0)
                    ball.setColor(Color.BLUE);
                else
                    ball.setColor(Color.YELLOW);
            }
        } else {
            double oldSign = Math.signum(ball.getDirection().getX());
            ball.setDirection(new Vector2D( oldSign * (-1.0), ball.getDirection().getY()));
        }
    }

    protected void processBarBallCollisionOnSurface(Bar bar, Ball ball) {
        if(isBarMoving(bar)) {
            if(Constants.IS_DEBUG_SET) {
                logger.debug(" ");
                logger.debug("New collision with Surface");
                logger.debug("Bar {}", (bar.getForwardVector().getX() > 0) ? "LEFT" : "RIGHT");
                logger.debug("Bar direction {}", (bar.getDirection().getY() > 0) ? "DOWN" : "UP");
                logger.debug("Ball X direction {}", (bar.getDirection().getX() < 0) ? "LEFT" : "RIGHT");
                logger.debug("Ball Y direction {}", (bar.getDirection().getY() > 0) ? "DOWN" : "UP");
                logger.debug("Old Vector velocity {}", ball.getVelocity().toString());
                logger.debug("Old Vector direction {}", ball.getDirection().toString());
                ball.setVelocity(getNewVelocity(bar, ball));
                ball.setDirection(getNewDirection(bar, ball));
                logger.debug("New Vector velocity {}", ball.getVelocity().toString());
                logger.debug("New Vector direction {}", ball.getDirection().toString());
                logger.debug("Ball new X direction {}", (ball.getDirection().getX() < 0) ? "LEFT" : "RIGHT");
                logger.debug("Ball new Y direction {}", (ball.getDirection().getY() > 0) ? "DOWN" : "UP");

                if (ball.getDirection().getY() > 0)
                    ball.setColor(Color.BLUE);
                else
                    ball.setColor(Color.YELLOW);
            }
        } else {
            double oldSign = Math.signum(ball.getDirection().getX());
            ball.setDirection(new Vector2D( oldSign * (-1.0), ball.getDirection().getY()));
        }
    }

    public abstract boolean isBarBallCollisionOnSurface(Bar bar, Ball ball, double delta);

    public abstract boolean isBarBallCollisionOnEdges(Bar bar, Ball ball, double delta);
}
