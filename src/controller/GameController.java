package controller;

import enums.GameState;
import models.actions.Action;
import view.GameView;
import models.actions.EnemyAction;
import models.actions.PlayerAction;
import models.entities.Enemy;
import models.entities.Entity;
import models.entities.Player;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GameController {
    public static GameState gameState;
    private final GameView gameView;

    private final Player player;
    private final List<Enemy> enemies;

    public GameController(GameView gameView, Player player, List<Enemy> enemies) {
        this.gameView = gameView;
        this.player = player;
        this.enemies = enemies;
    }

    public void startGame(){
        gameView.showElementsInteractions();
        player.setName(gameView.setPlayerName());
        int turn = 1;
        while(gameState != GameState.ENDED && !player.isDead()){
            gameView.showActualTurn(turn);

            resetCooldowns();

            gameView.showEntities(player, enemies);

            List<Entity<?>> playOrder = Stream.concat(Stream.of(player), enemies.stream())
                    .sorted(Comparator.comparingDouble(Entity::getSpeed))
                    .toList().reversed();

            gameView.showPlayOrder(playOrder);

            gameView.actionsStarted();

            for (var entity : playOrder){
                if(entity.isDead()) continue;
                if(entity instanceof Player){
                    playerTurn();
                    if(gameState == GameState.ENDED) break;
                } else if (entity instanceof Enemy enemy) {
                    enemyTurn(enemy);
                }
                System.out.println("\n");
                if(player.isDead()) break;
            }

            gameView.actionsFinished();
            gameView.showTurnResume(turn);

            if(enemies.isEmpty()){
                gameView.showVictoryMessage();
                gameState = GameState.ENDED;
                break;
            }

            if(player.isDead()) break;

            gameView.endTurn();

            turn++;
        }
    }

    public void resetCooldowns(){
        Stream.concat(
                player.getActions().stream(),
                enemies.stream().flatMap(e -> e.getActions().stream())
        ).forEach(Action::decreaseCooldown);
    }

    public void playerTurn(){
        playerChoiceAction().ifPresentOrElse(selectedAction -> {
            Enemy selectedEnemy = playerChoiceEnemy();
            player.doAction(selectedAction, selectedEnemy);
            gameView.spacing(1);
            gameView.showAttackDescription(player, selectedAction, selectedEnemy);
            if(selectedEnemy.isDead()) {
                enemies.remove(selectedEnemy);
                gameView.showEnemyDeath(selectedEnemy, selectedAction);
            }
        }, gameView::showSurrenderMessage);
    }

    public Optional<PlayerAction> playerChoiceAction(){
        int selectedAction;
        int optionIndex = 1;

        Map<Integer, PlayerAction> playerActionsOptions = new HashMap<>();

        for (var actionAvailable : player.getAvailableActions()){
            playerActionsOptions.put(optionIndex, actionAvailable);
            optionIndex++;
        }

        int exitOption = playerActionsOptions.size() + 1;

        String options = gameView.mapPlayerOptions(playerActionsOptions,player);

        boolean invalidOption;
        do{
            invalidOption = false;
            selectedAction = gameView.selectAction(options);

            if(selectedAction == exitOption) {
                gameState = GameState.ENDED;
                return Optional.empty();
            }

            if(selectedAction < 0 || selectedAction > playerActionsOptions.size() + 1){
                gameView.showInvalidOptionMessage();
                invalidOption = true;
            }

        }while(invalidOption);

        return Optional.of(playerActionsOptions.get(selectedAction));
    }

    public Enemy playerChoiceEnemy() {
        int selectedEnemy;
        gameView.showEnemiesOptionsHeader();
        Map<Integer, Enemy> enemiesOptions = IntStream.range(0, enemies.size())
                .boxed()
                .collect(Collectors.toMap(i -> i + 1, enemies::get));

        boolean validTargets;
        do {
            validTargets = false;
            selectedEnemy = gameView.selectEnemy(enemiesOptions);

            if (selectedEnemy < 0 || selectedEnemy > enemiesOptions.size()) {
                gameView.showInvalidOptionMessage();
                continue;
            }

            validTargets = true;

        } while (!validTargets);

        return enemiesOptions.get(selectedEnemy);
    }

    public void enemyTurn(Enemy enemy){
        Random random = new Random();
        List<EnemyAction> actions = enemy.getAvailableActions();
        if(actions.isEmpty()){
            gameView.enemySkipTurn(enemy);
            return;
        }

        EnemyAction action = actions.get(random.nextInt(0, actions.size()));
        enemy.doAction(action, player);
        gameView.showAttackDescription(enemy, action, player);
        if(player.isDead()) gameView.showPlayerDeath(player, enemy, action);
    }
}
