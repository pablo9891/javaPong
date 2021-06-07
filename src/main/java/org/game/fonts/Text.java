package org.game.fonts;

import java.awt.*;

public class Text {
    private String text;
    private Color color;
    private int x, y;
    private int width, height;
    private int size;

    public Text(String text, int x, int y, int size, Color color) {
        this.text = text;
        this.color = color;
        this.x = x;
        this.y = y;
        this.size = size;
        this.width = size * this.text.length();
        this.height = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
