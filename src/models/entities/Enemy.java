package models.entities;

import enums.Element;
import enums.EnemyType;
import models.actions.EnemyAction;

import java.util.List;

public class Enemy extends Entity<EnemyAction> {
    private final EnemyType type;

    public Enemy(float maxHealth, float speed, List<EnemyAction> actions, Element element, EnemyType type) {
        super(
                String.format("%s %s", element.getName(),type.getName()).trim(),
                type.getDamageModifier(),
                maxHealth * type.getHealthModifier(),
                speed * type.getSpeedModifier(),
                actions,
                element
        );
        this.type = type;
    }
}
