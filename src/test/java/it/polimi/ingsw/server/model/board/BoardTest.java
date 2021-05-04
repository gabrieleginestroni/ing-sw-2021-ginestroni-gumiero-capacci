package it.polimi.ingsw.server.model.board;

import com.google.gson.Gson;
import it.polimi.ingsw.server.controller.Player;
import it.polimi.ingsw.server.exceptions.*;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.Resource;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.games.MultiplayerGame;
import it.polimi.ingsw.server.model.games.SoloGame;
import it.polimi.ingsw.server.virtual_view.VirtualView;
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
        Player player1 = new Player( "giagum",null);
        Player player2 = new Player( "giagum",null);
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        VirtualView vv = new VirtualView();
        MultiplayerGame multiplayerGame = new MultiplayerGame(player,vv);
        Board b = player1.getBoard();

        LeaderCard l = multiplayerGame.get4LeaderCards().get(0);
        b.addLeaderCard(l);
        assertEquals(l, b.getHand().get(0));
    }

    @Test
    public void TestAddDevelopmentCard() throws developmentCardSlotLimitExceededException, invalidDevelopmentCardLevelPlacementException, emptyDevCardGridSlotSelectedException {
        Player player1 = new Player( "giagum",null);
        Player player2 = new Player( "giagum",null);
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        VirtualView vv = new VirtualView();
        MultiplayerGame multiplayerGame = new MultiplayerGame(player,vv);
        Board b = player1.getBoard();

        b.addDevelopmentCard(multiplayerGame.getCardFromGrid(0,1), 0);
        assertEquals(1, b.getCardNumber(1, Color.BLUE));
    }

    @Test
    public void TestRemoveLeaderDepotResource() throws addResourceLimitExceededException, invalidResourceTypeException, removeResourceLimitExceededException {
        Player player1 = new Player( "giagum",null);
        Player player2 = new Player( "giagum",null);
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        VirtualView vv = new VirtualView();
        MultiplayerGame multiplayerGame = new MultiplayerGame(player,vv);
        Board b = player1.getBoard();

        List<LeaderCard> list = multiplayerGame.get4LeaderCards();
        LeaderCard l = list.get(0);
        b.addLeaderCard(l);
        l.activateCard();
        //System.out.println(l.getPower() + " " + l.getResource());
        switch (l.getPower()) {
            case "depots":
                b.addLeaderDepotResource(l.getResource(), 2, 0);
                assertEquals(2, b.getWarehouseResource(l.getResource()));
                assertEquals(2, b.getResourceNumber(l.getResource()));
                b.removeLeaderDepotResource(l.getResource(),1, 0);
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
        Player player1 = new Player( "giagum",null);
        Player player2 = new Player( "giagum",null);
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        VirtualView vv = new VirtualView();
        MultiplayerGame multiplayerGame = new MultiplayerGame(player,vv);
        Board b = player1.getBoard();

        b.addStrongboxResource( Resource.SERVANT, 2);
        assertEquals(2, b.getStrongBoxResource(Resource.SERVANT));
        b.addStrongboxResource( Resource.SERVANT, 2);
        assertEquals(4, b.getStrongBoxResource(Resource.SERVANT));
    }

    @Test
    public void TestRemoveStrongboxResource() throws invalidStrongBoxRemoveException {
        Player player1 = new Player( "giagum",null);
        Player player2 = new Player( "giagum",null);
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        VirtualView vv = new VirtualView();
        MultiplayerGame multiplayerGame = new MultiplayerGame(player,vv);
        Board b = player1.getBoard();

        b.addStrongboxResource( Resource.SERVANT, 2);
        b.removeStrongboxResource( Resource.SERVANT, 2);
        assertEquals(0, b.getStrongBoxResource(Resource.SERVANT));
    }

    @Test
    public void Test2LeaderCardsActivation() throws addResourceLimitExceededException, invalidResourceTypeException {
        Player player1 = new Player( "giagum",null);
        Player player2 = new Player( "giagum",null);
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        VirtualView vv = new VirtualView();
        MultiplayerGame multiplayerGame = new MultiplayerGame(player,vv);
        Board b = player1.getBoard();

        List<LeaderCard> list = multiplayerGame.get4LeaderCards();
        LeaderCard l = list.get(0);
        b.addLeaderCard(l);
        l.activateCard();
        //System.out.println(l.getPower() + " " + l.getResource());
        switch (l.getPower()) {
            case "depots":
                b.addLeaderDepotResource( l.getResource(), 2, 0);
                assertEquals(2, b.getWarehouseResource(l.getResource()));
                assertEquals(l.getResource(), b.getLeaderDepotResourceType(0));
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
                b.addLeaderDepotResource(l.getResource(), 2, b.getWareHouse().getLeaderStorages().size()-1);
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
    public void Test2DevelopmentCard() throws developmentCardSlotLimitExceededException, invalidDevelopmentCardLevelPlacementException, emptyDevCardGridSlotSelectedException {
        Player player1 = new Player( "giagum",null);
        Player player2 = new Player( "giagum",null);
        List<Player> player = new ArrayList<>();
        player.add(player1);
        player.add(player2);
        VirtualView vv = new VirtualView();
        MultiplayerGame multiplayerGame = new MultiplayerGame(player,vv);
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
    public void GenericTestWith2LeaderActivation() throws IOException,
            duplicatedWarehouseTypeException, addResourceLimitExceededException, invalidResourceTypeException, invalidSwapException {
        Player player1 = new Player( "giagum",null);
        Player player2 = new Player( "giagum",null);
        List<Player> player = new ArrayList<>();
        player.add(player1);
        VirtualView vv = new VirtualView();
        MultiplayerGame multiplayerGame = new MultiplayerGame(player,vv);
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
        b1.addLeaderDepotResource(tempArray[4].getResource(), 1, 0);
        b1.addLeaderDepotResource(tempArray[5].getResource(), 2, 1);
        assertEquals(1, b1.getResourceNumber(tempArray[4].getResource()));
        assertEquals(2, b1.getResourceNumber(tempArray[5].getResource()));
        assertEquals(0, b1.getResourceNumber(Resource.COIN));
        assertEquals(0, b1.getResourceNumber(Resource.SHIELD));

        //normal depots
        WarehouseDepot w1 = b1.getWareHouse().getStorages()[0];
        WarehouseDepot w2 = b1.getWareHouse().getStorages()[1];
        WarehouseDepot w3 = b1.getWareHouse().getStorages()[2];
        b1.addWarehouseDepotResource( Resource.STONE, 1, 0);

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
        Player player1 = new Player( "giagum",null);
        List<Player> player = new ArrayList<>();
        player.add(player1);
        VirtualView vv = new VirtualView();
        MultiplayerGame multiplayerGame = new MultiplayerGame(player,vv);
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
        b1.addLeaderDepotResource(tempArray[4].getResource(), 1, 0);
        assertEquals(1, b1.getLeaderDepotResourceNumber(0));
        assertEquals(1, b1.getResourceNumber(tempArray[4].getResource()));
        assertEquals(0, b1.getResourceNumber(Resource.COIN));

        //normal depots
        b1.addWarehouseDepotResource( Resource.STONE, 1, 0);

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
        assertFalse(multiplayerGame.isSection1Reported());
        assertFalse(multiplayerGame.isSection2Reported());
        assertFalse(multiplayerGame.isSection3Reported());
        b1.giveFaithPoints(2);

        assertEquals(6, b1.computeVictoryPoints());
        assertFalse(multiplayerGame.isSection1Reported());
        assertFalse(multiplayerGame.isSection2Reported());
        assertFalse(multiplayerGame.isSection3Reported());
        b1.giveFaithPoints(5);
        b1.computeActivationPopeTile(0);

        // --> 1 report
        assertEquals(9, b1.computeVictoryPoints());
        assertTrue(multiplayerGame.isSection1Reported());
        assertFalse(multiplayerGame.isSection2Reported());
        assertFalse(multiplayerGame.isSection3Reported());
        b1.giveFaithPoints(3);

        //11
        assertEquals(11, b1.computeVictoryPoints());
        assertTrue(multiplayerGame.isSection1Reported());
        assertFalse(multiplayerGame.isSection2Reported());
        assertFalse(multiplayerGame.isSection3Reported());
        b1.giveFaithPoints(3);

        //14
        assertEquals(13, b1.computeVictoryPoints());
        assertTrue(multiplayerGame.isSection1Reported());
        assertFalse(multiplayerGame.isSection2Reported());
        assertFalse(multiplayerGame.isSection3Reported());
        b1.giveFaithPoints(3);
        b1.computeActivationPopeTile(1);

        //17 --> 2 report
        assertEquals(19, b1.computeVictoryPoints());
        assertTrue(multiplayerGame.isSection1Reported());
        assertTrue(multiplayerGame.isSection2Reported());
        assertFalse(multiplayerGame.isSection3Reported());
        b1.giveFaithPoints(3);

        //20
        assertEquals(22, b1.computeVictoryPoints());
        assertTrue(multiplayerGame.isSection1Reported());
        assertTrue(multiplayerGame.isSection2Reported());
        assertFalse(multiplayerGame.isSection3Reported());
        b1.giveFaithPoints(3);

        //23
        assertEquals(26, b1.computeVictoryPoints());
        assertTrue(multiplayerGame.isSection1Reported());
        assertTrue(multiplayerGame.isSection2Reported());
        assertFalse(multiplayerGame.isSection3Reported());
        b1.giveFaithPoints(2);
        b1.computeActivationPopeTile(2);

        //25
        assertEquals(34, b1.computeVictoryPoints());
        assertTrue(multiplayerGame.isSection1Reported());
        assertTrue(multiplayerGame.isSection2Reported());
        assertTrue(multiplayerGame.isSection3Reported());
        assertTrue(multiplayerGame.isGameOver());
    }

    @Test
    public void DevCardGameOverTest() throws emptyDevCardGridSlotSelectedException, developmentCardSlotLimitExceededException, invalidDevelopmentCardLevelPlacementException {
        Player player1 = new Player( "giagum",null);
        VirtualView vv = new VirtualView();
        SoloGame solo = new SoloGame(player1,vv);
        Board b1 = player1.getBoard();

        b1.addDevelopmentCard(solo.getCardFromGrid(0, 0), 0);
        assertFalse(solo.isGameOver());
        b1.addDevelopmentCard(solo.getCardFromGrid(1, 0), 0);
        assertFalse(solo.isGameOver());
        b1.addDevelopmentCard(solo.getCardFromGrid(2, 0), 0);
        assertFalse(solo.isGameOver());
        b1.addDevelopmentCard(solo.getCardFromGrid(0, 0), 1);
        assertFalse(solo.isGameOver());
        b1.addDevelopmentCard(solo.getCardFromGrid(1, 0), 1);
        assertFalse(solo.isGameOver());
        b1.addDevelopmentCard(solo.getCardFromGrid(2, 0), 1);
        assertFalse(solo.isGameOver());
        b1.addDevelopmentCard(solo.getCardFromGrid(0, 0), 2);
        assertTrue(solo.isGameOver());
    }

    @Test
    public void removeWarehouseDepotResource() throws addResourceLimitExceededException, invalidResourceTypeException, duplicatedWarehouseTypeException, removeResourceLimitExceededException {
        Player player1 = new Player( "giagum",null);
        VirtualView vv = new VirtualView();
        SoloGame solo = new SoloGame(player1,vv);
        Board b1 = player1.getBoard();
        b1.addWarehouseDepotResource(Resource.COIN,1, 0);
        assertEquals(1, b1.getWarehouseResource(Resource.COIN));
        assertEquals(1, b1.getWarehouseDepotResourceNumber(0));
        assertEquals(Resource.COIN, b1.getWarehouseDepotResourceType(0));
        b1.removeWarehouseDepotResource(Resource.COIN, 1,0);
        assertEquals(0, b1.getWarehouseResource(Resource.COIN));
        assertEquals(0, b1.getWarehouseDepotResourceNumber(0));
        assertNull(b1.getWarehouseDepotResourceType(0));
    }
}
