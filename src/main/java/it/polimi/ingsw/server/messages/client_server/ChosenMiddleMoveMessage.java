package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message that contains the decision of skipping/doing a leader action
 */
public class ChosenMiddleMoveMessage implements Message {
    private final int move;

    /**
     *
     * @param move Middle turn move (0 skip leader action, 1 do a leader action)
     */
    public ChosenMiddleMoveMessage(int move) {
        this.move = move;
    }

    /**
     * {@inheritDoc}
     * @param state State that will handle the message. It must be the current state of the controller
     * @param controller Controller of the game
     */
    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitMiddleTurnState(move,controller);
    }
}
