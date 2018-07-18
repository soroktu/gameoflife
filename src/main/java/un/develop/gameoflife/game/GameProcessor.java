package un.develop.gameoflife.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import un.develop.gameoflife.board.Board;

@Component
public class GameProcessor {
    private final static Logger log = LoggerFactory.getLogger(GameProcessor.class);


    public Board execute(Board board) {
        GameTurn nextTurn = new GameTurn(board);
        Board calculatedBoard = nextTurn.makeBoardOnNextTurn();
        return calculatedBoard;
    }

}
