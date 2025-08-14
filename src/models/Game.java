package models;

import enums.GameState;
import models.actions.Action;
import models.actions.EnemyAction;
import models.actions.PlayerAction;
import models.enemies.Enemy;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        System.out.println("O jogo foi iniciado");
        int turn = 1;
        while(gameState != GameState.ENDED && player.getCurrentHealth() > 0){
            System.out.printf("\n-====Turno %s ====-\n\n",turn);

            playerTurn();
            if(gameState == GameState.ENDED) break;

            System.out.print("\n");

            enemiesTurn();

            if(enemies.isEmpty()){
                System.out.println("Todos inimigos foram derrotados! Você venceu!");
                gameState = GameState.ENDED;
                break;
            }
            turn++;
        }
        System.out.println("O jogo foi finalizado");
    }

    public void playerTurn(){
        System.out.println(player.getStatus());

        PlayerAction actionChoice = playerChoiceAction();
        if(actionChoice == null) return;

        Enemy enemyChoice = playerChoiceEnemy();

        player.doAction(actionChoice, enemyChoice);

        if(enemyChoice.getCurrentHealth() <= 0) enemies.remove(enemyChoice);

        showAttackLog(player, actionChoice, enemyChoice);
    }

    public PlayerAction playerChoiceAction(){
        int optionChoice = 0;

        Map<Integer, PlayerAction> playerActionsOptions = IntStream.range(0, player.getActions().size())
                .boxed()
                .collect(Collectors.toMap(i -> i + 1, player::getAction));

        int exitOption = playerActionsOptions.size() + 1;
        StringBuilder menuOptions = new StringBuilder();

        playerActionsOptions.forEach((k,v) -> menuOptions.append(String.format("%s - %s\n", k, v)));
        menuOptions.append(String.format("%s - Desistir", exitOption));

        do{
            System.out.println("----Ações----");
            System.out.println(menuOptions);
            System.out.print("Escolha: ");
            optionChoice = sc.nextInt();

            if(optionChoice == exitOption) {
                gameState = GameState.ENDED;
                return null;
            }

            if(optionChoice < 0 || optionChoice > playerActionsOptions.size() + 1){
                System.out.println("Escolha uma opção válida!");
            }

        }while(optionChoice < 0 || optionChoice > playerActionsOptions.size() + 1);

        return playerActionsOptions.get(optionChoice);
    }

    public Enemy playerChoiceEnemy() {
        int enemyChoice = 0;
        System.out.println("\n----Alvo(s)----");
        Map<Integer, Enemy> enemiesOptions = IntStream.range(0, enemies.size())
                .boxed()
                .collect(Collectors.toMap(i -> i + 1, enemies::get));

        boolean validTargets;
        do {
            validTargets = false;
            enemiesOptions.forEach((k, v) -> System.out.printf("%s - %s\n", k, v.getStatus()));
            System.out.print("Escolha: ");
            enemyChoice = sc.nextInt();

            if (enemyChoice < 0 || enemyChoice > enemiesOptions.size()) {
                System.out.println("Escolha um inimigo válido");
                continue;
            }

            validTargets = true;

        } while (!validTargets);

        return enemiesOptions.get(enemyChoice);
    }

    public void enemiesTurn() {
        if (!enemies.isEmpty()) {
            float allDamage = 0f;
            System.out.println("----Inimigos----");
            enemies.forEach(e -> System.out.println(e.getStatus()));
            for (var enemy : enemies) {
                if (player.getCurrentHealth() > 0) {
                    EnemyAction action = enemy.getActions().getFirst();
                    enemy.doAction(action, player);
                    allDamage += action.getRealDamage(enemy);
                    showAttackLog(enemy, action, player);
                }
            }
            System.out.printf("\nVocê recebeu no total %s de dano\n", allDamage);
        }
    }

    public void showAttackLog(Entity actor, Action action, Entity target){
        System.out.printf("\n%s usou %s em %s, causando %s de dano\n", actor, action.getName(), target, action.getRealDamage(actor));
    }
}
