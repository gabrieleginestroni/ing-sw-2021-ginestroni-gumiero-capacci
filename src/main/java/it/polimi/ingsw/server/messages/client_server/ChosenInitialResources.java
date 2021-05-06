package it.polimi.ingsw.server.messages.client_server;

import java.util.Map;

public class ChosenInitialResources extends Message {
    Map<Integer,Integer> chosenResources;

    public ChosenInitialResources(Map<Integer, Integer> chosenResources) {
        this.chosenResources = chosenResources;
    }

    public Map<Integer, Integer> getChosenResources() {
        return chosenResources;
    }


}
