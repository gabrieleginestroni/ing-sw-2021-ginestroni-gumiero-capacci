package it.polimi.ingsw.model.board;

import it.polimi.ingsw.exceptions.invalidStrongBoxRemoveException;
import it.polimi.ingsw.model.Resource;

import java.util.HashMap;
import java.util.Map;

/**@author Gabriele Ginestroni
 * Class that represents the player's Strongbox. It can store all types of resources
 */
public class StrongBox {
    private final Map<Resource,Integer> resources;

    /**
     * Constructor of an empty Strongbox
     */
    public StrongBox() {
        this.resources = new HashMap<>();
    }

    /**
     * Adds an amount of resource to the strongbox
     * @param res Type of resource
     * @param quantity Resource amount to add
     */
    public void addResource(Resource res,int quantity){
        int newQuantity;
        newQuantity = resources.getOrDefault(res,0) + quantity;
        resources.put(res,newQuantity);
    }

    /**
     * Getter of the amount of resource contained in the strongbox
     * @param res Type of resource
     * @return Amount of resource contained in the strongbox
     */
    public int getResource(Resource res){
        return resources.getOrDefault(res,0);
    }

    /**
     * Removes an amount of resource from the strongbox
     * @param res Type of resource
     * @param quantity Resource amount to remove
     * @throws invalidStrongBoxRemoveException In case the quantity to remove is greater than the resource amount stored
     */
    public void removeResource(Resource res,int quantity) throws invalidStrongBoxRemoveException {
        int newQuantity;
        newQuantity = resources.getOrDefault(res,0) - quantity;
        if(newQuantity<0){
            throw new invalidStrongBoxRemoveException();
        }
        resources.put(res,newQuantity);
    }

    /**
     * Computes the total amount of resource contained in the strongbox
     * @return Total amount of resources contained in the strongbox
     */
    public int getGenericResourceNumber(){
        int coinTot = getResource(Resource.COIN);
        int shieldTot = getResource(Resource.SHIELD);
        int servantTot = getResource(Resource.SERVANT);
        int stoneTot = getResource(Resource.STONE);

        return coinTot + servantTot + shieldTot + stoneTot;
    }
}
