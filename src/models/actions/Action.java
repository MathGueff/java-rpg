package models.actions;

import enums.Element;
import models.entities.Entity;

import java.util.Objects;

public abstract class Action {
    public abstract void execute(Entity<?> actor, Entity<?>... targets);

    private final String name;
    private final float damage;
    private final Element element;

    private final int useCooldown;
    private int cooldown;

    public Action(String name, float damage, int useCooldown, Element element) {
        this.name = name;
        this.damage = damage;
        this.useCooldown = useCooldown;
        this.cooldown = 0;
        this.element = element;
    }

    public boolean canUse(){
        return cooldown == 0;
    }

    public void resetCooldown(){
        cooldown = useCooldown + 1;
    }

    public void decreaseCooldown(){
        if(cooldown == 0) return;
        cooldown--;
    }

    public float getDamage() {
        return damage;
    }

    public float getRealDamage(Entity<?> actor, Entity<?> target){
        float totalDamage = damage * actor.getBaseDamage();

        Element weakness = target.getElement().getWeakness();

        if(element.equals(weakness))
            totalDamage *= 2f;

        return totalDamage;
    }

    public boolean isStrong(Entity<?> entity){
        return element.equals(entity.getElement().getWeakness());
    }

    public String getName() {
        return name;
    }

    public Element getElement() {
        return element;
    }

    public int getUseCooldown() {
        return useCooldown;
    }

    public int getCooldown() {
        return cooldown;
    }

    @Override
    public String toString() {
        String strAction = String.format("%s - %.0f de dano ", name, damage);
        if(useCooldown > 0)
            strAction += String.format("(%s %s de cooldown)", useCooldown, useCooldown == 1 ? "turno" : "turnos");
        return strAction;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Action action)) return false;
        return Float.compare(getDamage(), action.getDamage()) == 0 && useCooldown == action.useCooldown && cooldown == action.cooldown && Objects.equals(getName(), action.getName()) && getElement() == action.getElement();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDamage(), getElement(), useCooldown, cooldown);
    }
}
