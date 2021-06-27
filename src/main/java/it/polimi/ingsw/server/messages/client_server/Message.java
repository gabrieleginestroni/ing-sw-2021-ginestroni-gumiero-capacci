package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

import java.io.Serializable;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class the represents a serializable message, used by the client to interact with the server
 */
public interface Message extends Serializable {
    /**
     * Handles the message by calling the visit method that corresponds to the message on the state passed as parameter
     * @param state State that will handle the message. It must be the current state of the controller
     * @param controller Controller of the game
     */
    void handleMessage(State state,Controller controller);

}
