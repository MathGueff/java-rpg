package models.actions;

import models.Entity;

public class EnemyAction extends Action{
    private double damage;

    public EnemyAction(String name, double damage) {
        super(name);
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public void execute(Entity target) {
        target.takeDamage(damage);
    }
}
