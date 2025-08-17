import enums.Element;
import enums.EnemyType;
import models.actions.EnemyAction;
import models.actions.PlayerAction;
import models.entities.Enemy;
import models.entities.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Setup {
    private final Random random = new Random();

    //Constantes dos inimigos
    private final int ENEMIES_MIN_COUNT = 3;
    private final int ENEMIES_MAX_COUNT = 6 + 1;
    private final int ENEMY_MIN_LIFE = 10;
    private final int ENEMY_MAX_LIFE = 50 + 1;
    private final int ENEMY_MIN_SPEED = 1;
    private final int ENEMY_MAX_SPEED = 15 + 1;

    //Constantes do player
    private final int PLAYER_MIN_HEALTH = 100;
    private final int PLAYER_MAX_HEALTH = 250 + 1;
    private final int PLAYER_MIN_SPEED = 1;
    private final int PLAYER_MAX_SPEED = 20 + 1;

    public Player getPlayer() {
        List<PlayerAction> playerActions = new ArrayList<>(getPlayerActions());
        int playerHealth = random.nextInt(PLAYER_MIN_HEALTH,PLAYER_MAX_HEALTH);
        int playerSpeed = random.nextInt(PLAYER_MIN_SPEED,PLAYER_MAX_SPEED);
        return new Player("Player", playerHealth,playerSpeed, playerActions, Element.NONE);
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
        int enemyElementIndex = random.nextInt(Element.values().length);
        Element enemyElement = Element.values()[enemyElementIndex];
        //Velocidade
        float enemySpeed = random.nextFloat(ENEMY_MIN_SPEED, ENEMY_MAX_SPEED);
        //Ações
        List<EnemyAction> actions = getEnemiesActions().stream()
                .filter(a -> a.getElement().equals(enemyElement))
                .map(e -> new EnemyAction(e.getName(), e.getDamage(), e.getUseCooldown(), e.getElement()))
                .toList();
        List<EnemyAction> availableActions = new ArrayList<>(actions);
        List<EnemyAction> enemyActions = new ArrayList<>();
        int numberOfActions = random.nextInt(1, actions.size() + 1);
        for (int i = 0; i < numberOfActions; i++) {
            int randomAction = random.nextInt(availableActions.size());
            enemyActions.add(availableActions.get(randomAction));
            availableActions.remove(randomAction);
        }
        //Tipo
        int enemyTypeIndex = random.nextInt(0, EnemyType.values().length);
        EnemyType enemyType = EnemyType.values()[enemyTypeIndex];

        return new Enemy(
                enemyLife,
                enemySpeed,
                enemyActions,
                enemyElement,
                enemyType
        );
    }

    public List<EnemyAction> getEnemiesActions(){
        return List.of(
                new EnemyAction("Corte Rápido", 5, 0, Element.NONE),
                new EnemyAction("Corte Pesado", 8, 1, Element.NONE),
                new EnemyAction("Ataque por Trás", 15, 2, Element.NONE),
                new EnemyAction("Garra Afiada", 10, 1, Element.NONE),
                new EnemyAction("Assoprar", 22, 3, Element.AIR),
                new EnemyAction("Empurrar", 5, 0, Element.AIR),
                new EnemyAction("Corrente de vento", 10, 1, Element.AIR),
                new EnemyAction("Vento cortante", 10, 1, Element.AIR),
                new EnemyAction("Cuspe Ácido", 6, 0, Element.EARTH),
                new EnemyAction("Martelada na cara", 18, 2, Element.EARTH),
                new EnemyAction("Mordida Venenosa", 11, 1, Element.EARTH),
                new EnemyAction("Tacar pedra", 10, 3, Element.EARTH),
                new EnemyAction("Labareda", 6, 0, Element.FIRE),
                new EnemyAction("Aquecer o coração", 1, 3, Element.FIRE),
                new EnemyAction("Em chamas", 25, 4, Element.FIRE),
                new EnemyAction("Queimar", 75, 10, Element.FIRE),
                new EnemyAction("Choque", 8, 0, Element.LIGHTNING),
                new EnemyAction("Raiozinho", 1, 2, Element.LIGHTNING),
                new EnemyAction("Arrepio", 15, 2, Element.LIGHTNING),
                new EnemyAction("Zeus", 35, 5, Element.LIGHTNING),
                new EnemyAction("Esfriar o coração", 20, 3, Element.WATER),
                new EnemyAction("Tsunami", 20, 4, Element.WATER),
                new EnemyAction("Punhos de gelo", 5, 0, Element.WATER),
                new EnemyAction("Esguicho", 8, 1, Element.WATER)
        );
    }

    public List<PlayerAction> getPlayerActions(){
        return List.of(
                new PlayerAction("Bola de fogo", 35, 2, Element.FIRE),
                new PlayerAction("Cubos de gelo",28, 1, Element.WATER),
                new PlayerAction("Rajada",20, 0, Element.AIR),
                new PlayerAction("Abalo sísmico",50, 3, Element.EARTH),
                new PlayerAction("Choquei",20, 0, Element.LIGHTNING)
        );
    }
}
