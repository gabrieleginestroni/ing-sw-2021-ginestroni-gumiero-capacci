package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.server.model.Resource;

/**
 * @author Gabriele Ginestroni
 * Class that represents the depot obtained by a Storage Leader Card's power
 */
public class LeaderDepot extends Depot {

    /**
     *Constructor of a leader depot card power storage initialized with the default value for an empty Leader Depot
     * @param resource resource type of Leader depot to create
     *
     */
    public LeaderDepot(Resource resource)  {


        this.resourceType = resource;
        this.storageLimit = 2;
        this.storageQuantity = 0;
    }


}
