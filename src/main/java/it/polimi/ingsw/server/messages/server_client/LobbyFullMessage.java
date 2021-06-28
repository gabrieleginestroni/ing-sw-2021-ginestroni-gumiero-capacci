package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used in the login phase of the game to communicate the connecting client that the game he selected
 * has already started.
 */
public class LobbyFullMessage implements AnswerMessage {

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
        view.visitLobbyFull("Game full. Please choose another game ID:");
    }
}
