package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.states.MultiplayerState;
import it.polimi.ingsw.server.controller.states.SoloState;
import it.polimi.ingsw.server.controller.states.State;
import it.polimi.ingsw.server.messages.client_server.Message;
import it.polimi.ingsw.server.model.games.Game;
import it.polimi.ingsw.server.virtual_view.VirtualView;

import java.util.List;

public abstract class Controller {

    VirtualView virtualView;

    public Controller(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    public abstract void handleMessage(Message message);

    public abstract boolean isGameOver();

    public abstract Player getCurrentPlayer();

    public abstract CommunicationMediator getMediator();

    public abstract void nextPlayer();

    public abstract List<Player> othersPlayers();

    public void setCurrentState(State state) {
        if (this instanceof MultiplayerController)
            this.setCurrentState((MultiplayerState) state);
        else{
            this.setCurrentState((SoloState) state);
        }
    }

    abstract void setCurrentState(MultiplayerState multiplayerState);

    abstract void setCurrentState(SoloState state);

    public VirtualView getVirtualView() {
        return this.virtualView;
    }
    public abstract Game getModel();

    public abstract State getMarketState();
    public abstract State getActivateProductionState();
    public abstract State getLeaderActionState();
    public abstract State getDevCardSaleState();
    public abstract State getStartTurnState();
    public abstract State getMiddleTurnState();
    public abstract State getEndTurnState();
    public abstract State getMainActionState();

}