package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;

/**
 * @author Gabriele Ginestroni
 * Class that represents the Warehouse storage slot
 */

public class WarehouseDepot extends Depot {
    /**
     * Empty Warehouse depot constructor with  resource type initialized to null
     *
     * @param storageLimit storage resource limit of the storage to be created
     *
     */
    public WarehouseDepot(int storageLimit) {

        this.resourceType = null;
        this.storageLimit = storageLimit;
        this.storageQuantity = 0;
    }

    /**
     *Removes an amount of resources from the depot, if the resource type matches to the one of the depot and if
     * the quantity to remove is smaller than the actual quantity stored. Sets resourceType to null if the new quantity
     * is zero.
     * @param resource type of the resource to remove
     * @param quantity amount of resource to remove
     * @throws removeResourceLimitExceededException thrown if the quantity to remove is greater than the actual quantity stored
     * @throws invalidResourceTypeException thrown if the resource type doesn't match the one of the depot
     */
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
