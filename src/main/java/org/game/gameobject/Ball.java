package org.game.gameobject;

import org.game.math.Vector2D;
import org.game.utils.Constants;

import java.awt.*;

public class Ball extends GameObject {

    public Ball(Vector2D pos, double width, double height, Vector2D vel, Vector2D forwardVector, Color color) {
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
    }

    public void draw(Graphics2D g) {
        this.drawGameObject(g);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Ball height: " + this.height + "\n");
        s.append("Ball width: " + this.width + "\n");
        s.append("Ball position: " + this.position.toString() +"\n");
        s.append("Ball velocity: " + this.vel.toString() +"\n");
        s.append("Ball direction: " + this.direction.toString() +"\n");
        String dirX = (this.direction.getX() < 0 ) ? "LEFT" : "RIGHT";
        String dirY = (this.direction.getY() > 0 ) ? "DOWN" : "UP";
        s.append("Ball goes: " + dirY + " and " + dirX + "\n");
        s.append("Ball forwardVector: " + this.forwardVector.toString());

        return s.toString();
    }
}
