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
    private Player player;
    private List<Horde> hordes;
    private Horde currentHorde;
    public static GameState gameState;

    public Game(Scanner sc) {
        this.sc = sc;
    }

    public void startGame(){
        currentHorde = hordes.getFirst();
        System.out.println("O jogo foi iniciado");
        int turn = 1;
        while(gameState != GameState.ENDED && player.getHealth() > 0){
            System.out.printf("\n\n-====Turno %s ====-\n\n",turn);

            playerTurn();
            if(gameState == GameState.ENDED) break;

            enemiesTurn();

            //turnResume();

            if(currentHorde.getEnemies().isEmpty()){
                hordes.removeFirst();

                if(hordes.isEmpty()){
                    System.out.println("Todas as hordas foram derrotadas! Você venceu!");
                    gameState = GameState.ENDED;
                    break;
                } else {
                    currentHorde = hordes.getFirst();
                }
            }
            turn++;
        }
        System.out.println("O jogo foi finalizado");
    }

    public void playerTurn(){
        System.out.println("[Seu turno]\n");
        System.out.println(player.getStatus());
        PlayerActions actionChoice = playerChoiceAction();
        if(actionChoice == null) return;
        Enemy enemyChoice = playerChoiceEnemy();
        player.doAction(actionChoice, enemyChoice);
        currentHorde.update();
        System.out.println("\n[Fim do seu turno]\n");
    }

    public void enemiesTurn(){
        if(!currentHorde.getEnemies().isEmpty()){
            System.out.println("[Turno dos inimigos]\n");
            for(var enemy : currentHorde.getEnemies()){
                if(player.getHealth() > 0) {
                    EnemyAction action = enemy.getActions().getFirst();
                    enemy.doAction(action, player);
                }
            }
            System.out.println("\n[Fim do turno dos inimigos]");
        }
    }

    public void turnResume(){
        System.out.println(player.getStatus());
        currentHorde.getEnemies().forEach(e -> System.out.println(e.getStatus()));
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
        System.out.println("----Inimigos----");
        Map<Integer, Enemy> currentEnemiesOptions = IntStream.range(0, currentHorde.getEnemies().size())
                .boxed()
                .collect(Collectors.toMap(i -> i + 1, i -> currentHorde.getEnemies().get(i)));

        do{
            currentEnemiesOptions.forEach((k,v) -> System.out.printf("%s - %s (%s)\n", k, v, v.getHealth()));
            enemyChoice = sc.nextInt();

            if(enemyChoice < 0 || enemyChoice > currentEnemiesOptions.size()){
                System.out.println("Escolha um inimigo válido");
            }

        }while(enemyChoice < 0 || enemyChoice > currentEnemiesOptions.size());

        return currentEnemiesOptions.get(enemyChoice);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setHordes(List<Horde> hordes) {
        this.hordes = hordes;
    }
}
