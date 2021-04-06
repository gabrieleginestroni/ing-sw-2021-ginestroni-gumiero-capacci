package it.polimi.ingsw.model.board;

import it.polimi.ingsw.controller.ControllerPlayer;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.games.MultiplayerGame;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void TestAddLeaderCard() {
        Board b = new Board();
        ControllerPlayer controllerPlayer1 = new ControllerPlayer( "localhost", 8080, "giagum");
        ControllerPlayer controllerPlayer2 = new ControllerPlayer( "localhost", 8080, "gabry");
        List<ControllerPlayer> controllerPlayer = new ArrayList<>();
        controllerPlayer.add(controllerPlayer1);
        controllerPlayer.add(controllerPlayer2);
        MultiplayerGame multiplayerGame = new MultiplayerGame(controllerPlayer);
        LeaderCard l = multiplayerGame.get4LeaderCards().get(0);
        b.addLeaderCard(l);
        assertEquals(l, b.getHand().get(0));
    }

    @Test
    public void TestAddDevelopmentCard() throws developmentCardSlotLimitExceededException, invalidDevelopmentCardLevelPlacementException {
        Board b = new Board();
        ControllerPlayer controllerPlayer1 = new ControllerPlayer( "localhost", 8080, "giagum");
        ControllerPlayer controllerPlayer2 = new ControllerPlayer( "localhost", 8080, "gabry");
        List<ControllerPlayer>  controllerPlayer = new ArrayList<>();
        controllerPlayer.add(controllerPlayer1);
        controllerPlayer.add(controllerPlayer2);
        MultiplayerGame multiplayerGame = new MultiplayerGame(controllerPlayer);
        b.addDevelopmentCard(multiplayerGame.getCardFromGrid(0,1), b.getCardSlot()[0]);
        assertEquals(1, b.getCardNumber(1, Color.BLUE));
    }

    @Test
    public void TestGetWarehouseResource(){

    }

    @Test
    public void TestGetStrongBoxResource() {
    }

    @Test
    public void TestAddLeaderDepot() {
    }



    @Test
    public void TestGetFaithPoints() {
    }

    @Test
    public void TestGiveFaithPoints() {
    }

    @Test
    public void TestGetCardNumber() {
    }

    @Test
    public void TestAddDepotResource() {
    }

    @Test
    public void TestRemoveDepotResource() {
    }

    @Test
    public void TestAddStrongboxResource() {
        Board b = new Board();
       b.addStrongboxResource( Resource.SERVANT, 2);
        assertEquals(2, b.getStrongBoxResource(Resource.SERVANT));
        b.addStrongboxResource( Resource.SERVANT, 2);
        assertEquals(4, b.getStrongBoxResource(Resource.SERVANT));
    }

    @Test
    public void TestRemoveStrongboxResource() throws invalidStrongBoxRemoveException {
        Board b = new Board();
        b.addStrongboxResource( Resource.SERVANT, 2);
        b.removeStrongboxResource( Resource.SERVANT, 2);
        assertEquals(0, b.getStrongBoxResource(Resource.SERVANT));
    }

    @Test
    public void TestDiscardLeaderCard() {
    }


    @Test
    public void TestGetWhiteMarbles() {
    }

    @Test
    public void TestGetResourceNumber() {
    }

    @Test
    public void TestGetWhiteMarble() {
    }

    @Test
    public void TestGetDiscount() {
    }

    @Test
    public void Test2LeaderCardActivation() throws invalidDepotTypeChangeException, duplicatedWarehouseTypeException, addResourceLimitExceededException, invalidResourceTypeException {
        Board b = new Board();
        ControllerPlayer controllerPlayer1 = new ControllerPlayer("localhost", 8080, "giagum");
        ControllerPlayer controllerPlayer2 = new ControllerPlayer("localhost", 8080, "gabry");
        List<ControllerPlayer> controllerPlayer = new ArrayList<>();
        controllerPlayer.add(controllerPlayer1);
        controllerPlayer.add(controllerPlayer2);
        MultiplayerGame multiplayerGame = new MultiplayerGame(controllerPlayer);

        List<LeaderCard> list = multiplayerGame.get4LeaderCards();
        LeaderCard l = list.get(0);
        b.addLeaderCard(l);
        l.activateCard();
        System.out.println(l.getPower() + " " + l.getResource());
        switch (l.getPower()) {
            case "depots":
                b.addDepotResource(b.getWareHouse().getLeaderStorages().get(0), l.getResource(), 2);
                assertEquals(2, b.getWarehouseResource(l.getResource()));
                assertEquals(2, b.getResourceNumber(l.getResource()));
                break;
            case "discount":
                assertEquals(l.getResource(), b.getDiscount().get(b.getDiscount().size()-1));
                break;
            case "whiteMarble":
                assertEquals(l.getResource(), b.getWhiteMarbles().get(0));
                break;
            default: //"production"
                assertTrue(l.isActive());
        }
        l = list.get(1);
        b.addLeaderCard(l);
        l.activateCard();
        System.out.println(l.getPower() + " " + l.getResource());
        switch (l.getPower()) {
            case "depots":
                b.addDepotResource(b.getWareHouse().getLeaderStorages().get(b.getWareHouse().getLeaderStorages().size()-1), l.getResource(), 2);
                assertEquals(2, b.getWarehouseResource(l.getResource()));
                assertEquals(2, b.getResourceNumber(l.getResource()));
                break;
            case "discount":
                assertEquals(l.getResource(), b.getDiscount().get(b.getDiscount().size()-1));
                break;
            case "whiteMarble":
                assertEquals(l.getResource(), b.getWhiteMarbles().get(b.getWhiteMarbles().size()-1));
                break;
            default: //"production"
                assertTrue(l.isActive());

        }
    }

    @Test
    public void Test2DevelopmentCard() throws developmentCardSlotLimitExceededException, invalidDevelopmentCardLevelPlacementException {
        Board b = new Board();
        ControllerPlayer controllerPlayer1 = new ControllerPlayer("localhost", 8080, "giagum");
        ControllerPlayer controllerPlayer2 = new ControllerPlayer("localhost", 8080, "gabry");
        List<ControllerPlayer> controllerPlayer = new ArrayList<>();
        controllerPlayer.add(controllerPlayer1);
        controllerPlayer.add(controllerPlayer2);
        MultiplayerGame multiplayerGame = new MultiplayerGame(controllerPlayer);

        DevelopmentCard c = multiplayerGame.getCardFromGrid(0,0);
        b.addDevelopmentCard(c,b.getCardSlot()[0]);
        assertEquals(1,b.getCardNumber(1, Color.GREEN));
        c = multiplayerGame.getCardFromGrid(1,3);
        b.addDevelopmentCard(c,b.getCardSlot()[0]);
        assertEquals(1,b.getCardNumber(2,Color.PURPLE));

        c = multiplayerGame.getCardFromGrid(2,0);
        b.addDevelopmentCard(c,b.getCardSlot()[0]);
        assertEquals(1,b.getCardNumber(3, Color.GREEN));
        assertEquals(2,b.getCardNumber(1, Color.GREEN));

        c = multiplayerGame.getCardFromGrid(0,2);
        b.addDevelopmentCard(c,b.getCardSlot()[1]);
        assertEquals(1,b.getCardNumber(3, Color.GREEN));
        assertEquals(2,b.getCardNumber(1, Color.GREEN));
        assertEquals(1,b.getCardNumber(1, Color.YELLOW));




    }
}
