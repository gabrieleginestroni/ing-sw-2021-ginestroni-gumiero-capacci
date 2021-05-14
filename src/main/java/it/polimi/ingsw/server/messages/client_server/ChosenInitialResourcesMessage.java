package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

import java.util.Map;

public class ChosenInitialResourcesMessage extends Message {
    Map<Integer,Integer> chosenResources;

    public ChosenInitialResourcesMessage(Map<Integer, Integer> chosenResources) {
        this.chosenResources = chosenResources;
    }

    public Map<Integer, Integer> getChosenResources() {
        return chosenResources;
    }


    @Override
    public void handleMessage(State state, Controller controller) {

    }
}
