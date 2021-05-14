package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

import java.io.Serializable;

public abstract class Message implements Serializable {

    public abstract void handleMessage(State state,Controller controller);

}
