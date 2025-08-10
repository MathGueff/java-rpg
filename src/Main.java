import models.Element;
import models.Game;
import models.Player;
import models.actions.EnemyAction;
import models.actions.PlayerActions;
import models.actions.SpellAction;
import models.enemies.Enemy;
import models.enemies.Rogue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Player player = getPlayer();
        List<Enemy> enemies = getEnemies();
        Game game = new Game(player, enemies);
        game.startGame();
    }

    private static Player getPlayer() {
        List<PlayerActions> playerActions = new ArrayList<>(getPlayerActions());
        return new Player("Matheus", 200, 10, playerActions);
    }

    private static List<Enemy> getEnemies() {
        Random random = new Random();

        var enemyCount = random.nextInt(1, 10);
        List<Enemy> generatedEnemies = new ArrayList<>();
        List<EnemyAction> enemyActions = new ArrayList<>(getEnemiesActions());

        for (int i = 0; i < enemyCount; i++) {
            int enemyLife = random.nextInt(5,30);
            int enemyElement = random.nextInt(0,Element.values().length);
            int actionIndex = random.nextInt(0, enemyActions.size());
            Enemy enemy = new Rogue(enemyLife, List.of(enemyActions.get(actionIndex)), Element.getWithCode(enemyElement));
            generatedEnemies.add(enemy);
        }
        return generatedEnemies;
    }

    private static List<EnemyAction> getEnemiesActions(){
        return List.of(
                new EnemyAction("Corte rápido",8),
                new EnemyAction("Corte pesado", 12),
                new EnemyAction("Ataque por trás", 19),
                new EnemyAction("Sanguessuga", 2)
        );
    }

    private static List<PlayerActions> getPlayerActions(){
        return List.of(
                new SpellAction("Bola de fogo", 1,25),
                new SpellAction("Espinhos de gelo", 2,20),
                new SpellAction("Tornado", 5,4)
        );
    }
}
