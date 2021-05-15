package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

public class ChosenLeaderMessage implements Message{
    private final int[] chosenLeaderIndex;

    public ChosenLeaderMessage(int[] chosenLeaderIndex) {
        this.chosenLeaderIndex = chosenLeaderIndex;
    }

    public int[] getChosenLeaderIndex() {
        return chosenLeaderIndex;
    }

    @Override
    public void handleMessage(State state, Controller controller) {

    }
}
