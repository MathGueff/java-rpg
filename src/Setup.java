import enums.Element;
import enums.EnemyType;
import models.actions.EnemyAction;
import models.actions.PlayerAction;
import models.entities.Enemy;
import models.entities.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Setup {
    private final Random random = new Random();

    //Constantes dos inimigos
    private final int ENEMIES_MIN_COUNT = 1;
    private final int ENEMIES_MAX_COUNT = 5 + 1;
    private final int ENEMY_MIN_LIFE = 10;
    private final int ENEMY_MAX_LIFE = 50 + 1;
    private final int ENEMY_MIN_SPEED = 1;
    private final int ENEMY_MAX_SPEED = 15 + 1;
    private final int ENEMY_MAX_ACTIONS = 4 + 1;

    //Constantes do player
    private final int PLAYER_MIN_HEALTH = 100;
    private final int PLAYER_MAX_HEALTH = 250 + 1;
    private final int PLAYER_MIN_SPEED = 1;
    private final int PLAYER_MAX_SPEED = 20 + 1;

    public Player getPlayer() {
        List<PlayerAction> playerActions = new ArrayList<>(getPlayerActions());
        int playerHealth = random.nextInt(PLAYER_MIN_HEALTH,PLAYER_MAX_HEALTH);
        int playerSpeed = random.nextInt(PLAYER_MIN_SPEED,PLAYER_MAX_SPEED);
        return new Player("Player", playerHealth,playerSpeed, playerActions);
    }

    public List<Enemy> getEnemies() {
        var enemyCount = random.nextInt(ENEMIES_MIN_COUNT, ENEMIES_MAX_COUNT);
        List<Enemy> generatedEnemies = new ArrayList<>();
        for (int i = 0; i < enemyCount; i++) {
            generatedEnemies.add(getEnemy());
        }
        return generatedEnemies;
    }

    public Enemy getEnemy(){
        //Vida
        int enemyLife = random.nextInt(ENEMY_MIN_LIFE,ENEMY_MAX_LIFE);
        //Elemento
        int enemyElement = random.nextInt(0, Element.values().length);
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

    public List<EnemyAction> getEnemiesActions(){
        return List.of(
                new EnemyAction("Corte Rápido", 5, 0),
                new EnemyAction("Corte Pesado", 8, 0),
                new EnemyAction("Ataque por Trás", 10, 1),
                new EnemyAction("Garra Afiada", 10, 0),
                new EnemyAction("Cuspe Ácido", 6, 0),
                new EnemyAction("Martelada na cara", 18, 2),
                new EnemyAction("Choque", 4, 0),
                new EnemyAction("Mordida Venenosa", 11, 1),
                new EnemyAction("Labareda", 6, 0),
                new EnemyAction("Tacar pedra", 10, 3),
                new EnemyAction("Assoprar", 22, 1),
                new EnemyAction("Aquecer o coração", 1, 3),
                new EnemyAction("Esfriar o coração", 20, 3)
        );
    }

    public List<PlayerAction> getPlayerActions(){
        return List.of(
                new PlayerAction("Bola de fogo", 25, 2),
                new PlayerAction("Espinhos de gelo",20, 1),
                new PlayerAction("Tornado",40, 0)
        );
    }
}
