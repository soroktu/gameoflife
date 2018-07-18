package un.develop.gameoflife.cell;

public class Cell {

    private int x;
    private int y;

    public Cell() {
        super();
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell(int x, int y, int torEdgeLength) {
        if (x >= torEdgeLength) { x = x % torEdgeLength; }
        if (x < 0) { x = torEdgeLength + x; }

        if (y >= torEdgeLength) { y = y % torEdgeLength; }
        if (y < 0) { y = torEdgeLength + y; }
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cell))
            return false;
        if (obj == this)
            return true;
        Cell element = (Cell) obj;

        return this.x == element.getX() && this.y == element.getY();
    }
}
