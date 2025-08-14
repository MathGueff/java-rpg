package enums;

public enum Element {
    NONE(""),
    EARTH("Earth"),
    FIRE("Fire"),
    LIGHTNING("Lightning"),
    WATER("Water"),
    AIR("Air");

    private String name;
    private Element weakness;

    Element(String name) {
        this.name = name;
    }

    public Element getWeakness() {
        return weakness;
    }

    public String getName() {
        return name;
    }

    static {
        EARTH.weakness = Element.AIR;
        FIRE.weakness = Element.WATER;
        LIGHTNING.weakness = Element.EARTH;
        WATER.weakness = Element.LIGHTNING;
        AIR.weakness = Element.FIRE;
    }
}
