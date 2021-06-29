package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.Server;
import it.polimi.ingsw.server.controller.states.*;

import it.polimi.ingsw.server.messages.client_server.Message;

import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.games.Game;
import it.polimi.ingsw.server.model.games.SoloGame;

import it.polimi.ingsw.server.virtual_view.VirtualView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * The controller class of a Solo Game.
 */
public class SoloController extends Controller{
    private final SoloGame model;
    private SoloState currentState;
    private final Player player;
    private final CommunicationMediator mediator;

    public static final SoloState startTurnState = new StartTurnState();
    public static final SoloState marketState = new MarketState();
    public static final SoloState devCardSaleState = new SoloDevCardSaleState();
    public static final SoloState leaderActionState = new SoloLeaderActionState();
    public static final SoloState activateProductionState = new SoloActivateProductionState();
    public static final SoloState middleTurnState = new MiddleTurnState();
    public static final SoloState endTurnState = new LorenzoTurnState();
    public static final SoloState mainActionState = new MainActionState();
    public static final SoloState endGameState = new SoloEndGameState();
    public static final SoloState resourceManagementState = new SoloResourceManagementState();
    public static final SoloState swapState = new SwapState();
    public static final SoloState whiteMarbleState = new WhiteMarbleState();

    /**
     * @param player The player that is going to play the relative Solo Game.
     * @param gameID The gameID of the Solo Game.
     */
    public SoloController(Player player,String gameID) {
        super(new VirtualView(),gameID);
        this.player = player;

        model = new SoloGame(this.player,this.virtualView);
        mediator = new CommunicationMediator();

        List<LeaderCard> chosenLeaders = virtualView.propose4Leader(model.get4LeaderCards(), player);

        try {
            for (LeaderCard leaderCard : chosenLeaders)
                player.getBoard().addLeaderCard(leaderCard);

            this.player.getBoard().setInkwell();
            this.virtualView.gameStarted();

            currentState = startTurnState;
            virtualView.startTurn(this.player.getNickname(), null);
        }catch (NullPointerException e){
            Server.lobbies.remove(gameID);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * This method is used to notify the disconnection of the only player in the game,
     * that leads to the elimination of the game.
     * @param player The disconnected player.
     */
    @Override
    public void notifyPlayerDisconnection(Player player) {
        Server.lobbies.remove(gameID);
        Thread.currentThread().interrupt();
    }

    /**
     * This method is used to resolve double dispatching problems delegating the handling of a message to the
     * actual current state of the controller: that allows to execute the right code for the specific dynamic type of the message
     * and the specific dynamic type of the state.
     * @param message The message that the ClientHandler of the only player just received.
     */
    @Override
    public void handleMessage(Message message) {
        message.handleMessage(this.currentState,this);
    }

    /**
     * @param state The new current state.
     */
    @Override
    public void setCurrentState(State state) {
        this.currentState = (SoloState) state;
    }

    @Override
    public void notifyPlayerReconnection(Player player) {}

    @Override
    public boolean isGameOver() {
        return model.isGameOver();
    }

    @Override
    public boolean isRoundOver() {
        return true;
    }

    @Override
    public Player getCurrentPlayer() {
        return this.player;
    }

    @Override
    public CommunicationMediator getMediator() {
        return this.mediator;
    }

    @Override
    public void nextPlayer() {}

    @Override
    public List<Player> othersPlayers() {
        return new ArrayList<>();
    }

    @Override
    public Game getModel() {
        return model;
    }

    @Override
    public State getMarketState() {
        return marketState;
    }

    @Override
    public State getActivateProductionState() {
        return activateProductionState;
    }

    @Override
    public State getLeaderActionState() {
        return leaderActionState;
    }

    @Override
    public State getDevCardSaleState() {
        return devCardSaleState;
    }

    @Override
    public State getResourceManagementState() { return resourceManagementState; }

    @Override
    public State getSwapState() {return swapState; }

    @Override
    public State getWhiteMarbleState() {return whiteMarbleState; }

    @Override
    public State getStartTurnState() {
        return startTurnState;
    }

    @Override
    public State getMiddleTurnState() { return middleTurnState; }

    @Override
    public State getEndTurnState() { return endTurnState; }

    @Override
    public State getMainActionState() { return mainActionState; }

    @Override
    public State getEndGameState() { return endGameState; }
}
