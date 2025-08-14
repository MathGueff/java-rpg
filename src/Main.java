import enums.Element;
import models.Game;
import models.Player;
import models.actions.EnemyAction;
import models.actions.PlayerAction;
import models.enemies.Enemy;
import enums.EnemyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int ENEMIES_MAX_COUNT = 10;
    private static final int ENEMIES_MIN_COUNT = 3;
    private static final int ENEMY_MAX_LIFE = 50;
    private static final int ENEMY_MIN_LIFE = 10;

    public static void main(String[] args) {
        Player player = getPlayer();
        List<Enemy> enemies = getEnemies();
        Game game = new Game(player, enemies);
        game.startGame();
    }

    private static Player getPlayer() {
        List<PlayerAction> playerActions = new ArrayList<>(getPlayerActions());
        return new Player("Matheus", 1000,10, playerActions);
    }

    private static List<Enemy> getEnemies() {Random random = new Random();
        var enemyCount = random.nextInt(ENEMIES_MIN_COUNT, ENEMIES_MAX_COUNT);
        List<Enemy> generatedEnemies = new ArrayList<>();
        for (int i = 0; i < enemyCount; i++) {
            generatedEnemies.add(getEnemy());
        }
        return generatedEnemies;
    }

    private static Enemy getEnemy(){
        var random = new Random();
        List<EnemyAction> allEnemiesActions = new ArrayList<>(getEnemiesActions());
        //Vida
        int enemyLife = random.nextInt(ENEMY_MIN_LIFE,ENEMY_MAX_LIFE);
        //Elemento
        int enemyElement = random.nextInt(0,Element.values().length);
        //Velocidade
        float enemySpeed = random.nextFloat(0, 10);
        //Ações
        int actionIndex = random.nextInt(0, allEnemiesActions.size());
        //Tipo
        int enemyType = random.nextInt(0, EnemyType.values().length);
        return new Enemy(
            enemyLife,
            enemySpeed,
            List.of(allEnemiesActions.get(actionIndex)),
            EnemyType.values()[enemyType],
            Element.values()[enemyElement]
        );
    }

    private static List<EnemyAction> getEnemiesActions(){
        return List.of(
                new EnemyAction("Corte Rápido", 8),
                new EnemyAction("Corte Pesado", 12),
                new EnemyAction("Ataque por Trás", 19),
                new EnemyAction("Garra Afiada", 10),
                new EnemyAction("Investida Brutal", 15),
                new EnemyAction("Cuspe Ácido", 14),
                new EnemyAction("Chamas Negras", 25),
                new EnemyAction("Explosão de Sombras", 20),
                new EnemyAction("Mordida Venenosa", 11),
                new EnemyAction("Pancada Sísmica", 18)
        );
    }

    private static List<PlayerAction> getPlayerActions(){
        return List.of(
                new PlayerAction("Bola de fogo", 25),
                new PlayerAction("Espinhos de gelo",20),
                new PlayerAction("Tornado",40)
        );
    }
}
