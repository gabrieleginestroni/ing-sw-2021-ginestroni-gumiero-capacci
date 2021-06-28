package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to communicate the termination of the game because
 * too many players disconnected or someone disconnected during the setup of the game.
 */
public class GameAbortedMessage implements AnswerMessage {

    /**
     * {@inheritDoc}
     * @param view The view class of the client
     * @throws invalidClientInputException Thrown when the current player tries to perform an illegal action.
     */
    @Override
    public void selectView(View view) throws invalidClientInputException {
        view.visitGameAbort();
    }
}
