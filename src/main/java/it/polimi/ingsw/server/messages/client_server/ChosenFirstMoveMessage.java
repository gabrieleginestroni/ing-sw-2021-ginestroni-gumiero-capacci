package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message that contains the player's decision to do a main or a leader action
 */
public class ChosenFirstMoveMessage implements Message {
    private final int move;

    /**
     *
     * @param move Index of the move. '0' is a main action, '1' a leader action
     */
    public ChosenFirstMoveMessage(int move) {
        this.move = move;
    }

    /**
     * {@inheritDoc}
     * @param state State that will handle the message. It must be the current state of the controller
     * @param controller Controller of the game
     */
    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitStartTurnState(this.move,controller);

    }
}
