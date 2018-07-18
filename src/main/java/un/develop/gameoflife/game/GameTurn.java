package un.develop.gameoflife.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import un.develop.gameoflife.board.Board;
import un.develop.gameoflife.cell.Cell;

import java.util.ArrayList;
import java.util.List;

public class GameTurn {
    private final static Logger log = LoggerFactory.getLogger(GameProcessor.class);

    private Board board;
    private boolean[][] currentStepCells;
    private boolean[][] nextStepCells;

    public GameTurn(Board board) {
        this.board = board;
        this.currentStepCells = new boolean[board.getEdgeLength()][board.getEdgeLength()];
        this.nextStepCells = new boolean[board.getEdgeLength()][board.getEdgeLength()];
    }

    private void markElementOnArray(int x, int y) {
        try {
            currentStepCells[x][y] = true;
        } catch (IndexOutOfBoundsException exception) {
            log.error("O.", exception.getMessage());
        }
    }

    private boolean getTorCell(int x, int y, boolean[][] cellsArray) {
        int edgeLength = cellsArray.length;
        if (x >= edgeLength) { x = x % edgeLength; }
        if (x < 0) { x = edgeLength + x; }
        if (y >= edgeLength) { y = y % edgeLength; }
        if (y < 0) { y = edgeLength + y; }
        return cellsArray[x][y];
    }

    private int getNeighborsNumber(int x, int y) {

        assert x >= 0 && x < this.board.getEdgeLength() && y >= 0 && y < this.board.getEdgeLength();
        int neighborsNumber = 0;
        for (int i = x-1; i <= x + 1; i++){
            for (int j = y-1; j <= y + 1; j++) {
                if (getTorCell(i, j, this.currentStepCells)) {
                    neighborsNumber++;
                }
            }
        }
        if (getTorCell(x, y, this.currentStepCells)) {
            neighborsNumber--;
        }
        return neighborsNumber;
    }
    private boolean shouldSurvive(Cell cell) {
        int n = getNeighborsNumber(cell.getX(), cell.getY());
        if (2 == n || 3 == n) {
            return true;
        }
        return false;
    }

    private void reviveElement(int x, int y, List<Cell> newElements) {
            if (!getTorCell(x, y, nextStepCells)) {
                if (3 == getNeighborsNumber(x, y)) {
                    Cell newLiveCell = new Cell(x, y, this.board.getEdgeLength());
                    newElements.add(newLiveCell);
                    nextStepCells[newLiveCell.getX()][newLiveCell.getY()] = true;
                }
            }
    }

    private void reviveNeighbors(Cell cell, List<Cell> newElements) {
        int x = cell.getX();
        int y = cell.getY();
        assert x >= 0 && x < this.board.getEdgeLength() && y >= 0 && y < this.board.getEdgeLength();

        for (int i = x-1; i <= x + 1; i++){
            for (int j = y-1; j <= y + 1; j++) {
                reviveElement(i, j, newElements);
            }
        }
    }

    public Board makeBoardOnNextTurn() {
        List<Cell> nextTurnCells = new ArrayList<>();
        board.getLiveCells().forEach(e -> markElementOnArray(e.getX(), e.getY()));

        board.getLiveCells().forEach(e -> {
            if (shouldSurvive(e)) {
                nextTurnCells.add(e);
            }
            reviveNeighbors(e, nextTurnCells);
        });

        Board newBoard = new Board();
        newBoard.setId(1L);
        newBoard.setEdgeLength(this.board.getEdgeLength());
        newBoard.setLiveCells(nextTurnCells);
        return newBoard;

    }

}
