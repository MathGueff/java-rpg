package models.actions;

import models.entities.Entity;

public class EnemyAction extends Action{
    public EnemyAction(String name, float damage) {
        super(name, damage);
    }

    @Override
    public void execute(Entity actor, Entity... targets) {
        for(var target : targets)
            target.takeDamage(getRealDamage(actor));
    }
}
