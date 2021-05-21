package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

public class ChosenMarketMoveMessage implements Message{
    private final int move;
    private final int index;

    public ChosenMarketMoveMessage(int move, int index) {
        this.move = move;
        this.index = index;
    }

    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitMarketState(move, index, controller);
    }

}
