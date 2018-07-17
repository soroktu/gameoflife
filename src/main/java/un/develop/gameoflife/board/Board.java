package un.develop.gameoflife.board;

import un.develop.gameoflife.cell.Cell;

import javax.persistence.Entity;
import java.util.List;


public class Board {

    private int edgeLength;
    private List<Cell> liveCells;
    private boolean[][] cellsArray;

    public Board(int edgeLength) {
        this.edgeLength = edgeLength;
    }

    public int getEdgeLength() {
        return edgeLength;
    }

    public void setEdgeLength(int edgeLength) {
        this.edgeLength = edgeLength;
    }

    public List<Cell> getLiveCells() {
        return liveCells;
    }

    public void setLiveCells(List<Cell> liveCells) {
        this.liveCells = liveCells;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Board))
            return false;
        if (obj == this)
            return true;
        Board structure = (Board) obj;

        return this.edgeLength == structure.getEdgeLength() && this.liveCells.containsAll(structure.getLiveCells());
    }

}
