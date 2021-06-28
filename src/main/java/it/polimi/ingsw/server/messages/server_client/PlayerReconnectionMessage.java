package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to communicate every player that one of the previously disconnected player just reconnected.
 */
public class PlayerReconnectionMessage implements AnswerMessage{
    private String nickname;

    /**
     * @param nickname The reconnected player's nickname.
     */
    public PlayerReconnectionMessage(String nickname) {
        this.nickname = nickname;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     * @throws invalidClientInputException
     */
    @Override
    public void selectView(View view) throws invalidClientInputException {
        view.visitPlayerReconnection(nickname);
    }
}
