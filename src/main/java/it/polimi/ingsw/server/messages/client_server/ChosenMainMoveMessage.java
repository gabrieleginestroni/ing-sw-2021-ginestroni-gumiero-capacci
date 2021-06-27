package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message that contains the main action chosen by the player
 */
public class ChosenMainMoveMessage  implements Message{
    private final int move;

    /**
     *
     * @param move Main action (0 - market, 1 - development purchase, 2 - production)
     */
    public ChosenMainMoveMessage(int move) {
        this.move = move;
    }

    /**
     * {@inheritDoc}
     * @param state State that will handle the message. It must be the current state of the controller
     * @param controller Controller of the game
     */
    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitMainActionState(this.move,controller);
    }
}
