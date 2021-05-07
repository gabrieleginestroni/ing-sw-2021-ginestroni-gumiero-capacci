package it.polimi.ingsw.server.messages.client_server;

import java.util.Map;

public class ChosenInitialResourcesMessage extends Message {
    Map<Integer,Integer> chosenResources;

    public ChosenInitialResourcesMessage(Map<Integer, Integer> chosenResources) {
        this.chosenResources = chosenResources;
    }

    public Map<Integer, Integer> getChosenResources() {
        return chosenResources;
    }


}
