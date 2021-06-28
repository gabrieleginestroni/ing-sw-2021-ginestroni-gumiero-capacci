package it.polimi.ingsw.server.messages.server_client;

import it.polimi.ingsw.client.view.View;

/**
 * @author Gabriele Ginestroni, Giacomo Gumiero, Tommaso Capacci
 * Message class used to announce the phase of the Market Action in which the current player chose to perform a swap
 * in his warehouse and the server is waiting for the indexes of the depots to swap.
 */
public class SwapMessage implements AnswerMessage {
    private final String currentPlayerNickname;
    private final String errorMessage;

    /**
     * @param currentPlayerNickname Current player's nickname.
     * @param errorMessage A nullable string that, in case of error, contains a message for the current player.
     */
    public SwapMessage(String currentPlayerNickname, String errorMessage) {
        this.currentPlayerNickname = currentPlayerNickname;
        this.errorMessage = errorMessage;
    }

    @Override
    public void selectView(View view) {
        view.visitSwapState(this.currentPlayerNickname,this.errorMessage);
    }
}
