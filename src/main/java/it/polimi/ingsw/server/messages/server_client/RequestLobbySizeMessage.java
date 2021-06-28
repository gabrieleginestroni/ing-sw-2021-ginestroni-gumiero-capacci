package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used in the login phase of the game to communicate the connecting client that the gameID he selected
 * is not associated with any active lobby so the server, in order to create a new lobby, needs to know the number of players
 * the client wants the new game to be for.
 */
public class RequestLobbySizeMessage implements AnswerMessage {

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     * @throws invalidClientInputException
     */
    @Override
    public void selectView(View view) throws invalidClientInputException {
        view.visitRequestLobbySize("A game with the given ID doesn't exist. Type the size of the game you'd like:");
    }
}
