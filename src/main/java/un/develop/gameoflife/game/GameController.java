package un.develop.gameoflife.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import un.develop.gameoflife.board.Board;

@RestController
@RequestMapping(value = "/rest/gameoflife")
public class GameController {
    private final static Logger log = LoggerFactory.getLogger(GameController.class);

    @Autowired
    GameService gameService;

    @ResponseBody
    @RequestMapping(value = "/calculate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Board calculate(@RequestBody Board board) {
        return gameService.calculateBoardOnNextStep(board);
    }

    @ResponseBody
    @RequestMapping(value = "/load", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Board load() {
        return gameService.loadBoardFromDB();
    }

}
