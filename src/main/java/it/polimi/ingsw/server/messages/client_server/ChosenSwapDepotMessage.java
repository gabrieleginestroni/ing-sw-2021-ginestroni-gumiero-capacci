package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

public class ChosenSwapDepotMessage implements Message {
    private final int depot1;
    private final int depot2;

    public ChosenSwapDepotMessage(int depot1, int depot2) {
        this.depot1 = depot1;
        this.depot2 = depot2;
    }

    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitSwapState(depot1, depot2, controller);
    }
}
