package it.polimi.ingsw.model.games;

import it.polimi.ingsw.model.Resource;

/**
 * @author Giacomo Gumiero
 * Class that represents the marble
 */
public class Marble {
    private final String color;
    private final Resource resource;

    public Marble(String color, Resource resource) {
        this.color = color;
        this.resource = resource;
    }

    /**
     *
     * @return color of marble
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @return Resource type
     */
    public Resource getResource() {
        return resource;
    }
}
