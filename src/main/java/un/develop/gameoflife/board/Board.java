package un.develop.gameoflife.board;

import un.develop.gameoflife.cell.Cell;

import javax.persistence.*;
import java.util.List;

@Entity
public class Board {

    @Id
    private long id;

    private int edgeLength;

    @Lob
    @Convert(converter = CellListConverter.class)
    private List<Cell> liveCells;

    public Board() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
