package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

import java.util.Map;

public class ChosenLeaderAction implements Message {
    private final Map<Integer,Integer> actionMap;

    public ChosenLeaderAction(Map<Integer, Integer> actionMap) {
        this.actionMap = actionMap;
    }

    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitLeaderActionState(actionMap,controller);
    }
}
