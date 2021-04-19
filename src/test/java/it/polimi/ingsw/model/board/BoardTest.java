package it.polimi.ingsw.model.board;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.Player;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.games.MultiplayerGame;
import it.polimi.ingsw.model.games.SoloGame;
import it.polimi.ingsw.model.games.emptyDevCardGridSlotSelected;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void TestAddLeaderCard() {
        Player player1 = new Player( "localhost", 8080, "giagum");
        Player player2 = new Player( "localhost", 8080, "gabry");
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        MultiplayerGame multiplayerGame = new MultiplayerGame(player);
        Board b = player1.getBoard();

        LeaderCard l = multiplayerGame.get4LeaderCards().get(0);
        b.addLeaderCard(l);
        assertEquals(l, b.getHand().get(0));
    }

    @Test
    public void TestAddDevelopmentCard() throws developmentCardSlotLimitExceededException, invalidDevelopmentCardLevelPlacementException, emptyDevCardGridSlotSelected {
        Player player1 = new Player( "localhost", 8080, "giagum");
        Player player2 = new Player( "localhost", 8080, "gabry");
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        MultiplayerGame multiplayerGame = new MultiplayerGame(player);
        Board b = player1.getBoard();

        b.addDevelopmentCard(multiplayerGame.getCardFromGrid(0,1), 0);
        assertEquals(1, b.getCardNumber(1, Color.BLUE));
    }

    @Test
    public void TestRemoveDepotResource() throws invalidDepotTypeChangeException,
            duplicatedWarehouseTypeException, addResourceLimitExceededException, invalidResourceTypeException, removeResourceLimitExceededException {
        Player player1 = new Player( "localhost", 8080, "giagum");
        Player player2 = new Player( "localhost", 8080, "gabry");
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        MultiplayerGame multiplayerGame = new MultiplayerGame(player);
        Board b = player1.getBoard();

        List<LeaderCard> list = multiplayerGame.get4LeaderCards();
        LeaderCard l = list.get(0);
        b.addLeaderCard(l);
        l.activateCard();
        //System.out.println(l.getPower() + " " + l.getResource());
        switch (l.getPower()) {
            case "depots":
                b.addWarehouseDepotResource(0, l.getResource(), 2);
                assertEquals(2, b.getWarehouseResource(l.getResource()));
                assertEquals(2, b.getResourceNumber(l.getResource()));
                b.removeWarehouseDepotResource(0,l.getResource(),1);
                assertEquals(1,b.getResourceNumber(l.getResource()));
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
    }

    @Test
    public void TestAddStrongboxResource() {
        Player player1 = new Player( "localhost", 8080, "giagum");
        Player player2 = new Player( "localhost", 8080, "gabry");
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        MultiplayerGame multiplayerGame = new MultiplayerGame(player);
        Board b = player1.getBoard();

        b.addStrongboxResource( Resource.SERVANT, 2);
        assertEquals(2, b.getStrongBoxResource(Resource.SERVANT));
        b.addStrongboxResource( Resource.SERVANT, 2);
        assertEquals(4, b.getStrongBoxResource(Resource.SERVANT));
    }

    @Test
    public void TestRemoveStrongboxResource() throws invalidStrongBoxRemoveException {
        Player player1 = new Player( "localhost", 8080, "giagum");
        Player player2 = new Player( "localhost", 8080, "gabry");
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        MultiplayerGame multiplayerGame = new MultiplayerGame(player);
        Board b = player1.getBoard();

        b.addStrongboxResource( Resource.SERVANT, 2);
        b.removeStrongboxResource( Resource.SERVANT, 2);
        assertEquals(0, b.getStrongBoxResource(Resource.SERVANT));
    }

    @Test
    public void Test2LeaderCardsActivation() throws invalidDepotTypeChangeException, duplicatedWarehouseTypeException, addResourceLimitExceededException, invalidResourceTypeException {
        Player player1 = new Player( "localhost", 8080, "giagum");
        Player player2 = new Player( "localhost", 8080, "gabry");
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        MultiplayerGame multiplayerGame = new MultiplayerGame(player);
        Board b = player1.getBoard();

        List<LeaderCard> list = multiplayerGame.get4LeaderCards();
        LeaderCard l = list.get(0);
        b.addLeaderCard(l);
        l.activateCard();
        //System.out.println(l.getPower() + " " + l.getResource());
        switch (l.getPower()) {
            case "depots":
                b.addLeaderDepotResource(0, l.getResource(), 2);
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
        //System.out.println(l.getPower() + " " + l.getResource());
        switch (l.getPower()) {
            case "depots":
                b.addLeaderDepotResource(b.getWareHouse().getLeaderStorages().size()-1, l.getResource(), 2);
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
    public void Test2DevelopmentCard() throws developmentCardSlotLimitExceededException, invalidDevelopmentCardLevelPlacementException, emptyDevCardGridSlotSelected {
        Player player1 = new Player( "localhost", 8080, "giagum");
        Player player2 = new Player( "localhost", 8080, "gabry");
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        MultiplayerGame multiplayerGame = new MultiplayerGame(player);
        Board b = player1.getBoard();

        DevelopmentCard c = multiplayerGame.getCardFromGrid(0,0);
        b.addDevelopmentCard(c,0);
        assertEquals(1,b.getCardNumber(1, Color.GREEN));
        c = multiplayerGame.getCardFromGrid(1,3);
        b.addDevelopmentCard(c,0);
        assertEquals(1,b.getCardNumber(2,Color.PURPLE));

        c = multiplayerGame.getCardFromGrid(2,0);
        b.addDevelopmentCard(c,0);
        assertEquals(c,b.getCardSlot()[0].getTopCard());
        assertEquals(1,b.getCardNumber(3, Color.GREEN));
        assertEquals(2,b.getCardNumber(1, Color.GREEN));

        c = multiplayerGame.getCardFromGrid(0,2);
        b.addDevelopmentCard(c,1);
        assertEquals(1,b.getCardNumber(3, Color.GREEN));
        assertEquals(2,b.getCardNumber(1, Color.GREEN));
        assertEquals(1,b.getCardNumber(1, Color.YELLOW));
    }

    @Test
    public void GenericTestWith2LeaderActivation() throws IOException, invalidDepotTypeChangeException,
            duplicatedWarehouseTypeException, addResourceLimitExceededException, invalidResourceTypeException, invalidSwapException {
        Player player1 = new Player( "localhost", 8080, "giagum");
        Player player2 = new Player( "localhost", 8080, "gabry");
        List<Player> player = new ArrayList<>();
        player.add(player1);
        MultiplayerGame multiplayerGame = new MultiplayerGame(player);
        Board b1 = player1.getBoard();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/LeaderCards.json"));
        Gson gson = new Gson();
        LeaderCard[] tempArray = gson.fromJson(reader, LeaderCard[].class);
        //0-3 discount
        //4-7 depot
        //8-11 marble
        //12-15 production
        b1.addLeaderCard(tempArray[4]);
        b1.addLeaderCard(tempArray[5]);
        b1.activateLeaderCard(0);
        b1.activateLeaderCard(0);
        //tempArray[4].activateCard();
        //tempArray[5].activateCard();

        //leaderDepots
        b1.addLeaderDepotResource(0, tempArray[4].getResource(), 1);
        b1.addLeaderDepotResource(1, tempArray[5].getResource(), 2);
        assertEquals(1, b1.getResourceNumber(tempArray[4].getResource()));
        assertEquals(2, b1.getResourceNumber(tempArray[5].getResource()));
        assertEquals(0, b1.getResourceNumber(Resource.COIN));
        assertEquals(0, b1.getResourceNumber(Resource.SHIELD));

        //normal depots
        WarehouseDepot w1 = b1.getWareHouse().getStorages()[0];
        WarehouseDepot w2 = b1.getWareHouse().getStorages()[1];
        WarehouseDepot w3 = b1.getWareHouse().getStorages()[2];
        b1.addWarehouseDepotResource(0, Resource.STONE, 1);

        assertEquals(2, b1.getResourceNumber(tempArray[4].getResource()));
        assertEquals(Resource.STONE, w1.getResourceType());
        assertNull(w2.getResourceType());
        assertNull(w3.getResourceType());

        b1.swapDepot(0, 2);

        assertNull(w1.getResourceType());
        assertNull(w2.getResourceType());
        assertEquals(Resource.STONE, w3.getResourceType());

        b1.addStrongboxResource(Resource.SERVANT, 2);
        b1.addStrongboxResource(Resource.COIN, 2);
        b1.addStrongboxResource(Resource.STONE, 2);
        b1.addStrongboxResource(Resource.SHIELD, 2);

        assertEquals(8, b1.computeVictoryPoints());
        assertEquals(4, b1.getWareHouse().getGenericResourceNumber());
    }

    @Test
    public void GenericTestWithLeaderDiscard() throws IOException, addResourceLimitExceededException, invalidResourceTypeException, invalidSwapException, duplicatedWarehouseTypeException {
        Player player1 = new Player( "localhost", 8080, "giagum");
        List<Player> player = new ArrayList<>();
        player.add(player1);
        MultiplayerGame multiplayerGame = new MultiplayerGame(player);
        Board b1 = player1.getBoard();
        Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/LeaderCards.json"));
        Gson gson = new Gson();
        LeaderCard[] tempArray = gson.fromJson(reader, LeaderCard[].class);
        //0-3 discount
        //4-7 depot
        //8-11 marble
        //12-15 production
        b1.addLeaderCard(tempArray[4]);
        b1.addLeaderCard(tempArray[5]);
        b1.activateLeaderCard(0);
        //tempArray[5].discardCard();

        //leaderDepots
        b1.addLeaderDepotResource(0, tempArray[4].getResource(), 1);
        assertEquals(1, b1.getLeaderDepotResourceNumber(0));
        assertEquals(1, b1.getResourceNumber(tempArray[4].getResource()));
        assertEquals(0, b1.getResourceNumber(Resource.COIN));

        //normal depots
        b1.addWarehouseDepotResource(0, Resource.STONE, 1);

        assertEquals(2, b1.getResourceNumber(tempArray[4].getResource()));
        assertEquals(Resource.STONE, b1.getWarehouseDepotResourceType(0));
        assertNull(b1.getWarehouseDepotResourceType(1));
        assertNull(b1.getWarehouseDepotResourceType(2));

        b1.swapDepot(0, 2);

        assertNull(b1.getWarehouseDepotResourceType(0));
        assertNull(b1.getWarehouseDepotResourceType(1));
        assertEquals(Resource.STONE, b1.getWarehouseDepotResourceType(2));

        b1.addStrongboxResource(Resource.SERVANT, 2);
        b1.addStrongboxResource(Resource.COIN, 2);
        b1.addStrongboxResource(Resource.STONE, 2);
        b1.addStrongboxResource(Resource.SHIELD, 2);

        assertEquals(5, b1.computeVictoryPoints());
        assertEquals(2, b1.getWareHouse().getGenericResourceNumber());

        b1.discardLeaderCard(0);
        assertEquals(5, b1.computeVictoryPoints());
        b1.giveFaithPoints(2);
        assertEquals(6, b1.computeVictoryPoints());
        b1.giveFaithPoints(5);
        assertEquals(7, b1.computeVictoryPoints());
        assertTrue(multiplayerGame.isSection1Reported());
        b1.giveFaithPoints(8);
        assertEquals(14, b1.computeVictoryPoints());
        assertTrue(multiplayerGame.isSection2Reported());
        b1.giveFaithPoints(8);
        assertEquals(25, b1.computeVictoryPoints());
        assertTrue(multiplayerGame.isSection3Reported());
        assertTrue(multiplayerGame.isGameOver());
    }

    @Test
    public void DevCardGameOverTest() throws emptyDevCardGridSlotSelected, developmentCardSlotLimitExceededException, invalidDevelopmentCardLevelPlacementException {
        Player player1 = new Player( "localhost", 8080, "giagum");
        SoloGame solo = new SoloGame(player1);
        Board b1 = player1.getBoard();

        b1.addDevelopmentCard(solo.getCardFromGrid(0, 0), 0);
        assertFalse();

    }
}
