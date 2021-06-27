package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message that contains the desired game size
 */
public class LoginSizeMessage implements Message {
    private final int size;

    /**
     *
     * @param size Chosen game size
     */
    public LoginSizeMessage(int size) {
        this.size = size;
    }

    /**
     *
     * @return Chosen game size
     */
    public int getSize() {
        return size;
    }

    @Override
    public void handleMessage(State state, Controller controller) {

    }
}
