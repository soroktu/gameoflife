package un.develop.gameoflife.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import un.develop.gameoflife.board.Board;
import un.develop.gameoflife.board.BoardRepository;

@Service
public class GameService {

    private final GameProcessor gameProcessor;

    private final BoardRepository boardRepository;

    @Autowired
    public GameService(GameProcessor gameProcessor, BoardRepository boardRepository) {
        this.gameProcessor = gameProcessor;
        this.boardRepository = boardRepository;
    }

    Board calculateBoardOnNextStep(Board board) {
        Board calculatedBoard = gameProcessor.execute(board);
        boardRepository.save(calculatedBoard);
        return calculatedBoard;
    }

    Board loadBoardFromDB() {
        Board loadedBoard = null;
        if (boardRepository.findById(1L).isPresent()) {
            loadedBoard = boardRepository.findById(1L).get();
        }
        return loadedBoard;
    }
}
