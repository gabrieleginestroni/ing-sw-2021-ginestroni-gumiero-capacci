package it.polimi.ingsw.model.board;

import it.polimi.ingsw.controller.ControllerPlayer;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.games.MultiplayerGame;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void addLeaderCardTest() {
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
    public void addDevelopmentCard() {
    }

    @Test
    public void getStrongBoxResource() {
    }

    @Test
    public void addLeaderDepot() {
    }

    @Test
    public void setInkwell() {
    }

    @Test
    public void getFaithPoints() {
    }

    @Test
    public void giveFaithPoints() {
    }

    @Test
    public void getCardNumber() {
    }

    @Test
    public void addDepotResource() {
    }

    @Test
    public void removeDepotResource() {
    }

    @Test
    public void addStrongboxResourceTest() {
        Board b = new Board();
       b.addStrongboxResource( Resource.SERVANT, 2);
        assertEquals(2, b.getStrongBoxResource(Resource.SERVANT));
        b.addStrongboxResource( Resource.SERVANT, 2);
        assertEquals(4, b.getStrongBoxResource(Resource.SERVANT));
    }

    @Test
    public void removeStrongboxResource() throws invalidStrongBoxRemoveException {
        Board b = new Board();
        b.addStrongboxResource( Resource.SERVANT, 2);
        b.removeStrongboxResource( Resource.SERVANT, 2);
        assertEquals(0, b.getStrongBoxResource(Resource.SERVANT));
    }

    @Test
    public void discardLeaderCard() {
    }

}