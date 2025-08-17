package view;

import models.actions.Action;
import models.actions.PlayerAction;
import models.entities.Enemy;
import models.entities.Entity;
import models.entities.Player;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameView {
    private final Scanner scanner;
    private final GameLogger gameLogger;

    public GameView(Scanner scanner, GameLogger gameLogger) {
        this.scanner = scanner;
        this.gameLogger = gameLogger;
    }

    public void showTurnResume(int i){
        System.out.printf("TURNO %s - RESUMO%n", i);
        System.out.println(gameLogger.getResume());
        gameLogger.clear();
    }

    public void spacing(int i){
        for (int j = 0; j < i; j++) {
            System.out.println();
        }
    }

    public void gameStarted(){
        System.out.println("====---O JOGO FOI INICIADO---====");
    }

    public void showElementsInteractions(){
        System.out.println("FRAQUEZAS: ");
        System.out.println("AIR -> EARTH -> LIGHTNING -> WATER -> FIRE -> AIR");
    }

    public void gameFinished(){
        System.out.println("====---O JOGO FOI FINALIZADO---====");
        scanner.close();
    }

    public void actionsStarted(){
        spacing(1);
        System.out.println("[--AÇÕES--]");
        spacing(1);
    }

    public void actionsFinished(){
        spacing(1);
        System.out.println("[--FIM DAS AÇÕES--]");
        spacing(1);
    }

    public void showActualTurn(int turn){
        System.out.printf("%n-====Turno %s ====-%n%n",turn);
    }

    public void endTurn(){
        System.out.println("Digite qualquer coisa e pressione enter para encerrar o turno");
        scanner.next();
        scanner.nextLine();
    }

    public void showAttackDescription(Entity<?> actor, Action action, Entity<?> target){
        float totalDamage = action.getRealDamage(actor, target);

        String description = String.format("%s: %s usou %s em %s, causando %.0f de dano -> %s (-%.0f) ",
                actor instanceof Player ? "Você" : "Inimigo",
                actor, action.getName(), target, totalDamage,
                target.getStatus(), totalDamage);

        if(action.isStrong(target))
            description += "BÔNUS DE FRAQUEZA";

        gameLogger.appendLog(description);
        if(target instanceof  Player) gameLogger.addDamageOnPlayer(totalDamage);

        System.out.printf(description);
    }

    public void showVictoryMessage(){
        System.out.println("Todos inimigos foram derrotados! Você venceu!");
    }

    public void showSurrenderMessage(){
        System.out.println("Você desistiu");
    }

    public void showInvalidOptionMessage(){
        System.out.println("Escolha uma opção válida!");
    }

    public void showEnemiesOptionsHeader(){
        System.out.println("\n----Alvos----");
    }

    public void showEnemyDeath(Enemy enemy, Action action){
        System.out.printf("%n%s foi morto por %s", enemy.getName(), action.getName());
    }

    public void showPlayerDeath(Player player, Enemy enemy, Action action){
        System.out.printf("%nO jogador %s foi morto por %s de %s",player.getName(), action.getName(), enemy.getName());
    }

    public void showEntities(Player player, List<Enemy> enemies){
        showPlayer(player);
        System.out.println("----Inimigos----");
        enemies.forEach(this::showEnemy);
    }

    public void showEnemy(Enemy enemy){
        System.out.println(enemy.getStatus());
    }

    public void showPlayer(Player player){
        System.out.println(player.getInfo());
    }

    public void showPlayOrder(List<Entity<?>> playOrder) {
        System.out.println("\nORDEM DE JOGADA:\n");

        playOrder.stream()
                .map(e -> String.format("%s - %.0f velocidade", e.getName(), e.getSpeed()))
                .forEach(System.out::println);
    }

    public void enemySkipTurn(Enemy enemy){
        System.out.printf("%s passou a vez\n", enemy.getName());
    }

    public String mapPlayerOptions(Map<Integer, PlayerAction> playerActionsOptions, Player player){
        int exitOption = playerActionsOptions.size() + 1;
        StringBuilder menuOptions = new StringBuilder();

        playerActionsOptions.forEach((k,v) -> menuOptions.append(String.format("%s - %s\n", k, v)));
        menuOptions.append(String.format("%s - Desistir", exitOption));

        return menuOptions.toString();
    }

    public int selectAction(String options){
        System.out.println("----Opções----");
        System.out.println(options);
        System.out.print("Escolha: ");
        return scanner.nextInt();
    }
    public int selectEnemy(Map<Integer,Enemy> enemiesOptions){
        enemiesOptions.forEach((k, v) -> System.out.printf("%s - %s%n", k, v.getStatus()));
        System.out.print("Escolha: ");
        return scanner.nextInt();
    }

    public String setPlayerName(){
        String name;
        boolean validName = false;
        do{
            validName = true;
            System.out.println("Digite o nome para o personagem: ");
            name = scanner.nextLine();

            if(!name.matches("[a-zA-Z ]+")){
                System.out.println("Digite um nome válido");
                validName = false;
            }
        } while(!validName);
        return name;
    }
}
