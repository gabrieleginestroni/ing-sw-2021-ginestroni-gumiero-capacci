package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

import java.io.Serializable;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Interface that represents the behaviour of the messages sent by the server to the client.
 */
public interface AnswerMessage extends Serializable {

    /**
     * This method is used to resolve double dispatching problems, allowing to execute the right code
     * according with the dynamic type of the client's view.
     * @param view The view class of the client.
     * @throws invalidClientInputException Thrown when the current player tries to perform an illegal action.
     */
    void selectView(View view) throws invalidClientInputException;
}
