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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (wall ? 1231 : 1237);
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Square other = (Square) obj;
        if (wall != other.wall)
            return false;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

}