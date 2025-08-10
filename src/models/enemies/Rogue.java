package models.enemies;

import models.Element;
import models.actions.EnemyAction;

import java.util.List;

public class Rogue extends Enemy{
    public Rogue(double life, List<EnemyAction> actions, Element element) {
        super(life, actions, element);
    }

    public Rogue(double life, List<EnemyAction> actions) {
        super(life, actions);
    }
}
