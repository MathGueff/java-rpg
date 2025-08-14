package models.actions;

import models.Entity;

public class EnemyAction extends Action{
    public EnemyAction(String name, float damage) {
        super(name, damage);
    }

    @Override
    public void execute(Entity actor, Entity... targets) {
        targets[0].takeDamage(getRealDamage(actor));
    }
}
