package it.polimi.ingsw.virtualview;

import it.polimi.ingsw.controller.Player;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.games.SoloGame;
import it.polimi.ingsw.exceptions.emptyDevCardGridSlotSelectedException;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BoardObserverTest {


    @Test
    public void testProductions() throws emptyDevCardGridSlotSelectedException, developmentCardSlotLimitExceededException, invalidDevelopmentCardLevelPlacementException, invalidStrongBoxRemoveException, addResourceLimitExceededException, invalidResourceTypeException, duplicatedWarehouseTypeException, removeResourceLimitExceededException {
        Player p1 = new Player("localhost",80,"giagum");
        SoloGame solo = new SoloGame(p1);
        Board b1 = p1.getBoard();
        BoardObserver ob1 = p1.getBoardObserver();
        System.out.println(ob1.toString());

        //Adding required resources(cost) to Strongbox
        DevelopmentCard d1 = solo.getCardFromGrid(0, 0);
        Map<Resource, Integer> cost = d1.getCost();
        for(Map.Entry<Resource, Integer> resCost : cost.entrySet()){
            b1.addStrongboxResource(resCost.getKey(), resCost.getValue());
            if(b1.getResourceNumber(resCost.getKey()) < resCost.getValue())
                assertFalse(true);
        }
        assertFalse(false);
        System.out.println(ob1.toString());

        //Removing required resources(cost) from Strongbox
        for(Map.Entry<Resource, Integer> resCost : cost.entrySet()){
            b1.removeStrongboxResource(resCost.getKey(), resCost.getValue());
        }

        //Card purchase
        b1.addDevelopmentCard(d1, 2);
        solo.removeCardFromGrid(0, 0);
        System.out.println(ob1.toString());


        //Adding required resources(productionInput) to Warehouse
        DevelopmentCard devToProduce = b1.getTopCard(2);
        Map<Resource, Integer> devProdIn = devToProduce.getProductionInput();
        for(Map.Entry<Resource, Integer> resProdIn : devProdIn.entrySet()){
            int index = resProdIn.getValue()-1;
            while(b1.getWarehouseDepotResourceNumber(index) > 0)
                index++;
            b1.addWarehouseDepotResource(resProdIn.getKey(), resProdIn.getValue(), index);
        }
        System.out.println(ob1.toString());

        //Removing required resources(productionInput) from Warehouse
        for(Map.Entry<Resource, Integer> resProdIn : devProdIn.entrySet()){
            Resource t1 = b1.getWarehouseDepotResourceType(0);
            Resource t2 = b1.getWarehouseDepotResourceType(1);
            Resource t3 = b1.getWarehouseDepotResourceType(2);
            int index = (t1 == resProdIn.getKey() ? 0 : t2 == resProdIn.getKey() ? 1 : 2);
            b1.removeWarehouseDepotResource(resProdIn.getKey(), resProdIn.getValue(), index);
        }
        System.out.println(ob1.toString());

        //Adding given resources(productionOutput) to Strongbox
        Map<Resource, Integer> devProdOut = devToProduce.getProductionOutput();
        for(Map.Entry<Resource, Integer> resProdOut : devProdOut.entrySet()){
            b1.addStrongboxResource(resProdOut.getKey(), resProdOut.getValue());
        }
        System.out.println(ob1.toString());
    }

    @Test
    public void testToString() throws addResourceLimitExceededException, invalidResourceTypeException, duplicatedWarehouseTypeException, invalidSwapException, emptyDevCardGridSlotSelectedException, developmentCardSlotLimitExceededException, invalidDevelopmentCardLevelPlacementException, removeResourceLimitExceededException {
        Player p1 = new Player("localhost",80,"tommy");
        SoloGame solo = new SoloGame(p1);
        Board b1 = p1.getBoard();
        BoardObserver ob1 = p1.getBoardObserver();
        System.out.println(ob1.toString());

        b1.setInkwell();
        System.out.println(ob1.toString());

        b1.addWarehouseDepotResource(Resource.STONE,1,0);
        System.out.println(ob1.toString());

        b1.swapDepot(0,1);
        System.out.println(ob1.toString());

        b1.addDevelopmentCard(solo.getCardFromGrid(0, 0), 0);
        System.out.println(ob1.toString());

        b1.giveFaithPoints(3);
        b1.computeActivationPopeTile(0);
        System.out.println(ob1.toString());
        b1.giveFaithPoints(1);
        b1.computeActivationPopeTile(0);
        System.out.println(ob1.toString());
        b1.removeWarehouseDepotResource(Resource.STONE, 1, 1);
        System.out.println(ob1.toString());
        b1.addWarehouseDepotResource(Resource.COIN, 2, 1);
        System.out.println(ob1.toString());

        List<LeaderCard> l = solo.get4LeaderCards();
        b1.addLeaderCard(l.get(0));
        System.out.println(ob1.toString());
        b1.activateLeaderCard(0);
        System.out.println(ob1.toString());

        b1.addLeaderCard(l.get(1));
        System.out.println(ob1.toString());
        b1.discardLeaderCard(0);
        System.out.println(ob1.toString());

        b1.computeActivationPopeTile(0);
        System.out.println(ob1.toString());

        solo.addFaithLorenzo(16);
        b1.giveFaithPoints(7);
        b1.computeActivationPopeTile(1);
        System.out.println(ob1.toString());

    }
}