package models.enemies;

import models.Element;
import models.Entity;
import models.actions.Action;
import models.actions.EnemyAction;

import java.util.List;

public abstract class Enemy implements Entity {
    private final String name;
    private final double maxHealth;
    private double health;
    private final List<EnemyAction> actions;
    private final Element element;

    public Enemy(double maxHealth, List<EnemyAction> actions, Element element) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.element = element;
        this.actions = actions;
        name = getClass().getSimpleName() + (element != null ? " " + element : "");
    }

    public Enemy(double maxHealth, List<EnemyAction> actions) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.actions = actions;
        this.element = null;
        name = getClass().getSimpleName();
    }

    public double getHealth() {
        return health;
    }

    public List<EnemyAction> getActions() {
        return actions;
    }

    public String getStatus(){
        return String.format("%s %.0f/%.0f", name, health, maxHealth);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void takeDamage(double damage){
        health -= damage;
        if(health <= 0) health = 0;
    }

    @Override
    public void doAction(Action action, Entity target) {
        action.execute(target);
    }
}
