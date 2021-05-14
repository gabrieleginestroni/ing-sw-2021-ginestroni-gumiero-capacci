package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

public class ChosenFirstMoveMessage extends Message {
    private final int move;

    public ChosenFirstMoveMessage(int move) {
        this.move = move;
    }


    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitStartTurnState(this.move,controller);

    }
}
