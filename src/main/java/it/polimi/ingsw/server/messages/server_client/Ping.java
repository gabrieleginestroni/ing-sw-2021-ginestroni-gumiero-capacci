package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class periodically sent from the server to the client to test the connection and reset the client's socket timeout.
 */
public class Ping implements AnswerMessage {
    @Override
    public void selectView(View view) throws invalidClientInputException {
    }
}
