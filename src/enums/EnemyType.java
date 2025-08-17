package enums;

public enum EnemyType {
    ROGUE("Rogue", 1.4f, 0.7f, 2.0f),
    WARRIOR("Warrior", 0.9f, 1.8f, 0.7f),
    MAGE("Mage", 1.8f, 0.6f, 0.9f),
    GIANT("Giant", 1.2f, 3.0f, 0.4f),
    SPIDER("Spider", 1.0f, 0.8f, 1.6f),
    SLIME("Slime", 0.6f, 1.2f, 0.8f),
    EXECUTOR("Executor", 1.3f, 1.5f, 1.2f);

    private final String name;
    private final float damageModifier;
    private final float healthModifier;
    private final float speedModifier;

    EnemyType(String name, float damageModifier, float healthModifier, float speedModifier) {
        this.name = name;
        this.damageModifier = damageModifier;
        this.healthModifier = healthModifier;
        this.speedModifier = speedModifier;
    }

    public String getName() {
        return name;
    }

    public float getHealthModifier() {
        return healthModifier;
    }

    public float getSpeedModifier() {
        return speedModifier;
    }

    public float getDamageModifier() {
        return damageModifier;
    }
}
