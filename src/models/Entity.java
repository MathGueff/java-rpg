package models;

import models.actions.Action;

import java.util.List;

public abstract class Entity<T>{
    private final String name;
    private final float baseDamage;
    private final float maxHealth;
    private float currentHealth;
    private float speed;
    private final List<T> actions;

    public Entity(String name, float baseDamage, float maxHealth, float speed, List<T> actions) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
        this.speed = speed;
        this.actions = actions;
    }

    public void takeDamage(float baseDamage){
        currentHealth -= baseDamage;
        if(currentHealth < 0) currentHealth = 0;
    }
    public void doAction(Action action, Entity... targets){
        action.execute(this, targets);
    }

    public String getName() {
        return name;
    }

    public float getBaseDamage() {
        return baseDamage;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public float getSpeed() {
        return speed;
    }

    public List<T> getActions() {
        return actions;
    }

    public T getAction(int i){
        return actions.get(i);
    }

    public abstract String getStatus();

    @Override
    public String toString() {
        return name;
    }
}
