package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.Arrays;

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

    public void addWarehouseDepotResource(int warehouseDepotIndex, Resource res, int quantity) throws invalidDepotTypeChangeException,
            invalidResourceTypeException,addResourceLimitExceededException,duplicatedWarehouseTypeException{

        invalidResourceCheck(res);

        if(storages[warehouseDepotIndex].getResourceType() == null){
            if(Arrays.stream(storages).anyMatch(s->s.getResourceType() == res)) {
                throw new duplicatedWarehouseTypeException();
            }
            storages[warehouseDepotIndex].setResourceType(res);

        }
        storages[warehouseDepotIndex].addResource(res,quantity);

    }

    public void removeWarehouseDepotResource(int warehouseDepotIndex, Resource res, int quantity) throws invalidResourceTypeException,
            removeResourceLimitExceededException{

        invalidResourceCheck(res);

        storages[warehouseDepotIndex].removeResource(res,quantity);
    }

    public void addLeaderDepotResource(int leaderDepotIndex, Resource res, int quantity) throws invalidResourceTypeException,
            addResourceLimitExceededException, IndexOutOfBoundsException{

        invalidResourceCheck(res);

        leaderStorages.get(leaderDepotIndex).addResource(res,quantity);

    }

    public void removeLeaderDepotResource(int leaderDepotIndex, Resource res, int quantity) throws invalidResourceTypeException,
            removeResourceLimitExceededException, IndexOutOfBoundsException{

        invalidResourceCheck(res);

        leaderStorages.get(leaderDepotIndex).removeResource(res,quantity);
    }



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

    public void createLeaderDepot(Resource res){
        leaderStorages.add(new LeaderDepot(res));
    }


    /**
     * Checks if the resource type is allowed to be stored in a depot
     * @param resource resource type to check
     * @throws invalidResourceTypeException thrown if the resource type is illegal
     */
     private void invalidResourceCheck(Resource resource) throws invalidResourceTypeException {
        if( resource == Resource.FAITH || resource == Resource.WHITE){
            throw new invalidResourceTypeException();
        }
    }

    public int getGenericResourceNumber(){
         int coinTot = getTotalWarehouseQuantity(Resource.COIN);
         int shieldTot = getTotalWarehouseQuantity(Resource.SHIELD);
         int servantTot = getTotalWarehouseQuantity(Resource.SERVANT);
         int stoneTot = getTotalWarehouseQuantity(Resource.STONE);

         return coinTot + servantTot + shieldTot + stoneTot;
    }
}
