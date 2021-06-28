package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used in the login phase of the game to communicate the connecting client that, in the game he selected,
 * someone is already using the nickname he also chose.
 */
public class NicknameAlreadyUsedMessage implements AnswerMessage {
    private final String gameID;

    /**
     * @param gameID The gameID of the game the client was previously trying to connect to.
     */
    public NicknameAlreadyUsedMessage(String gameID) {
        this.gameID = gameID;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
        view.visitNicknameAlreadyUsed("Nickname already used. Please choose another nickname.",gameID);
    }
}
