package org.example.javalabup.Objects;

public class Point {
    private int x = 0, y = 0;
    private int r = 0;
    public Point(int x, int y, int r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
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

    public int getR() {
        return r;
    }
}
