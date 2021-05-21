package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

public class ChosenMarketDepotMessage implements Message {
    private final int chosenDepot;

    public ChosenMarketDepotMessage(int chosenDepot) {
        this.chosenDepot = chosenDepot;
    }

    public int getChosenDepot() {
        return chosenDepot;
    }

    @Override
    public void handleMessage(State state, Controller controller) {

    }
}
