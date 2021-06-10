package org.game.fonts;

import java.awt.Color;

public class Text {
    private String txt;
    private Color color;
    private int x;
    private int y;
    private int width;
    private int height;
    private int size;

    public Text(String txt, int x, int y, int size, Color color) {
        this.txt = txt;
        this.color = color;
        this.x = x;
        this.y = y;
        this.size = size;
        this.width = size * this.txt.length();
        this.height = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getText() {
        return txt;
    }

    public void setText(String text) {
        this.txt = text;
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
