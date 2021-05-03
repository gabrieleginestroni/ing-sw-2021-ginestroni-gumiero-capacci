package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.games.SoloGame;

public class SoloController implements Controller{
    private final SoloGame model;
    private SoloState currentState;
    private final Player player;
    private final CommunicationMediator mediator;

    //TODO
    public SoloController(Player player) {
        this.player = player;
        model = new SoloGame(this.player);
        mediator = new CommunicationMediator();
        //currentState = StartGameState;
    }
}
