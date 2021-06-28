package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.view.exceptions.invalidClientInputException;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to communicate every player that someone just disconnected.
 */
public class PlayerDisconnectionMessage implements AnswerMessage{
    private String nickname;

    /**
     * @param nickname The disconnected player's nickname.
     */
    public PlayerDisconnectionMessage(String nickname) {
        this.nickname = nickname;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     * @throws invalidClientInputException
     */
    @Override
    public void selectView(View view) throws invalidClientInputException {
        view.visitPlayerDisconnection(nickname);
    }
}
