package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to communicate the start of a Main Action.
 */
public class MainActionStateMessage  implements AnswerMessage{
    private final String errorMessage;
    private final String currentPlayerNickname;

    /**
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public MainActionStateMessage(String currentPlayerNickname,String errorMessage) {
        this.errorMessage = errorMessage;
        this.currentPlayerNickname = currentPlayerNickname;
    }

    /**
     * {@inheritDoc}
     * @param view The view class of the client.
     */
    @Override
    public void selectView(View view) {
        view.visitMainActionState(currentPlayerNickname,errorMessage);
    }
}
