package org.game.gameobject;

import org.game.math.Vector2D;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

public abstract class GameObject {
    protected double width;
    protected double height;
    protected Color color;
    protected Vector2D vel;
    protected Vector2D position;
    protected Vector2D direction;
    protected Vector2D forwardVector;

    protected GameObject(Vector2D pos, double width, double height, Vector2D vel, Vector2D forwardVector, Color color) {
        this.position = pos;
        this.width = width;
        this.height = height;
        this.color = color;
        this.vel = vel;
        this.direction = new Vector2D(0, 0);
        this.forwardVector = forwardVector;
    }

    public abstract void update(double delta);

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fill(new Rectangle2D.Double(this.position.getX(), this.position.getY(), this.width, this.height));
    }

    public Vector2D getVelocity() { return this.vel; }

    public Vector2D getPosition() { return this.position; }

    public Vector2D getDirection() { return this.direction; }

    public Vector2D getForwardVector() { return this.forwardVector; }

    public void setDirection(Vector2D v) { this.direction = v; }

    public void setColor(Color color) { this.color = color; }

    public void setVelocity(Vector2D v) { this.vel = v; }

    public void setPosition(Vector2D v) { this.position = v; }
}
