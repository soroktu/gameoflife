package un.develop.gameoflife.game;

import org.junit.Test;
import un.develop.gameoflife.board.Board;
import un.develop.gameoflife.cell.Cell;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameProcessorTest {

    @Test
    public void checkTreeDotsOnBoard() {
        List<Cell> startCells = new ArrayList<>();
        startCells.add(new Cell(8, 9));
        startCells.add(new Cell(9, 9));
        startCells.add(new Cell(10, 9));
        Board startBoard = generateBoard(20, startCells);

        List<Cell> expectedElements = new ArrayList<>();
        expectedElements.add(new Cell(9, 8));
        expectedElements.add(new Cell(9, 9));
        expectedElements.add(new Cell(9, 10));
        Board expectedBoard = generateBoard(20, expectedElements);

        GameProcessor lifeGameProcessor = new GameProcessor();

        // first iteration
        Board actualStructure = lifeGameProcessor.execute(startBoard);
        assertEquals(expectedBoard, actualStructure);

        // second iteration
        Board actual2Structure = lifeGameProcessor.execute(actualStructure);
        assertEquals(startBoard, actual2Structure);
    }

    private Board generateBoard(int edgeLength, List<Cell> cells) {
        Board board = new Board();
        board.setEdgeLength(edgeLength);
        board.setLiveCells(cells);
        return board;
    }

}