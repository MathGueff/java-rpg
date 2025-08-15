package models.entities;
import models.actions.PlayerAction;

import java.util.List;

public class Player extends Entity<PlayerAction> {

    public Player(String name, float maxHealth, float speed, List<PlayerAction> actions) {
        super(name, 1, maxHealth, speed, actions);
    }

    public String getInfo(){
        return String.format("""
                ----%s----
                Vida: %.0f/%.0f
                \s""", getName(), getCurrentHealth(),getMaxHealth());
    }
}
