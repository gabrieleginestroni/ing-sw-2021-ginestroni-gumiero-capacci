package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to communicate the start of the Activate Poduction Action.
 */
public class ActivateProductionStateMessage implements AnswerMessage {
    private final String currentPlayerNickname;
    private final String errorMessage;

    /**
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public ActivateProductionStateMessage(String currentPlayerNickname, String errorMessage) {
        this.currentPlayerNickname = currentPlayerNickname;
        this.errorMessage = errorMessage;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client
     * @throws invalidClientInputException Thrown when the current player tries to perform an illegal action.
     */
    @Override
    public void selectView(View view) throws invalidClientInputException {
        view.visitProductionState (currentPlayerNickname, errorMessage);
    }
}
