package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;

/**
 * @author Gabriele Ginestroni
 * Class that represents the Warehouse storage slot that every player's board has
 */

public class WarehouseDepot extends Depot {
    /**
     * Constructor that creates a warehouse depot with the specified resource type and storage limit, if the resource type
     * is allowed
     * @param resource resource type of the storage to be created
     * @param storageLimit storage resource limit of the storage to be created
     * @throws invalidResourceTypeException throwed if the resource type is illegal
     */
    public WarehouseDepot(Resource resource,int storageLimit) throws invalidResourceTypeException {

        invalidResourceCheck(resource);

        this.resourceType = resource;
        this.storageLimit = storageLimit;
        this.storageQuantity = 0;
    }

    /**
     * Sets the depot's storage resource type if it's empty
     * @param resourceType resource type to set
     * @throws invalidWarehouseDepotTypeChangeException throwed if the depot isn't empty
     * @throws invalidResourceTypeException throwed if the resource type is illegal
     */
    public void setResourceType(Resource resourceType) throws invalidWarehouseDepotTypeChangeException,invalidResourceTypeException {

        invalidResourceCheck(resourceType);

        if(this.storageQuantity != 0){
            throw new invalidWarehouseDepotTypeChangeException();
        }
        this.resourceType = resourceType;

    }

}
