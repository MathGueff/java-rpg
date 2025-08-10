package models;

import models.actions.Action;
import models.actions.PlayerActions;
import models.actions.SpellAction;

import java.util.List;

public class Player implements Entity{
    private final String name;
    private final double maxHealth;
    private final int maxMana;
    private double health;
    private int mana;
    private final List<PlayerActions> actions;

    public Player(String name, double maxHealth, int maxMana, List<PlayerActions> actions) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.maxMana = maxMana;
        this.mana = maxMana;
        this.actions = actions;
    }

    @Override
    public void doAction (Action action, Entity target) {
        action.execute(target);
        if(action instanceof SpellAction spellAction)
            decreaseMana(spellAction.getCost());
    }

    @Override
    public void takeDamage(double damage) {
        health -= damage;
        if(health <= 0){
            health = 0;
            System.out.println("Você morreu!");
        }
    }

    public String getStatus(){
        return String.format("""
                ----%s----
                Vida: %.0f/%.0f
                Mana: %s/%s\s
                \s""", name, health,maxHealth, mana, maxMana);
    }

    public void decreaseMana(int manaToDecrease){
        mana -= manaToDecrease;
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public List<PlayerActions> getActions() {
        return actions;
    }

    public PlayerActions getAction(int i){
        return actions.get(i);
    }

    public int countActions(){
        return actions.size();
    }

    @Override
    public String toString() {
        return name;
    }
}
