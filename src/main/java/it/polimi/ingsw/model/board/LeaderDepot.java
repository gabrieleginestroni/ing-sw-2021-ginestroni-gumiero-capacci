package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;

/**
 * @author Gabriele Ginestroni
 * Class that represents the depot obtained by a Storage Leader Card's power
 */
public class LeaderDepot extends Depot {

    /**
     *Constructor that creates a Leader Depot if the resource type is allowed and initializes the instance
     * with the default value for an empty Leader Depot with its storage limit
     * @param resource resource type of Leader depot to create
     * @throws invalidResourceTypeException throwed if the resource type is illegal
     */
    public LeaderDepot(Resource resource) throws invalidResourceTypeException {

        invalidResourceCheck(resource);

        this.resourceType = resource;
        this.storageLimit = 2;
        this.storageQuantity = 0;
    }


}
