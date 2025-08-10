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
        showDescription(target);
        target.takeDamage(damage);
    }

    @Override
    public void showDescription(Entity target) {
        System.out.printf("%s sofreu %s de dano\n",target,damage);
    }
}
