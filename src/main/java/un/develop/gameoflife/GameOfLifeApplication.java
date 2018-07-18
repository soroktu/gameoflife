package un.develop.gameoflife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import un.develop.gameoflife.board.BoardRepository;

@SpringBootApplication
public class GameOfLifeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameOfLifeApplication.class, args);
    }
}
