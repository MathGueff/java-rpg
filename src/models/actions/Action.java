package models.actions;

import models.entities.Entity;

public abstract class Action {
    public abstract void execute(Entity actor, Entity... targets);

    private String name;
    private float damage;

    public Action(String name, float damage) {
        this.name = name;
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public float getRealDamage(Entity actor){
        return damage * actor.getBaseDamage();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s - %s de dano", name, damage);
    }
}
