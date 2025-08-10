package models.actions;

import models.Entity;

public class EnemyAction extends Action{
    private double damage;

    public EnemyAction(String name, double damage) {
        super(name);
        this.damage = damage;
    }

    @Override
    public void execute(Entity target) {
        target.takeDamage(damage);
        System.out.printf("%s sofreu %s de dano\n",target,damage);
    }
}
