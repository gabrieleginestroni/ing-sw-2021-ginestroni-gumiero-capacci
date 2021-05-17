package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

public class ChosenMiddleMoveMessage implements Message {
    private final int move;

    public ChosenMiddleMoveMessage(int move) {
        this.move = move;
    }

    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitMiddleTurnState(move,controller);
    }
}
