package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.states.MultiplayerState;
import it.polimi.ingsw.server.controller.states.SoloState;
import it.polimi.ingsw.server.controller.states.StartTurnState;
import it.polimi.ingsw.server.messages.client_server.Message;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.games.Lorenzo;
import it.polimi.ingsw.server.model.games.SoloGame;
import it.polimi.ingsw.server.virtual_view.VirtualView;

import java.util.List;

public class SoloController extends Controller{
    private final SoloGame model;
    private SoloState currentState;
    private final Player player;
    private final CommunicationMediator mediator;


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

        currentState = new StartTurnState();
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
        return null;
    }

    @Override
    public CommunicationMediator getMediator() {
        return null;
    }

    @Override
    public void nextPlayer() {

    }

    @Override
    public List<Player> othersPlayers() {
        return null;
    }

    @Override
    public Lorenzo getLorenzo() {
        return null;
    }

    @Override
    void setCurrentState(MultiplayerState multiplayerState) {

    }

    @Override
    void setCurrentState(SoloState state) {

    }
}
