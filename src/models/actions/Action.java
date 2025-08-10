package models.actions;

import models.Entity;

public abstract class Action {
    public abstract void execute(Entity target);

    public abstract void showDescription(Entity target);

    private String name;

    public Action(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
