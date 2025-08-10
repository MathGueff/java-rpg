package models.actions;

import models.Entity;

import java.util.List;

public abstract class Action {
    public abstract void execute(Entity target);

    private String name;

    public Action(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
