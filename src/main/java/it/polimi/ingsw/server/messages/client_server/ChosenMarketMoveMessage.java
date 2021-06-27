package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message that contains a market move
 */
public class ChosenMarketMoveMessage implements Message{
    private final int move;
    private final int index;

    /**
     *
     * @param move Move type (0 horizontal, 1 vertical)
     * @param index Index of the row or column to shift
     */
    public ChosenMarketMoveMessage(int move, int index) {
        this.move = move;
        this.index = index;
    }

    /**
     * {@inheritDoc}
     * @param state State that will handle the message. It must be the current state of the controller
     * @param controller Controller of the game
     */
    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitMarketState(move, index, controller);
    }

}
