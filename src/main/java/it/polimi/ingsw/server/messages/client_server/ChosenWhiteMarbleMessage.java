package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;
import it.polimi.ingsw.server.model.Resource;

public class ChosenWhiteMarbleMessage implements Message {
    private final Resource res;

    public ChosenWhiteMarbleMessage(Resource res) {
        this.res = res;
    }

    public Resource getRes() {
        return res;
    }

    @Override
    public void handleMessage(State state, Controller controller) {
    }

}
