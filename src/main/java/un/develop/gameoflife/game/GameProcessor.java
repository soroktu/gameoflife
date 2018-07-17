package un.develop.gameoflife.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import un.develop.gameoflife.board.Board;
import un.develop.gameoflife.cell.Cell;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameProcessor {
    private final static Logger log = LoggerFactory.getLogger(GameProcessor.class);


    public Board execute(Board board) {
        Board calculatedBoard = updateElementsAndNeighbors(board);
        return calculatedBoard;
    }

    private Board updateElementsAndNeighbors(Board board) {
        List<Cell> newLiveCells = new ArrayList<>();

        int edgeLength = board.getEdgeLength();
        boolean[][] boardArray = new boolean[edgeLength][edgeLength];
        boolean[][] revivedElements = new boolean[edgeLength][edgeLength];

        board.getLiveCells().forEach(e -> markElementOnArray(e.getX(), e.getY(), boardArray));

        board.getLiveCells().forEach(e -> {
            if (shouldSurvive(e, edgeLength, boardArray)) {
                newLiveCells.add(e);
            }
            reviveNeighbors(e, boardArray, revivedElements, newLiveCells);
        });

        Board newBoard = new Board();
        newBoard.setEdgeLength(edgeLength);
        newBoard.setLiveCells(newLiveCells);
        return newBoard;
    }

    private void reviveNeighbors(Cell cell, boolean[][] cellsArray, boolean[][] revivedElements,
                                 List<Cell> newElements) {
        int x = cell.getX();
        int y = cell.getY();
        int edgeLength = cellsArray.length;
        assert x >= 0 && x < edgeLength && y >= 0 && y < edgeLength;

        for (int i = x-1; i <= x + 1; i++){
            for (int j = y-1; j <= y + 1; j++) {
                reviveElement(i, j, cellsArray, revivedElements, newElements);
            }
        }
    }

    private void reviveElement(int x, int y, boolean[][] cellsArray, boolean[][] revivedElements,
                               List<Cell> newElements) {
        int edgeLength = cellsArray.length;
        if (x >= edgeLength) { x = x % edgeLength; }
        if (x < 0) { x = edgeLength + x; }

        if (y >= edgeLength) { y = y % edgeLength; }
        if (y < 0) { y = edgeLength + y; }
        if (x >= 0 && x < edgeLength && y >= 0 && y < edgeLength) {
            if (!revivedElements[x][y]) {
                if (3 == getNeighborsNumber(x, y, cellsArray)) {
                    revivedElements[x][y] = true;
                    newElements.add(new Cell(x, y));
                }
            }
        }
    }

    private void markElementOnArray(int x, int y, boolean[][] cellsArray) {
        try {
            cellsArray[x][y] = true;
        } catch (IndexOutOfBoundsException exception) {
            log.error("Improper cell on list.", exception.getMessage());
        }
    }

    private boolean shouldSurvive(Cell element, int edgeLength, boolean[][] cellsArray) {
        int n = getNeighborsNumber(element.getX(), element.getY(), cellsArray);
        if (2 == n || 3 == n) {
            return true;
        }
        return false;
    }
    private boolean isLive(int x, int y, boolean[][] cellsArray) {
        int edgeLength = cellsArray.length;
        if (x >= edgeLength) { x = x % edgeLength; }
        if (x < 0) { x = edgeLength + x; }

        if (y >= edgeLength) { y = y % edgeLength; }
        if (y < 0) { y = edgeLength + y; }
        return cellsArray[x][y];
    }
    private int getNeighborsNumber(int x, int y, boolean[][] cellsArray) {

        int edgeLength = cellsArray.length;
        assert x >= 0 && x < edgeLength && y >= 0 && y < edgeLength;

        int neighborsNumber = 0;
        for (int i = x-1; i <= x + 1; i++){
            for (int j = y-1; j <= y + 1; j++) {
                if (isLive(i, j, cellsArray)) {
                    neighborsNumber++;
                }
            }
        }
        if (isLive(x, y, cellsArray)) {
            neighborsNumber--;
        }
        return neighborsNumber;
    }

}
