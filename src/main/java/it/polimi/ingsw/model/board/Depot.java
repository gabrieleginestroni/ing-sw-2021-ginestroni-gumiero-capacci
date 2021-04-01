package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;

/**
 * @author Gabriele Ginestroni
 * Class that represents a single storage unit without any distinction between Warehouse and Leader Cards' depots
 */
public abstract class Depot {
    int storageQuantity;
    int storageLimit;
    Resource resourceType;

    /**
     *Adds an amount of resources to the depot, if the resource type matches to the one of the depot and if the new
     * quantity doesn't exceed the storage limit quantity
     *
     * @param resource type of the resource to add
     * @param quantity amount of resource to add
     * @throws addResourceLimitExceededException thrown if the new quantity would exceed the storage limit
     * @throws invalidResourceTypeException thrown if the resource type is illegal
     */
    public void addResource(Resource resource, int quantity) throws addResourceLimitExceededException,invalidResourceTypeException {
        int newQuantity;


        wrongResourceCheck(resource);

        newQuantity= this.storageQuantity + quantity;
        if(newQuantity>storageLimit){
            throw new addResourceLimitExceededException();
        }
        this.storageQuantity = newQuantity;

    }

    /**
     *Adds an amount of resources to the depot, if the resource type matches to the one of the depot and if
     * the quantity to remove is smaller than the actual quantity stored
     * @param resource type of the resource to remove
     * @param quantity amount of resource to remove
     * @throws removeResourceLimitExceededException thrown if the quantity to remove is greater than the actual quantity stored
     * @throws invalidResourceTypeException thrown if the resource type is illegal
     */
    public void removeResource(Resource resource,int quantity) throws removeResourceLimitExceededException,invalidResourceTypeException{
        int newQuantity;


        wrongResourceCheck(resource);

        newQuantity= this.storageQuantity - quantity;
        if(newQuantity<0){
            throw new removeResourceLimitExceededException();
        }
        this.storageQuantity = newQuantity;
    }



    /**
     * Checks if the resource type matches the one of the depot
     * @param resource resource type to check
     * @throws invalidResourceTypeException thrown if the resource type is illegal
     */
    private void wrongResourceCheck(Resource resource) throws invalidResourceTypeException {
        if(resource != this.resourceType){
            throw new invalidResourceTypeException();
        }
    }
    /**
     * Sets the depot's storage resource type if it's empty
     * @param resourceType resource type to set
     * @throws invalidDepotTypeChangeException thrown if the depot isn't empty
     *
     */
    public void setResourceType(Resource resourceType) throws invalidDepotTypeChangeException {



        if(this.storageQuantity != 0){
            throw new invalidDepotTypeChangeException();
        }
        this.resourceType = resourceType;

    }


    /**
     *  Depot's resource quantity getter
     * @return storageQuantity
     */
    int getResourceQuantity(){
        return this.storageQuantity;
    }

    /**
     * Depot's resource type getter
     * @return resourceType
     */
    Resource getResourceType(){
        return this.resourceType;
    }



}
