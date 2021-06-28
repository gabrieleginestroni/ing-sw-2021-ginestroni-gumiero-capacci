package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to communicate the start of the Leader Action.
 */
public class LeaderActionStateMessage implements AnswerMessage {
    private final String currentPlayerNickname;

    /**
     * @param currentPlayerNickname Current player's nickname.
     */
    public LeaderActionStateMessage(String currentPlayerNickname) {
        this.currentPlayerNickname = currentPlayerNickname;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
        view.visitLeaderAction(this.currentPlayerNickname);
    }
}
