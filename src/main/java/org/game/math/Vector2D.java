package org.game.math;

public class Vector2D {
    private double x;
    private double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void addVector(Vector2D v) {
        this.x += v.getX();
        this.y += v.getY();
    }

    public void substractVector(Vector2D v) {
        this.x = this.x + (-1) * v.getX();
        this.y = this.y + (-1) * v.getY();
    }

    public void multiplyByScalar(double n) {
        this.x *= n;
        this.y *= n;
    }

    public void setX(double x) { this.x = x; }

    public void setY(double y) { this.y = y; }

    public double getX() { return this.x; }

    public double getY() { return this.y; }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Vector2D) {
            Vector2D v = (Vector2D) o;
            if(this.x == v.getX() && this.y == v.getY())
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[ x: " + getX() + " y: " + getY() + " ]";
    }
}
