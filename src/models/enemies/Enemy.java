package models.enemies;

import models.Element;
import models.Entity;
import models.actions.Action;
import models.actions.EnemyAction;

import java.util.List;

public abstract class Enemy implements Entity {
    private double life;
    private List<EnemyAction> actions;
    private Element element;

    public Enemy(double life, List<EnemyAction> actions, Element element) {
        this.life = life;
        this.element = element;
        this.actions = actions;
    }

    public Enemy(double life, List<EnemyAction> actions) {
        this.life = life;
        this.actions = actions;
    }

    public double getHealth() {
        return life;
    }

    public List<EnemyAction> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getClass().getSimpleName(), element != null ? element : "");
    }

    @Override
    public void takeDamage(double damage){
        life -= damage;
    }

    @Override
    public void doAction(Action action, Entity target) {
        action.execute(target);
    }
}
