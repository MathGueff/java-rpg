package models.actions;

import models.Entity;

public class SpellAction extends PlayerActions{
    private final int cost;
    private final double damage;

    public SpellAction(String name, int cost, double damage) {
        super(name);
        this.cost = cost;
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public void execute(Entity target) {
        target.takeDamage(damage);
    }
}
