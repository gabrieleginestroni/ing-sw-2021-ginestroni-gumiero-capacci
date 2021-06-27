package it.polimi.ingsw.server.messages.client_server;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.controller.states.State;

import java.util.Map;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message that contains a map of the player's leader actions
 */
public class ChosenLeaderActionMessage implements Message {
    private final Map<Integer,Integer> actionMap;

    /**
     *
     * @param actionMap Map of leader actions. Key is the leader card index referred to the hidden hand,
     * value is the action related to the leader (0 - do nothing, 1 - activate, 2 - discard)
     */
    public ChosenLeaderActionMessage(Map<Integer, Integer> actionMap) {
        this.actionMap = actionMap;
    }

    /**
     * {@inheritDoc}
     * @param state State that will handle the message. It must be the current state of the controller
     * @param controller Controller of the game
     */
    @Override
    public void handleMessage(State state, Controller controller) {
        state.visitLeaderActionState(actionMap,controller);
    }
}
