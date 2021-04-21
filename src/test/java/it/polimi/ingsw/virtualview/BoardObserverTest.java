package it.polimi.ingsw.virtualview;

import it.polimi.ingsw.controller.Player;
import it.polimi.ingsw.exceptions.addResourceLimitExceededException;
import it.polimi.ingsw.exceptions.duplicatedWarehouseTypeException;
import it.polimi.ingsw.exceptions.invalidResourceTypeException;
import it.polimi.ingsw.exceptions.invalidSwapException;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.games.SoloGame;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardObserverTest {

    @Test
    public void testToString() throws addResourceLimitExceededException, invalidResourceTypeException, duplicatedWarehouseTypeException, invalidSwapException {
        Player p1 = new Player("localhost",80,"tommy");
        SoloGame solo = new SoloGame(p1);
        System.out.println(p1.getBoardObserver().toString());

        p1.getBoard().setInkwell();
        System.out.println(p1.getBoardObserver().toString());

        p1.getBoard().addWarehouseDepotResource(Resource.STONE,1,0);
        System.out.println(p1.getBoardObserver().toString());

        p1.getBoard().swapDepot(0,1);
        System.out.println(p1.getBoardObserver().toString());

    }
}