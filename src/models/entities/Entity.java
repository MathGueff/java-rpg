package models.entities;

import models.actions.Action;

import java.util.List;

public abstract class Entity<T>{
    private final String name;
    private final float baseDamage;
    private final float maxHealth;
    private float currentHealth;
    private final float speed;
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

    public void doAction(Action action, Entity<?>... targets){
        if(targets == null || targets.length == 0)
            throw new IllegalArgumentException("Pelo menos 1 alvo deve ser fornecido");
        action.execute(this, targets);
    }

    public String getStatus(){
        return String.format("%s - %.0f/%.0f", name, currentHealth, maxHealth);
    }

    public boolean isDead(){
        return currentHealth <= 0;
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

    @Override
    public String toString() {
        return name;
    }
}
