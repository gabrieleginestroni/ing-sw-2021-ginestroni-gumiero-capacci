package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to communicate to all the player who is the first in the round.
 */
public class InkwellMessage implements AnswerMessage {
    private final String nickname;

    /**
     * @param nickname First player's nickname.
     */
    public InkwellMessage(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return First player's nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
        view.visitInkwell(nickname);
    }
}
