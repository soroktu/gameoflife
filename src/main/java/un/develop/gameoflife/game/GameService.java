package un.develop.gameoflife.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import un.develop.gameoflife.board.Board;
import un.develop.gameoflife.board.BoardRepository;

@Service
public class GameService {

    @Autowired
    GameProcessor gameProcessor;

    @Autowired
    BoardRepository boardRepository;

    public Board calculateBoardOnNextStep(Board board) {
        Board calculatedBoard = gameProcessor.execute(board);
        boardRepository.save(calculatedBoard);
        return calculatedBoard;
    }

    public Board loadBoardFromDB() {
        return boardRepository.getOne(1L);
    }
}
