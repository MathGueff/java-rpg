package models;

import enums.GameState;
import models.actions.Action;
import models.actions.EnemyAction;
import models.actions.PlayerAction;
import models.entities.Enemy;
import models.entities.Entity;
import models.entities.Player;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Game {
    private final Scanner sc;
    private final Player player;
    private final List<Enemy> enemies;
    public static GameState gameState;

    public Game(Player player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;
        this.sc = new Scanner(System.in);
    }

    public void startGame(){
        gameState = GameState.STARTED;
        System.out.println("====---O JOGO FOI INICIADO---====");
        int turn = 1;
        while(gameState != GameState.ENDED && player.getCurrentHealth() > 0){
            System.out.printf("%n-====Turno %s ====-%n%n",turn);

            System.out.println(player.getInfo());
            System.out.println("----Inimigos----");
            enemies.forEach(e -> System.out.println(e.getStatus()));


            List<Entity<?>> playOrder = Stream.<Entity<?>>concat(Stream.of(player), enemies.stream())
                    .sorted(Comparator.comparingDouble(Entity::getSpeed))
                    .toList().reversed();

            System.out.println("\nORDEM DE JOGADA:\n");

            playOrder.stream().map(p -> String.format("%s - %s", p.getName(), p.getSpeed()))
                    .forEach(System.out::println);

            System.out.println("\n[--AÇÕES--]\n");

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

            System.out.println("\n[--FIM DAS AÇÕES--]");

            if(enemies.isEmpty()){
                System.out.println("Todos inimigos foram derrotados! Você venceu!");
                gameState = GameState.ENDED;
                break;
            }
            turn++;
        }
        System.out.println("\n====---O JOGO FOI FINALIZADO---====\n");
    }

    public void playerTurn(){
        playerChoiceAction().ifPresentOrElse(selectedAction -> {
            Enemy selectedEnemy = playerChoiceEnemy();
            player.doAction(selectedAction, selectedEnemy);
            System.out.printf("%n");
            showAttackLog(player, selectedAction, selectedEnemy);
            if(selectedEnemy.isDead()) {
                enemies.remove(selectedEnemy);
                System.out.printf("%n%s foi morto por %s", selectedEnemy.getName(), selectedAction.getName());
            }
        }, () -> {
            System.out.println("Você desistiu");
        });
    }

    public Optional<PlayerAction> playerChoiceAction(){
        int selectedAction = 0;

        Map<Integer, PlayerAction> playerActionsOptions = IntStream.range(0, player.getActions().size())
                .boxed()
                .collect(Collectors.toMap(i -> i + 1, player::getAction));

        int exitOption = playerActionsOptions.size() + 1;
        StringBuilder menuOptions = new StringBuilder();

        playerActionsOptions.forEach((k,v) -> menuOptions.append(String.format("%s - %s\n", k, v)));
        menuOptions.append(String.format("%s - Desistir", exitOption));

        boolean invalidOption;
        do{
            invalidOption = false;
            System.out.println("----Opções----");
            System.out.println(menuOptions);
            System.out.print("Escolha: ");
            selectedAction = sc.nextInt();

            if(selectedAction == exitOption) {
                gameState = GameState.ENDED;
                return Optional.empty();
            }

            if(selectedAction < 0 || selectedAction > playerActionsOptions.size() + 1){
                System.out.println("Escolha uma opção válida!");
                invalidOption = true;
            }

        }while(invalidOption);

        return Optional.of(playerActionsOptions.get(selectedAction));
    }

    public Enemy playerChoiceEnemy() {
        int selectedEnemy = 0;
        System.out.println("\n----Alvos----");
        Map<Integer, Enemy> enemiesOptions = IntStream.range(0, enemies.size())
                .boxed()
                .collect(Collectors.toMap(i -> i + 1, enemies::get));

        boolean validTargets;
        do {
            validTargets = false;
            enemiesOptions.forEach((k, v) -> System.out.printf("%s - %s%n", k, v.getStatus()));
            System.out.print("Escolha: ");
            selectedEnemy = sc.nextInt();

            if (selectedEnemy < 0 || selectedEnemy > enemiesOptions.size()) {
                System.out.println("Escolha um inimigo válido");
                continue;
            }

            validTargets = true;

        } while (!validTargets);

        return enemiesOptions.get(selectedEnemy);
    }

    public void enemyTurn(Enemy enemy){
        Random random = new Random();
        EnemyAction action = enemy.getActions().get(random.nextInt(0, enemy.getActions().size()));
        enemy.doAction(action, player);
        showAttackLog(enemy, action, player);
        if(player.isDead()) System.out.printf("%nO jogador %s foi morto por %s de %s",player.getName(), action.getName(), enemy.getName());
    }

    public void showAttackLog(Entity actor, Action action, Entity target){
        System.out.printf("%s: %s usou %s em %s, causando %.0f de dano -> %s (-%.0f)",
                actor instanceof Player ? "Você" : "Inimigo",
                actor, action.getName(), target, action.getRealDamage(actor),
                    target.getStatus(), action.getRealDamage(actor));
    }
}
