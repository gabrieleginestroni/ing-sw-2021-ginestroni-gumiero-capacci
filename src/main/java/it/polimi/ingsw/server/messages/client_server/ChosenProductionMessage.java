package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;
import it.polimi.ingsw.server.model.Resource;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message that contains the player's decisions related to the activation of a production
 */
public class ChosenProductionMessage implements Message{
    private final int productionIndex;
    private final Map<Integer, Integer> wareHouseMap;
    private final Map<Resource, Integer> strongBoxMap;
    private final Resource chosenResource;

    /**
     *
     * @param productionIndex Index of the production to activate (0 - 2 development, 3 - 4 leader, 5 board,
     * 6 receive outputs)
     * @param wareHouseMap Map of depot resources to use to activate the production. Key is the depot, value is
     * the quantity
     * @param strongBoxMap Map of strongbox resources to use to activate the production. Key is the resource,
     * value is the quantity
     * @param chosenResource Output resource to produce in case of board production
     */
    public ChosenProductionMessage(int productionIndex, Map<Integer, Integer> wareHouseMap, Map<Resource, Integer> strongBoxMap, Resource chosenResource) {
        this.productionIndex = productionIndex;
        this.wareHouseMap = wareHouseMap;
        this.strongBoxMap = strongBoxMap;
        this.chosenResource = chosenResource;
    }

    /**
     * {@inheritDoc}
     * @param state State that will handle the message. It must be the current state of the controller
     * @param controller Controller of the game
     */
    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitActivateProductionState(productionIndex, wareHouseMap, strongBoxMap, chosenResource, controller);
    }
}
