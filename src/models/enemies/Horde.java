package models.enemies;

import java.util.List;

public class Horde {
    private List<Enemy> enemies;

    public Horde(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void update(){
        enemies = enemies.stream().filter(e -> e.getHealth() > 0).toList();
    }
}
