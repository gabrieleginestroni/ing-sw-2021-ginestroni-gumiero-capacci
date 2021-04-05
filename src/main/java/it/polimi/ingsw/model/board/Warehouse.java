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

    public void addResource(Depot depot, Resource res, int quantity) throws invalidDepotTypeChangeException,
            invalidResourceTypeException,addResourceLimitExceededException,duplicatedWarehouseTypeException{

        invalidResourceCheck(res);

        if(depot.getResourceType() == null){
            if(Arrays.stream(storages).anyMatch(s->s.getResourceType() == res)) {
                throw new duplicatedWarehouseTypeException();
            }
            depot.setResourceType(res);

        }
        depot.addResource(res,quantity);

    }

    public void removeResource(Depot depot, Resource res, int quantity) throws invalidResourceTypeException,
            removeResourceLimitExceededException{

        invalidResourceCheck(res);

        depot.removeResource(res,quantity);
    }

    public void swapDepot(WarehouseDepot depot1,WarehouseDepot depot2) throws invalidSwapException{
        if(depot1.storageQuantity <= depot2.storageLimit
                && depot2.storageQuantity <= depot1.storageQuantity){
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
                     invalidDepotTypeChangeException | addResourceLimitExceededException e)
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
}
