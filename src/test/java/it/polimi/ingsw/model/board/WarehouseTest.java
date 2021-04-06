package it.polimi.ingsw.model.board;
import it.polimi.ingsw.model.Resource;
import org.junit.Test;

import static org.junit.Assert.*;

public class WarehouseTest {

    @Test
    public void TestAddResourceTest() throws invalidDepotTypeChangeException,
        duplicatedWarehouseTypeException, addResourceLimitExceededException, invalidResourceTypeException {
        Warehouse w = new Warehouse();
        w.addResource(w.getStorages()[0], Resource.COIN, 1);
        assertEquals(Resource.COIN, w.getStorages()[0].getResourceType());
        assertEquals(1, w.getStorages()[0].getResourceQuantity());
        //throws exception
        //w.addResource(w.getStorages()[0], Resource.FAITH, 1);
        //w.addResource(w.getStorages()[0], Resource.STONE, 2);
        w.addResource(w.getStorages()[1], Resource.STONE, 2);
        assertEquals(Resource.STONE, w.getStorages()[1].getResourceType());
        assertEquals(2, w.getStorages()[1].getResourceQuantity());
    }

    @Test
    public void TestRemoveResource() throws invalidResourceTypeException, removeResourceLimitExceededException,
            addResourceLimitExceededException, invalidDepotTypeChangeException, duplicatedWarehouseTypeException {
        Warehouse w = new Warehouse();
        w.addResource(w.getStorages()[0], Resource.COIN, 1);
        w.removeResource(w.getStorages()[0], Resource.COIN, 1);
        assertEquals(w.getStorages()[0].getResourceType(), Resource.COIN);
        assertEquals(0, w.getStorages()[0].getResourceQuantity());
    }

    @Test
    public void TestSwapDepot() throws invalidDepotTypeChangeException, duplicatedWarehouseTypeException,
            addResourceLimitExceededException, invalidResourceTypeException, invalidSwapException {
        Warehouse w = new Warehouse();
        w.addResource(w.getStorages()[0], Resource.COIN, 1);
        w.addResource(w.getStorages()[1], Resource.STONE, 1);
        w.swapDepot(w.getStorages()[0], w.getStorages()[1]);
        assertEquals(Resource.STONE, w.getStorages()[0].getResourceType());
        assertEquals(1, w.getStorages()[0].getResourceQuantity());
        assertEquals(Resource.COIN, w.getStorages()[1].getResourceType());
        assertEquals(1, w.getStorages()[1].getResourceQuantity());
    }

    @Test
    public void TestGetTotalWarehouseQuantity() throws invalidDepotTypeChangeException,
            duplicatedWarehouseTypeException, addResourceLimitExceededException, invalidResourceTypeException {
        Warehouse w = new Warehouse();
        w.addResource(w.getStorages()[0], Resource.COIN, 1);
        w.addResource(w.getStorages()[1], Resource.STONE, 1);
        assertEquals(1, w.getTotalWarehouseQuantity(Resource.COIN));
        assertEquals(1, w.getTotalWarehouseQuantity(Resource.STONE));
        w.addResource(w.getStorages()[1], Resource.STONE, 1);
        assertEquals(2, w.getTotalWarehouseQuantity(Resource.STONE));
        w.createLeaderDepot(Resource.STONE);
        w.addResource(w.getLeaderStorages().get(0), Resource.STONE, 1);
        assertEquals(3, w.getTotalWarehouseQuantity(Resource.STONE));
    }

    @Test
    public void TestCreateLeaderDepot() throws invalidDepotTypeChangeException,
            duplicatedWarehouseTypeException, addResourceLimitExceededException, invalidResourceTypeException {
        Warehouse w = new Warehouse();
        w.createLeaderDepot(Resource.SERVANT);
        w.addResource(w.getLeaderStorages().get(0), Resource.SERVANT, 1);
        assertEquals(1, w.getTotalWarehouseQuantity(Resource.SERVANT));
    }
}