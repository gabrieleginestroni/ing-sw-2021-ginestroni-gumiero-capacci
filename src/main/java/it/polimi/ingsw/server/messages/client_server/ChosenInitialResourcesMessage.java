package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message that contains a mapping of resources and depot chosen by the player during the setup phase of the game
 */
public class ChosenInitialResourcesMessage implements Message {
    Map<Integer,Integer> chosenResources;

    /**
     *
     * @param chosenResources Mapping of resource-depot chosen by the player. The key is a resource, value is a depot
     */
    public ChosenInitialResourcesMessage(Map<Integer, Integer> chosenResources) {
        this.chosenResources = chosenResources;
    }

    /**
     *
     * @return Mapping of resource-depot chosen by the player
     */
    public Map<Integer, Integer> getChosenResources() {
        return chosenResources;
    }


    @Override
    public void handleMessage(State state, Controller controller) {

    }
}
