package models;

import models.entities.Enemy;
import models.entities.Player;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final GameView gameView;
    private final GameController gameController;

    public Game(Player player, List<Enemy> enemies) {
        this.gameView = new GameView(new Scanner(System.in));
        this.gameController = new GameController(this.gameView, player, enemies);
    }

    public void startGame(){
        gameView.gameStarted();
        gameController.startTurns();
        gameView.gameFinished();
    }
}
