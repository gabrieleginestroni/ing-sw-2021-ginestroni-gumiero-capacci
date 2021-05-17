package it.polimi.ingsw.server.model.cards;

import it.polimi.ingsw.server.model.Resource;

/**
 * @author Giacomo Gumiero
 * Class that implements the requirements of type Resource
 */
public class ResourceRequirement extends Requirement{
    private Resource resource;
    private int quantity;

    public Resource getResource() {
        return resource;
    }

    public int getQuantity() {
        return quantity;
    }
}