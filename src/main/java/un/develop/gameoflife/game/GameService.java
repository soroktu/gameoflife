package un.develop.gameoflife.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import un.develop.gameoflife.board.Board;

@Service
public class GameService {

    @Autowired
    GameProcessor gameProcessor;

    public Board calculateBoardOnNextStep(Board board) {
        return gameProcessor.execute(board);
    }
}
