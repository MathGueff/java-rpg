package models;

import models.actions.EnemyAction;
import models.actions.PlayerActions;
import models.enemies.Enemy;
import models.enemies.Horde;

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
        while(gameState != GameState.ENDED && player.getHealth() > 0){
            System.out.printf("\n\n-====Turno %s ====-\n\n",turn);

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

        PlayerActions actionChoice = playerChoiceAction();
        if(actionChoice == null) return;

        Enemy enemyChoice = playerChoiceEnemy();

        player.doAction(actionChoice, enemyChoice);

        if(enemyChoice.getHealth() <= 0) enemies.remove(enemyChoice);

        System.out.printf("\n%s usou %s em %s\n", player, actionChoice, enemyChoice);
    }

    public void enemiesTurn(){
        if(!enemies.isEmpty()){
            System.out.println("----Inimigos----");
            for(var enemy : enemies){
                if(player.getHealth() > 0) {
                    EnemyAction action = enemy.getActions().getFirst();
                    enemy.doAction(action, player);
                    System.out.printf("\n%s usou %s em %s\n", enemy, action, player);
                }
            }
        }
    }

    public PlayerActions playerChoiceAction(){
        int optionChoice = 0;

        Map<Integer, PlayerActions> playerActionsOptions = IntStream.range(0, player.countActions())
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

    public Enemy playerChoiceEnemy(){
        int enemyChoice = 0;
        System.out.println("\n----Alvo----");
        Map<Integer, Enemy> currentEnemiesOptions = IntStream.range(0, enemies.size())
                .boxed()
                .collect(Collectors.toMap(i -> i + 1, i -> enemies.get(i)));

        do{
            currentEnemiesOptions.forEach((k,v) -> System.out.printf("%s - %s (%s)\n", k, v, v.getHealth()));
            System.out.print("Escolha: ");
            enemyChoice = sc.nextInt();

            if(enemyChoice < 0 || enemyChoice > currentEnemiesOptions.size()){
                System.out.println("Escolha um inimigo válido");
            }

        }while(enemyChoice < 0 || enemyChoice > currentEnemiesOptions.size());

        return currentEnemiesOptions.get(enemyChoice);
    }
}
