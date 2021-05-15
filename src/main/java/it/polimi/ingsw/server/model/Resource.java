package it.polimi.ingsw.server.model;
/**
 * @author Giacomo Gumiero
 * Enum to define resource type
 */
public enum Resource {
    COIN("Yellow"),
    SERVANT("Purple"),
    STONE("Grey"),
    SHIELD("Blue"),
    FAITH("Red"),
    WHITE("White");

    private final String color;

    Resource(String color){
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
