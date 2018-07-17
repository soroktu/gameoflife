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

    private void reviveNeighbors(Cell cell, boolean[][] structureArray, boolean[][] revivedElements,
                                 List<Cell> newElements) {
        int x = cell.getX();
        int y = cell.getY();
        int edgeLength = structureArray.length;
        assert x >= 0 && x < edgeLength && y >= 0 && y < edgeLength;

        reviveElement(x - 1, y - 1, structureArray, revivedElements, newElements);
        reviveElement(x - 1, y, structureArray, revivedElements, newElements);
        reviveElement(x - 1, y + 1, structureArray, revivedElements, newElements);
        reviveElement(x, y - 1, structureArray, revivedElements, newElements);
        reviveElement(x, y + 1, structureArray, revivedElements, newElements);
        reviveElement(x + 1, y - 1, structureArray, revivedElements, newElements);
        reviveElement(x + 1, y, structureArray, revivedElements, newElements);
        reviveElement(x + 1, y + 1, structureArray, revivedElements, newElements);
    }

    private void reviveElement(int x, int y, boolean[][] structureArray, boolean[][] revivedElements,
                               List<Cell> newElements) {
        int edgeLength = structureArray.length;
        if (x >= 0 && x < edgeLength && y >= 0 && y < edgeLength) {
            if (!revivedElements[x][y]) {
                if (3 == getNeighborsNumber(x, y, structureArray)) {
                    revivedElements[x][y] = true;
                    newElements.add(new Cell(x, y));
                }
            }
        }
    }

    private void markElementOnArray(int x, int y, boolean[][] structureArray) {
        try {
            structureArray[x][y] = true;
        } catch (IndexOutOfBoundsException exception) {
            log.error("Improper cell on list.", exception.getMessage());
        }
    }

    private boolean shouldSurvive(Cell element, int edgeLength, boolean[][] structureArray) {
        int n = getNeighborsNumber(element.getX(), element.getY(), structureArray);
        if (2 == n || 3 == n) {
            return true;
        }
        return false;
    }

    private int getNeighborsNumber(int x, int y, boolean[][] structureArray) {

        int edgeLength = structureArray.length;
        assert x >= 0 && x < edgeLength && y >= 0 && y < edgeLength;

        int neighborsNumber = 0;

        if (x > 0) {
            if (structureArray[x - 1][y]) {
                neighborsNumber++;
            }
            if (y > 0 && structureArray[x - 1][y - 1]) {
                neighborsNumber++;
            }
            if (y < edgeLength - 1 && structureArray[x - 1][y + 1]) {
                neighborsNumber++;
            }
        }
        if (y > 0) {
            if (structureArray[x][y - 1]) {
                neighborsNumber++;
            }
        }
        if (y < edgeLength - 1) {
            if (structureArray[x][y + 1]) {
                neighborsNumber++;
            }
        }
        if (x < edgeLength - 1) {
            if (structureArray[x + 1][y]) {
                neighborsNumber++;
            }
            if (y > 0 && structureArray[x + 1][y - 1]) {
                neighborsNumber++;
            }
            if (y < edgeLength - 1 && structureArray[x + 1][y + 1]) {
                neighborsNumber++;
            }
        }
        return neighborsNumber;
    }

}
