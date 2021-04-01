package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

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

    public void addResource(Depot depot, Resource res,int quantity) throws invalidDepotTypeChangeException,
            invalidResourceTypeException,addResourceLimitExceededException{

        invalidResourceCheck(res);

        if(depot.getResourceType() == null) depot.setResourceType(res);
        depot.addResource(res,quantity);

    }

    public void removeResource(Depot depot, Resource res, int quantity) throws invalidResourceTypeException,
            removeResourceLimitExceededException{

        invalidResourceCheck(res);

        depot.removeResource(res,quantity);
    }




    /**
     * Checks if the resource type is allowed to be stored in a depot
     * @param resource resource type to check
     * @throws invalidResourceTypeException thrown if the resource type is illegal
     */
    void invalidResourceCheck(Resource resource) throws invalidResourceTypeException {
        if( resource == Resource.FAITH || resource == Resource.WHITE){
            throw new invalidResourceTypeException();
        }
    }
}
