package models;

import models.actions.Action;

public interface Entity {
    void takeDamage(double damage);
    void doAction(Action action, Entity target);
}
