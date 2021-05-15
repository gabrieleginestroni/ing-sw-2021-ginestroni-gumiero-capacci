package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.states.*;
import it.polimi.ingsw.server.messages.client_server.Message;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.games.Game;
import it.polimi.ingsw.server.model.games.Lorenzo;
import it.polimi.ingsw.server.model.games.SoloGame;
import it.polimi.ingsw.server.virtual_view.VirtualView;

import java.util.List;

public class SoloController extends Controller{
    private final SoloGame model;
    private SoloState currentState;
    private final Player player;
    private final CommunicationMediator mediator;

    public static final SoloState startTurnState = new StartTurnState();
    public static final SoloState marketState = new MarketState();
    public static final SoloState devCardSaleState = new DevCardSaleState();
    public static final SoloState leaderActionState = new LeaderActionState();
    public static final SoloState activateProductionState = new ActivateProductionState();


    //TODO
    public SoloController(Player player) {
        super(new VirtualView());
        this.player = player;

        model = new SoloGame(this.player,this.virtualView);
        mediator = new CommunicationMediator();

        List<LeaderCard> chosenLeaders = virtualView.propose4Leader(model.get4LeaderCards(), player);

        for(LeaderCard leaderCard : chosenLeaders)
            player.getBoard().addLeaderCard(leaderCard);

        this.player.getBoard().setInkwell();
        this.virtualView.gameStarted();

        currentState = startTurnState;
        virtualView.startTurn(this.player.getNickname());


        //currentState = StartGameState;
    }

    @Override
    public void handleMessage(Message message) {

    }

    @Override
    public boolean isGameOver() {
        return false;
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
    public void nextPlayer() {

    }

    @Override
    public List<Player> othersPlayers() {
        return null;
    }


    @Override
    void setCurrentState(MultiplayerState multiplayerState) {

    }

    @Override
    void setCurrentState(SoloState state) {
        this.currentState = state;

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
    public State getStartTurnState() {
        return startTurnState;
    }
}
