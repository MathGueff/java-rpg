package models;

public enum Element {
    EARTH(0),
    FIRE(1),
    LIGHTNING(2),
    WATER(3),
    AIR(4);

    private int code;
    private Element weakness;

    Element(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public Element getWeakness() {
        return weakness;
    }

    public static Element getWithCode(int code){
        for(Element e : values()){
            if(e.getCode() == code){
                return e;
            }
        }
        return null;
    }

    static {
        EARTH.weakness = Element.AIR;
        FIRE.weakness = Element.WATER;
        LIGHTNING.weakness = Element.EARTH;
        WATER.weakness = Element.LIGHTNING;
        AIR.weakness = Element.FIRE;
    }
}
