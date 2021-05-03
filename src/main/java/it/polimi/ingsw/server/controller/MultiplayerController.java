package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.games.MultiplayerGame;
import it.polimi.ingsw.server.virtualview.VirtualView;

import java.util.List;

public class MultiplayerController implements Controller{
    private final MultiplayerGame model;
    private MultiplayerState currentState;
    private final List<Player> players;
    private final CommunicationMediator mediator;
    private final VirtualView virtualView;

    //TODO
    public MultiplayerController(List<Player> players) {
        this.players = players;
        this.virtualView = new VirtualView();
        model = new MultiplayerGame(this.players,this.virtualView);
        mediator = new CommunicationMediator();

        System.out.println(virtualView.toJSONString());
        //currentState = StartGameState;
    }
}
