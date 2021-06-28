package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;


/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used in the login phase of the game to communicate the connecting client that the game he selected
 * is being created: someone already requested to connect to a game with the same gameID and the server is waiting
 * for that player's answer to create the relative lobby with the right number of players.
 */
public class LobbyNotReadyMessage implements AnswerMessage {

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
        view.visitLobbyNotReady("Lobby it's being creating, but not ready yet. Please choose another game ID:");
    }
}
