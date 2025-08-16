import enums.Element;
import models.Game;
import models.GameController;
import models.GameView;
import models.entities.Player;
import models.actions.EnemyAction;
import models.actions.PlayerAction;
import models.entities.Enemy;
import enums.EnemyType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final int ENEMIES_MIN_COUNT = 1;
    private static final int ENEMIES_MAX_COUNT = 20;
    private static final int ENEMY_MIN_LIFE = 10;
    private static final int ENEMY_MAX_LIFE = 50;
    private static final int ENEMY_MIN_SPEED = 1;
    private static final int ENEMY_MAX_SPEED = 10;
    private static final int ENEMY_MAX_ACTIONS = 5;
    private static final Random random = new Random();

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
        //Vida
        int enemyLife = random.nextInt(ENEMY_MIN_LIFE,ENEMY_MAX_LIFE);
        //Elemento
        int enemyElement = random.nextInt(0,Element.values().length);
        //Velocidade
        float enemySpeed = random.nextFloat(ENEMY_MIN_SPEED, ENEMY_MAX_SPEED);
        //Ações
        int actionCount = random.nextInt(2,getEnemiesActions().size() - (getEnemiesActions().size() - ENEMY_MAX_ACTIONS));
        Set<EnemyAction> enemyAction = new HashSet<>();
        for (int i = 0; i < actionCount; i++) {
            int randomAction = random.nextInt(0, getEnemiesActions().size());
            enemyAction.add(getEnemiesActions().get(randomAction));
        }
        //Tipo
        int enemyType = random.nextInt(0, EnemyType.values().length);
        return new Enemy(
            enemyLife,
            enemySpeed,
            enemyAction.stream().toList(),
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
