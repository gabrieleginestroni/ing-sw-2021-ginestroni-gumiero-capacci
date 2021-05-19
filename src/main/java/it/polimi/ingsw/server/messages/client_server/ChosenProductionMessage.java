package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

public class ChosenProductionMessage implements Message{
    private final int productionIndex;
    private final Map<Integer, Integer> wareHouseMap;
    private final Map<Resource, Integer> strongBoxMap;
    private final Resource chosenResource;

    public ChosenProductionMessage(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource) {
        this.productionIndex = productionIndex;
        this.wareHouseMap = wareHouseMap;
        this.strongBoxMap = strongBoxMap;
        this.chosenResource = chosenResource;
    }

    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitActivateProductionState(productionIndex, wareHouseMap, strongBoxMap, chosenResource, controller);
    }
}
