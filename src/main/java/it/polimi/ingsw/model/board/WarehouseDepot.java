package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;

/**
 * @author Gabriele Ginestroni
 * Class that represents the Warehouse storage slot that every player's board has
 */

public class WarehouseDepot extends Depot {
    /**
     * Constructor that creates a warehouse depot with his storage limit
     *
     * @param storageLimit storage resource limit of the storage to be created
     *
     */
    public WarehouseDepot(int storageLimit) {

        this.resourceType = null;
        this.storageLimit = storageLimit;
        this.storageQuantity = 0;
    }

    @Override
    public void removeResource(Resource resource,int quantity) throws removeResourceLimitExceededException,invalidResourceTypeException{
        int newQuantity;


        wrongResourceCheck(resource);

        newQuantity= this.storageQuantity - quantity;
        if(newQuantity<0){
            throw new removeResourceLimitExceededException();
        }

        this.storageQuantity = newQuantity;
        if(newQuantity == 0) setResourceType(null);


    }

}
