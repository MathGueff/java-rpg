import models.Element;
import models.Game;
import models.Player;
import models.actions.EnemyAction;
import models.actions.PlayerActions;
import models.actions.SpellAction;
import models.enemies.Enemy;
import models.enemies.Horde;
import models.enemies.Rogue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Game game = new Game(sc);
        Player player = new Player("Matheus", 200, 10, configurePlayerActions());
        game.setPlayer(player);

        EnemyAction enemyAction1 = new EnemyAction("Corte rápido", 20);
        EnemyAction enemyAction2 = new EnemyAction("Corte pesado", 100);
        Enemy enemy = new Rogue(15, List.of(enemyAction1));
        Enemy enemy2 = new Rogue(28, List.of(enemyAction1));
        Enemy enemy3 = new Rogue(45, List.of(enemyAction2));
        Horde horde1 = new Horde(List.of(enemy, enemy2, enemy3));
        Horde horde2 = new Horde(List.of(enemy2, enemy3));

        List<Horde> hordes = new ArrayList<>();
        hordes.add(horde1);
        hordes.add(horde2);

        game.setHordes(hordes);

        game.startGame();
        sc.close();
    }

    public static List<PlayerActions> configurePlayerActions(){
        var action = new SpellAction("Bola de fogo", 1,25);
        var action2 = new SpellAction("Espinhos de gelo", 2,20);
        var action3 = new SpellAction("Tornado", 5,4);
        return List.of(action,action2,action3);
    }

    public static void printDivision(String symbol, int length){
        System.out.println(symbol.repeat(length));
    }
}
