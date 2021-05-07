package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.states.SoloState;
import it.polimi.ingsw.server.messages.client_server.Message;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.games.SoloGame;
import it.polimi.ingsw.server.virtual_view.VirtualView;

import java.util.List;

public class SoloController implements Controller{
    private final SoloGame model;
    private SoloState currentState;
    private final Player player;
    private final CommunicationMediator mediator;
    private final VirtualView virtualView;


    //TODO
    public SoloController(Player player) {
        this.player = player;
        this.virtualView = new VirtualView();
        model = new SoloGame(this.player,this.virtualView);
        mediator = new CommunicationMediator();

        List<LeaderCard> chosenLeaders = virtualView.propose4Leader(model.get4LeaderCards(), player);

        for(LeaderCard leaderCard : chosenLeaders)
            player.getBoard().addLeaderCard(leaderCard);

        this.player.getBoard().setInkwell();
        this.virtualView.gameStarted();

        System.out.println(virtualView.toJSONString());
        //currentState = StartGameState;
    }

    @Override
    public void handleMessage(Message message) {

    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
