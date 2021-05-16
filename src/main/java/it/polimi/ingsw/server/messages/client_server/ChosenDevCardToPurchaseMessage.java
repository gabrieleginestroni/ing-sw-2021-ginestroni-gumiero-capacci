package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class ChosenDevCardToPurchaseMessage implements Message {
    private final int row;
    private final int col;
    private final int cardSlot;
    private final Map<Integer, Map<Resource, Integer>> resToRemove;

    public ChosenDevCardToPurchaseMessage(int row, int col, Map<Integer, Map<Resource, Integer>> resToRemove, int cardSlot) {
        this.row = row;
        this.col = col;
        this.cardSlot = cardSlot;
        this.resToRemove = resToRemove;
    }

    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitDevCardSaleState(this.row, this.col, this.resToRemove, this.cardSlot, controller);
    }
}
