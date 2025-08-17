package models.actions;

import enums.Element;
import models.entities.Entity;

public class EnemyAction extends Action{
    public EnemyAction(String name, float damage, int useCooldown, Element element) {
        super(name, damage, useCooldown, element);
    }

    @Override
    public void execute(Entity<?> actor, Entity<?>... targets) {
        for(var target : targets){
            target.takeDamage(getRealDamage(actor, target));
        }
        resetCooldown();
    }
}
