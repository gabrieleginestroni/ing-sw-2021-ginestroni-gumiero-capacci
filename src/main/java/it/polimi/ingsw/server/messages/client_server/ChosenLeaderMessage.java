package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message that contains the leader cards chosen by the player during the game setup
 */
public class ChosenLeaderMessage implements Message{
    private final int[] chosenLeaderIndex;

    /**
     *
     * @param chosenLeaderIndex Array of leader cards indexes chosen by the player. The index is the one
     * relative to the cards proposal order
     */
    public ChosenLeaderMessage(int[] chosenLeaderIndex) {
        this.chosenLeaderIndex = chosenLeaderIndex;
    }

    /**
     *
     * @return Array of leader cards indexes chosen by the player
     */
    public int[] getChosenLeaderIndex() {
        return chosenLeaderIndex;
    }

    @Override
    public void handleMessage(State state, Controller controller) {

    }
}
