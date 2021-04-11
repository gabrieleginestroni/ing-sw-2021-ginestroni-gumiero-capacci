package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Arrays;

/**@author Gabriele Ginestroni
 * Class that represents the board's resource warehouse. Contains warehouse depots and storage leader cards' depots.
 */
public class Warehouse {
    private final WarehouseDepot[] storages;
    private final ArrayList<LeaderDepot> leaderStorages;

    public Warehouse() {
        this.storages = new WarehouseDepot[3];
        storages[0] = new WarehouseDepot(1);
        storages[1] = new WarehouseDepot(2);
        storages[2] = new WarehouseDepot(3);

        this.leaderStorages = new ArrayList<>();
    }

    //TODO
    //REMOVE
    public ArrayList<LeaderDepot> getLeaderStorages() {
        return leaderStorages;
    }

    //TODO
    //REMOVE
    public WarehouseDepot[] getStorages() {
        return storages;
    }


    /**
     * Adds an amount of the specified type resource to the warehouse depot that corresponds to the index.
     * @param warehouseDepotIndex Index of the warehouse depot between 0 and 2
     * @param res Resource type to add
     * @param quantity Quantity of resource to add
     *
     * @throws invalidResourceTypeException In case the resource type doesn't match the one of the depot
     * @throws addResourceLimitExceededException In case new quantity exceeds the storage limit
     * @throws duplicatedWarehouseTypeException  In case the add resource action tries to set a duplicated warehouse
     * depot type
     */
    public void addWarehouseDepotResource(int warehouseDepotIndex, Resource res, int quantity) throws
            invalidResourceTypeException,addResourceLimitExceededException,duplicatedWarehouseTypeException{


        if(storages[warehouseDepotIndex].getResourceType() == null){
            if(Arrays.stream(storages).anyMatch(s->s.getResourceType() == res)) {
                throw new duplicatedWarehouseTypeException();
            }
            storages[warehouseDepotIndex].setResourceType(res);

        }
        storages[warehouseDepotIndex].addResource(res,quantity);

    }

    /**
     * Removes an amount of the specified type resource to the warehouse depot that corresponds to the index.
     * Sets the resource type to null if the new quantity is zero.
     * @param warehouseDepotIndex Index of the warehouse depot between 0 and 2
     * @param res Resource type to remove
     * @param quantity Quantity of resource to remove
     * @throws invalidResourceTypeException In case the resource type doesn't match the one of the depot
     * @throws removeResourceLimitExceededException In case the quantity to remove is greater than the actual
     * stored quantity
     */
    public void removeWarehouseDepotResource(int warehouseDepotIndex, Resource res, int quantity) throws invalidResourceTypeException,
            removeResourceLimitExceededException{



        storages[warehouseDepotIndex].removeResource(res,quantity);
    }

    /**
     * Adds an amount of the specified type resource to the leader depot that corresponds to the index.
     * @param leaderDepotIndex Index of the leader depot between 0 and 1
     * @param res Resource type to add
     * @param quantity Quantity of resource to add
     * @throws invalidResourceTypeException In case the resource type doesn't match the one of the depot
     * @throws addResourceLimitExceededException  In case new quantity exceeds the storage limit
     * @throws IndexOutOfBoundsException In case a leader depot with the specified index doesn't exist
     */
    public void addLeaderDepotResource(int leaderDepotIndex, Resource res, int quantity) throws invalidResourceTypeException,
            addResourceLimitExceededException, IndexOutOfBoundsException{



        leaderStorages.get(leaderDepotIndex).addResource(res,quantity);

    }

    /**
     *Removes an amount of the specified type resource to the leader depot that corresponds to the index.
     * @param leaderDepotIndex Index of the leader depot between 0 and 1
     * @param res Resource type to remove
     * @param quantity Quantity of resource to remove
     * @throws invalidResourceTypeException In case the resource type doesn't match the one of the depot
     * @throws removeResourceLimitExceededException In case the quantity to remove is greater than the actual
     * @throws IndexOutOfBoundsException In case a leader depot with the specified index doesn't exist
     */
    public void removeLeaderDepotResource(int leaderDepotIndex, Resource res, int quantity) throws invalidResourceTypeException,
            removeResourceLimitExceededException, IndexOutOfBoundsException{


        leaderStorages.get(leaderDepotIndex).removeResource(res,quantity);
    }

    /**
     *Swaps two warehouse depot's resources if the storages limits permit the action and changes the resource
     * types accordingly.
     * @param warehouseDepot1Index Index of the first warehouse depot
     * @param warehouseDepot2Index Index of the seconds warehouse depot
     * @throws invalidSwapException In case the swap isn't allow by the game rules
     */

    public void swapDepot(int warehouseDepot1Index,int warehouseDepot2Index) throws invalidSwapException{
        WarehouseDepot depot1 = storages[warehouseDepot1Index];
        WarehouseDepot depot2 = storages[warehouseDepot2Index];
        if(depot1.storageQuantity <= depot2.storageLimit
                && depot2.storageQuantity <= depot1.storageLimit){
            Resource res1 = depot1.resourceType;
            int quantity1 = depot1.storageQuantity;

            Resource res2 = depot2.resourceType;
            int quantity2 = depot2.storageQuantity;

            try {
                depot1.removeResource(res1, quantity1);
                depot2.removeResource(res2, quantity2);
                depot1.setResourceType(res2);
                depot1.addResource(res2, quantity2);
                depot2.setResourceType(res1);
                depot2.addResource(res1, quantity1);
            } catch (removeResourceLimitExceededException | invalidResourceTypeException |
                      addResourceLimitExceededException e)
            { throw new invalidSwapException();}

        } else throw new invalidSwapException();

    }

    /**
     *Returns the total quantity of the resource contained in all warehouse and leader depots
     * @param res Resource type
     * @return Total amount of the resource contained in all warehouse and leader depots
     */

    public int getTotalWarehouseQuantity(Resource res){
        int totalWarehouse;
        int totalLeader;
        totalWarehouse = Arrays.stream(storages)
                .filter(s->s.getResourceType() == res)
                .mapToInt(WarehouseDepot::getResourceQuantity)
                .sum();
        totalLeader = leaderStorages.stream()
                .filter(l->l.getResourceType() == res)
                .mapToInt(LeaderDepot::getResourceQuantity)
                .sum();
        return totalLeader + totalWarehouse;
    }

    /**
     * Creates an empty leader depot
     * @param res Resource type of the leader depot
     */
    public void createLeaderDepot(Resource res){
        leaderStorages.add(new LeaderDepot(res));
    }



    /**
     *Returns the total quantity of resources contained in all warehouse and leader depots
     * @return Total amount of resources contained in all warehouse and leader depots
     */
    public int getGenericResourceNumber(){
         int coinTot = getTotalWarehouseQuantity(Resource.COIN);
         int shieldTot = getTotalWarehouseQuantity(Resource.SHIELD);
         int servantTot = getTotalWarehouseQuantity(Resource.SERVANT);
         int stoneTot = getTotalWarehouseQuantity(Resource.STONE);

         return coinTot + servantTot + shieldTot + stoneTot;
    }

    /**
     * Getter of a warehouse depot's stored quantity
     * @param index Index of the warehouse depot between 0 and 2
     * @return Resource quantity
     */
    public int getWarehouseDepotResourceNumber(int index){
        return storages[index].getResourceQuantity() ;
    }

    /**
     * Getter of a leader depot's stored quantity
     * @param index Index of the leader depot
     * @return Resource quantity
     */
    public int getLeaderDepotResourceNumber(int index){
         return leaderStorages.get(index).getResourceQuantity();

    }

    /**
     * Getter of a warehouse depot's resource type
     * @param index Index of the warehouse depot between 0 and 2
     * @return Resource type
     */
    public Resource getWarehouseDepotResourceType(int index){
        return storages[index].getResourceType() ;
    }

    /**
     * Getter of a leader depot's resource type
     * @param index Index of the leader depot
     * @return Resource type
     */
    public Resource getLeaderDepotResourceType(int index){
        return leaderStorages.get(index).getResourceType() ;
    }

}
