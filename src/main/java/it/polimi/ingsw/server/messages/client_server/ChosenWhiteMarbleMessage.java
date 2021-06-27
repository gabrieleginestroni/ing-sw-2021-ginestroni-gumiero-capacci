package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;
import it.polimi.ingsw.server.model.Resource;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Class that contains the chosen white marble power to use in case of two active white marble powers
 */
public class ChosenWhiteMarbleMessage implements Message {
    private final Resource res;

    /**
     *
     * @param res Chosen white marble power resource
     */
    public ChosenWhiteMarbleMessage(Resource res) {
        this.res = res;
    }

    /**
     *
     * @return Chosen white marble power resource
     */
    public Resource getRes() {
        return res;
    }

    @Override
    public void handleMessage(State state, Controller controller) {
    }

}
