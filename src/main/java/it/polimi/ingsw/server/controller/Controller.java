package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.states.State;
import it.polimi.ingsw.server.messages.client_server.Message;
import it.polimi.ingsw.server.model.games.Game;
import it.polimi.ingsw.server.virtual_view.VirtualView;

import java.util.List;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * The abstract class that represents the concept of Controller.
 */
public abstract class Controller {
    String gameID;
    VirtualView virtualView;

    /**
     * @param virtualView The VirtualView relative to the game the controller is going to handle.
     * @param gameID The gameID of the game.
     */
    public Controller(VirtualView virtualView,String gameID) {
        this.virtualView = virtualView;
        this.gameID = gameID;
    }

    public abstract void notifyPlayerDisconnection(Player player);

    public abstract void notifyPlayerReconnection(Player player);

    public abstract void handleMessage(Message message);

    public abstract boolean isGameOver();

    public abstract boolean isRoundOver();

    public abstract Player getCurrentPlayer();

    public abstract CommunicationMediator getMediator();

    public abstract void nextPlayer();

    public abstract List<Player> othersPlayers();

    public abstract void setCurrentState(State state);

    public abstract Game getModel();

    public abstract State getMarketState();
    public abstract State getActivateProductionState();
    public abstract State getLeaderActionState();
    public abstract State getDevCardSaleState();
    public abstract State getResourceManagementState();
    public abstract State getSwapState();
    public abstract State getWhiteMarbleState();

    public abstract State getStartTurnState();
    public abstract State getMiddleTurnState();
    public abstract State getEndTurnState();
    public abstract State getMainActionState();
    public abstract State getEndGameState();

    /**
     * @return The reference to the VirtualView relative to the game the controller is handling
     */
    public VirtualView getVirtualView() {
        return this.virtualView;
    }

    /**
     * @return The gameID of the game the controller is handling.
     */
    public String getGameID() {
        return gameID;
    }
}