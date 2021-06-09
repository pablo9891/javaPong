package org.game.gameobject;

import org.game.math.Vector2D;
import org.game.utils.Constants;

import java.awt.Color;

public class Bar extends GameObject {

    public Bar(Vector2D pos, double width, double height, Vector2D vel, Vector2D forwardVector, Color color) {
        super(pos, width, height, vel, forwardVector, color);
    }

    public void update(double delta) {
        double newXPosition = this.position.getX();
        double newYPosition = this.position.getY();

        if(direction.getX() != 0) {
            newXPosition = this.position.getX() + (vel.getX() * this.direction.getX()) * delta;
        }

        if(direction.getY() != 0) {
            newYPosition = this.position.getY() + (vel.getY() * this.direction.getY()) * delta;
        }

        this.position = new Vector2D(newXPosition, newYPosition);
        this.direction = new Vector2D(0, 0);
    }

    public void down(double delta) {
        double newXPosition = this.position.getX();
        double newYPosition = this.position.getY() + (vel.getY() * delta);

        if(newYPosition + Constants.BAR_HEIGHT < Constants.BOTTOM_BAR)
            this.position = new Vector2D(newXPosition, newYPosition);
    }

    public void up(double delta) {
        double newXPosition = this.position.getX();
        double newYPosition = this.position.getY() - (vel.getY() * delta);

        if(newYPosition > (Constants.TOP_BAR + Constants.TOP_BAR_MARGIN))
            this.position = new Vector2D(newXPosition, newYPosition);
    }
}
