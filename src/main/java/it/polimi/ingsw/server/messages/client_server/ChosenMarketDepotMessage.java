package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message that contains the player's resource management decision after a market action
 */
public class ChosenMarketDepotMessage implements Message {
    private final int chosenDepot;

    /**
     *
     * @param chosenDepot Player's market resource management decision (-2 swap depots, -1 discard resource,
     * 0 to 5 place resource in depots)
     */
    public ChosenMarketDepotMessage(int chosenDepot) {
        this.chosenDepot = chosenDepot;
    }

    /**
     *
     * @return Player's market resource management decision
     */
    public int getChosenDepot() {
        return chosenDepot;
    }

    @Override
    public void handleMessage(State state, Controller controller) {

    }
}
