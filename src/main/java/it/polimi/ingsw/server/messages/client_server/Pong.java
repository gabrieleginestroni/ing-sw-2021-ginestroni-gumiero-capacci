package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message used to keep the connection with the client alive
 */
public class Pong implements Message {
    @Override
    public void handleMessage(State state, Controller controller) {

    }
}
