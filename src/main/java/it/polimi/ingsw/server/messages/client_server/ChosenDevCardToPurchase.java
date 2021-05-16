package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class ChosenDevCardToPurchase implements Message {
    private final int row;
    private final int col;
    private final Map<Integer, Map<Resource, Integer>> resToRemove;

    public ChosenDevCardToPurchase(int row, int col,Map<Integer, Map<Resource,Integer>> resToRemove) {
        this.row = row;
        this.col = col;
        this.resToRemove = resToRemove;
    }

    @Override
    public void handleMessage(State state, Controller controller) {

    }
}
