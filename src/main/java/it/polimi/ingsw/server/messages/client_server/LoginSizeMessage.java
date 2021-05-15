package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

import java.io.Serializable;

public class LoginSizeMessage implements Message {
    private final int size;

    public LoginSizeMessage(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void handleMessage(State state, Controller controller) {

    }
}
