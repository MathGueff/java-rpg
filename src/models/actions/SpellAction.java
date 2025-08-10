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
        showDescription(target);
        target.takeDamage(damage);
    }

    @Override
    public void showDescription(Entity target) {
        System.out.printf("%s foi atingido e sofreu %s de dano\n",target,damage);
    }
}
