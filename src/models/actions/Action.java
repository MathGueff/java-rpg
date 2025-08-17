package models.actions;

import models.entities.Entity;

public abstract class Action {
    public abstract void execute(Entity<?> actor, Entity<?>... targets);

    private final String name;
    private final float damage;

    private final int useCooldown;
    private int cooldown;

    public Action(String name, float damage, int useCooldown) {
        this.name = name;
        this.damage = damage;
        this.useCooldown = useCooldown;
        this.cooldown = 0;
    }

    public boolean canUse(){
        return cooldown == 0;
    }

    public void resetCooldown(){
        cooldown = useCooldown;
    }

    public void decreaseCooldown(int amount){
        if(cooldown == 0) return;
        cooldown -= amount;
    }


    public float getDamage() {
        return damage;
    }

    public float getRealDamage(Entity<?> actor){
        return damage * actor.getBaseDamage();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String strAction = String.format("%s - %.0f de dano ", name, damage);
        if(useCooldown > 0)
            strAction += String.format("(%s %s de cooldown)", useCooldown, useCooldown == 1 ? "turno" : "turnos");
        return strAction;
    }
}
