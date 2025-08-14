package enums;

public enum EnemyType {
    ROGUE("Rogue", 1f, 0.8f, 2f),
    WARRIOR("Warrior",0.5f, 1.5f, 0.6f),
    MAGE("Mage", 1.5f,0.5f,0.8f),
    GIANT("Giant", 3f, 2.5f,0.2f),
    SPIDER("Spider", 1f, 0.5f, 1f),
    SLIME("Slime", 0.2f, 1f,1f),
    EXECUTOR("Executor", 5f, 1.2f, 0.1f);

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
