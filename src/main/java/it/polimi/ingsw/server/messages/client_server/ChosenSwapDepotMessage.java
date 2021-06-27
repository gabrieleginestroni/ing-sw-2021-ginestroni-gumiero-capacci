package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message that contains two warehouse depots to swap
 */
public class ChosenSwapDepotMessage implements Message {
    private final int depot1;
    private final int depot2;

    /**
     *
     * @param depot1 Depot to swap (-1 to exit swap state, 0 - 2 depots to swap)
     * @param depot2 Depot to swap (-1 to exit swap state, 0 - 2 depots to swap)
     */
    public ChosenSwapDepotMessage(int depot1, int depot2) {
        this.depot1 = depot1;
        this.depot2 = depot2;
    }

    /**
     * {@inheritDoc}
     * @param state State that will handle the message. It must be the current state of the controller
     * @param controller Controller of the game
     */
    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitSwapState(depot1, depot2, controller);
    }
}
