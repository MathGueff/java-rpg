package models.actions;

import models.entities.Entity;

public class PlayerAction extends Action{

    public PlayerAction(String name, float damage, int useCooldown) {
        super(name, damage, useCooldown);
    }

    @Override
    public void execute(Entity<?> actor, Entity<?>... targets) {
        for(var target : targets){
            target.takeDamage(getRealDamage(actor));
        }
        resetCooldown();
    }
}
