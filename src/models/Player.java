package models;

import models.actions.Action;
import models.actions.PlayerActions;
import models.actions.SpellAction;

import java.util.List;

public class Player implements Entity{
    private final String name;
    private double health;
    private int mana;
    private final List<PlayerActions> actions;

    public Player(String name, double health, int mana, List<PlayerActions> actions) {
        this.name = name;
        this.health = health;
        this.mana = mana;
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
    }

    public String getStatus(){
        return String.format("""
                ----%s----
                Vida: %s
                Mana: %s 
                """, name, health, mana);
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
