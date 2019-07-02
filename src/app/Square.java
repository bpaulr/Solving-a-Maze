package app;

public class Square {

    private int x;
    private int y;
    private boolean wall;

    public Square(int x, int y, boolean wall) {
        this.x = x;
        this.y = y;
        this.wall = wall;
    }

    public Square(int x, int y) {
        this(x, y, false);
    }

    public Square() {
        this(0, 0, false);
    }

    public boolean isWall() {
        return wall;
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

    public String toString() {
        return "[" + x + "," + y + "," + wall + "]";
    }

}