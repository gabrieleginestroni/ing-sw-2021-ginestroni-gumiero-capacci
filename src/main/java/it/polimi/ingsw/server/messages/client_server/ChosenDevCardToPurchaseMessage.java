package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message that contains the card slot, development card grid coordinates and resources to remove related
 * to the client's decisions taken during the development card purchase state
 */
public class ChosenDevCardToPurchaseMessage implements Message {
    private final int row;
    private final int col;
    private final int cardSlot;
    private final Map<Integer, Map<Resource, Integer>> resToRemove;

    /**
     *
     * @param row Development card grid row
     * @param col Development card grid column
     * @param resToRemove Map of resources that are used to buy the card. External key is a depot, inner key is
     * a resource, value is the quantity to remove from the depot
     * @param cardSlot Card slot where the card should be placed
     */
    public ChosenDevCardToPurchaseMessage(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot) {
        this.row = row;
        this.col = col;
        this.cardSlot = cardSlot;
        this.resToRemove = resToRemove;
    }

    /**
     * {@inheritDoc}
     * @param state State that will handle the message. It must be the current state of the controller
     * @param controller Controller of the game
     */
    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitDevCardSaleState(this.row, this.col, this.resToRemove, this.cardSlot, controller);
    }
}
