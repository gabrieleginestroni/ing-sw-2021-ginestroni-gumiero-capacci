package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to communicate the start of the game.
 */
public class GameStartedMessage implements AnswerMessage {

    /**
     * {@inheritDoc}
     * @param view The view class of the client
     */
    @Override
    public void selectView(View view) {
        view.visitGameStarted("Game started");
    }
}
