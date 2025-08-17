import models.entities.Player;
import models.entities.Enemy;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Setup setup = new Setup();
        Player player = setup.getPlayer();
        List<Enemy> enemies = setup.getEnemies();
        Game game = new Game(player, enemies);
        game.startGame();
    }
}
